package Model;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Grafo dirigido com arestas valoradas representado pela Lista de Adjacência.
 * 
 * Autor: alunos da turma 137 - Alg. e Est. Dados II - ES 
 * Data: 31/05/2017
 * 
 */
public class Grafo {


	LinkedList<LinkedList<Node>> la = new LinkedList<LinkedList<Node>>();
	ArrayList<Aeroporto> lv = new ArrayList<Aeroporto>(); 
	
	static class Node implements Comparator<Node>{
		private Rota rota;
		private Aeroporto aeroporto;
	
		public Node(Rota rota, Aeroporto aeroporto) {
			super();
			this.rota = rota;
			this.aeroporto = aeroporto;
		}

		public Rota getRota() {
			return rota;
		}

		public void setRota(Rota rota) {
			this.rota = rota;
		}

		public Aeroporto getAeroporto() {
			return aeroporto;
		}

		public void setAeroporto(Aeroporto aeroporto) {
			this.aeroporto = aeroporto;
		}

		@Override
		public String toString() {
			return "Node [rota=" + rota.getOrigem().getCodigo() + " - " + rota.getDestino().getCodigo() + "]";
		}

		@Override
		public int compare(Node n1, Node n2) {
			if(n1.getRota().getDistancia() < n1.getRota().getDistancia()){
				return -1;
			}else if(n1.getRota().getDistancia() > n1.getRota().getDistancia()){
				return 1;
			}
			return 0;
		}
	}
	
	/**
	 * Popula la (lista de adjacencia)
	 * @param rotas
	 */
	public Grafo(ArrayList<Rota> rotas){
		for(int i=0; i<rotas.size(); i++){
			Aeroporto origem = rotas.get(i).getOrigem();
			Aeroporto destino = rotas.get(i).getDestino();
			Rota rota = rotas.get(i);
			
			if(origem != null && destino != null){
				this.addAresta(origem, destino, rotas.get(i));
			}
		}
	}
	
	/**
	 * Encontra o indice de um aeroporto na lv (lista de vértices)
	 * @param codigo
	 * @return
	 */
	public int findAirportIndex(String codigo){
		for(int i=0; i<lv.size(); i++){
			if(lv.get(i).getCodigo().equals(codigo)){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Adiciona nodos na L.A
	 * @param origem
	 * @param aeroporto
	 * @param rota
	 */
	public void addAresta(Aeroporto origem, Aeroporto aeroporto, Rota rota) {
		int origemIndex = this.findAirportIndex(origem.getCodigo());
		
		//Se o aeroporto de origem não existe na L.V o adiciona na L.V e 
		//adciona uma lista na L.A
		if(origemIndex < 0){
			lv.add(origem);
			la.add(new LinkedList<>());
			origemIndex = la.size()-1;
		}
		//Adiciona aeroporto destino na lista do aeroporto origem
		la.get(origemIndex).add(new Node(rota, aeroporto));
	}
		
	/**
	 * Obtem grau de saida de um nodo
	 * @param nodo
	 * @return
	 */
	public int grauDeSaida(int nodo){
		return  la.get(nodo).size();
	}
	
	/*public int grauDeEntrada(int nodo){
		int cont = 0;
		for (int i = 0; i < la.size(); i++) {
			for (int j = 0 ; j < la.get(i).size(); j++){
				if(la.get(i).get(j).vDest == nodo){
					cont++;
				}
			}
		}
		return cont;
	}*/
	
	public int getArestas(){
		int cont = 0;
		for (int i = 0; i < la.size(); i++) {
			cont += la.get(i).size();
		}
		return cont;
	}
	
	/**
	 * Obtem a menor rota entre dois aeroportos
	 * @param codigo1
	 * @param codigo2
	 */
	public void determinarRotaDeMenorCusto(String codigo1, String codigo2){
		
	}
	
	public void dijsktra(String codigo1, String codigo2){
		Aeroporto origem = this.obterAeroportoPorCodigo(codigo1);
		Aeroporto destino = this.obterAeroportoPorCodigo(codigo2);
		if(origem == null || destino == null){
			System.out.println("Aeroporto não encontrado.");
			return;
		}
		ArrayList<Node> visitados = new ArrayList<Node>();
		HashMap<Integer, Double> distancias = new HashMap<Integer, Double>();
		distancias.put(this.findAirportIndex(origem.getCodigo()), 0.0);
		
		LinkedList<Node> adjacentes = this.obterAdjacentesDoMesmoPais(origem);		
		Node u = new Node(null,origem);
		this.dijsktraAux(u,adjacentes,visitados,distancias);
		
		/*
		 Iterator it = distancias.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        System.out.println(pair.getKey() + " = " + pair.getValue());
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		 */
	}	
	
	public void dijsktraAux(Node node, LinkedList<Node> adjacentes, ArrayList<Node> visitados, HashMap<Integer, Double> distancias){
		if(adjacentes.size() > 0 && !visitados.contains(adjacentes.get(0))){
			visitados.add(node);
			Node v = this.obterNodoComMenorDistancia(adjacentes);
			adjacentes.remove(v);
			
			int vIndex = this.findAirportIndex(v.getAeroporto().getCodigo());
			int uIndex = this.findAirportIndex(node.getAeroporto().getCodigo());
			double vDist = this.obtemDistancia(distancias, vIndex);
			double uDist = this.obtemDistancia(distancias, uIndex)+v.getRota().getDistancia();
			if(vDist > uDist){
				if(vDist == Double.MAX_VALUE){
					distancias.put(vIndex, uDist);
				}else{
					distancias.put(vIndex, distancias.get(vIndex)+uDist);
				}
			}
			
			LinkedList<Node> adj = this.obterAdjacentesDoMesmoPais(v.getAeroporto());
			this.dijsktraAux(v, adj, visitados, distancias);
		}
	}
	
	public double obtemDistancia(HashMap<Integer, Double> distancias, int key){
		if(!distancias.containsKey(key)){
			distancias.put(key, Double.MAX_VALUE);
		}
		return distancias.get(key);
	}
	
	public Node obterNodoComMenorDistancia(LinkedList<Node> nodes){
		Node n = nodes.get(0);
		double d = nodes.get(0).getRota().getDistancia();
		for(int i=1; i<nodes.size(); i++){
			if(nodes.get(i).getRota().getDistancia() < d){
				n = nodes.get(i);
				d = nodes.get(i).getRota().getDistancia();
			}
		}
		return n;
	}
	
	public Aeroporto obterAeroportoPorCodigo(String codigo){
		for(int i=0; i< this.lv.size(); i++ ){
			if(lv.get(i).getCodigo().equals(codigo)){
				return lv.get(i); 
			}
		}
		return null;
	}
	
	public ArrayList<Aeroporto> obterAeroportosDoMesmoPais(String pais){
		ArrayList<Aeroporto> aeroportos = new ArrayList<Aeroporto>();	
		for(int i=0; i< this.lv.size(); i++ ){
			if(this.lv.get(i).getPais().getCodigo().equals(pais)){
				aeroportos.add(this.lv.get(i));
			}
		}
		return aeroportos;
	}
	
	public LinkedList<Node> obterAdjacentesDoMesmoPais(Aeroporto origem){
		LinkedList<Node> adj = this.la.get(this.findAirportIndex(origem.getCodigo()));
		for(int i=0; i<adj.size(); i++){
			if(!adj.get(i).getAeroporto().getPais().getCodigo().equals(origem.getPais().getCodigo())){
				adj.remove(i);
			}
		}
		return adj;
	}
	
	/**
	 * Verifica se uma cia aérea faz uma rota exclusiva
	 * @param ciaNome
	 */
	public void verificarRotaExclusiva(String ciaNome){
		
	}
	
	@Override
	public String toString() {
		String toString ="";
		for (int i = 0; i < la.size(); i++) {
			toString+= la.get(i).toString() + "\n";
		}
		return toString;
	}

	
	
}
