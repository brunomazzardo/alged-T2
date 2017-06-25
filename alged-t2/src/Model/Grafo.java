package Model;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	
	LinkedList<Node> menorRota;
	HashMap<Integer, Double> distancias;
	
	public LinkedList<Node> getMenorRota() {
		return menorRota;
	}

	public void setMenorRota(LinkedList<Node> menorRota) {
		this.menorRota = menorRota;
	}

	public HashMap<Integer, Double> getDistancias() {
		return distancias;
	}

	public void setDistancias(HashMap<Integer, Double> distancias) {
		this.distancias = distancias;
	}

	/**
	 * Popula L.A (lista de adjacencia)
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
		int destinoIndex = this.findAirportIndex(aeroporto.getCodigo());
		
		//Se o aeroporto de origem não existe na L.V o adiciona na L.V e 
		//adciona uma lista na L.A
		if(origemIndex < 0){
			lv.add(origem);
			la.add(new LinkedList<>());
			origemIndex = la.size()-1;
		}
		if(destinoIndex < 0){
			lv.add(aeroporto);
			la.add(new LinkedList<>());
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
		Aeroporto origem = this.obterAeroportoPorCodigo(codigo1);
		Aeroporto destino = this.obterAeroportoPorCodigo(codigo2);
		if(origem == null || destino == null){
			System.out.println("Aeroporto não encontrado.");
			return;
		}
		this.dijsktra(origem, destino);
	}
	
	/**
	 * Algoritimo de dijsktra recursivo
	 * @param origem
	 * @param destino
	 */
	public void dijsktra(Aeroporto origem, Aeroporto destino){		
		ArrayList<Node> visitados = new ArrayList<Node>();
		this.distancias = new HashMap<Integer, Double>();
		distancias.put(this.findAirportIndex(origem.getCodigo()), 0.0);
		
		HashMap<String, ArrayList<Node>> caminho = new HashMap<String, ArrayList<Node>>();
		LinkedList<Node> adjacentes = this.obterAdjacentesDoMesmoPais(origem);		
		Node u = new Node(null,origem);
		this.dijsktraAux(u,adjacentes,visitados,distancias,caminho);
		this.menorRota = this.obterCaminho(caminho, new Node(null,destino));
	}	
	
	/**
	 * Metodo auxiliar para o algoritimo de dijsktra recursivo
	 * @param node
	 * @param adjacentes
	 * @param visitados
	 * @param distancias
	 * @param caminho
	 */
	public void dijsktraAux(Node node, LinkedList<Node> adjacentes, ArrayList<Node> visitados, HashMap<Integer, Double> distancias, HashMap<String, ArrayList<Node>> caminho){
		if(adjacentes.size() > 0 && !visitados.contains(adjacentes.get(0))){
			visitados.add(node);
			Node v = this.obterNodoComMenorDistancia(adjacentes);
			//adjacentes.remove(v);
			
			if(v.getAeroporto().getCodigo().equals("ABI")){
				System.out.println("FOUND");
			}
			
			int vIndex = this.findAirportIndex(v.getAeroporto().getCodigo());
			int uIndex = this.findAirportIndex(node.getAeroporto().getCodigo());
			double vDist = this.obtemDistancia(distancias, vIndex);
			double uDist = this.obtemDistancia(distancias, uIndex)+v.getRota().getDistancia();
			if(vDist > uDist){
				ArrayList<Node> a = new ArrayList<>();
				a.add(v);
				a.add(node);
				if(vDist == Double.MAX_VALUE){
					distancias.put(vIndex, uDist);					
					caminho.put(v.getAeroporto().getCodigo(),a);
				}else{
					distancias.put(vIndex, distancias.get(vIndex)+uDist);
					caminho.put(v.getAeroporto().getCodigo(),a);
				}
			}
			
			LinkedList<Node> adj = this.obterAdjacentesDoMesmoPais(v.getAeroporto());
			this.dijsktraAux(v, adj, visitados, distancias, caminho);
		}
	}
	
	/**
	 * Obtem o caminho mais curto dado o vertice destino e os caminhos
	 * @param caminho
	 * @param destino
	 * @return
	 */
	public LinkedList<Node> obterCaminho(HashMap<String, ArrayList<Node>> caminho, Node destino) {
        LinkedList<Node> rota = new LinkedList<Node>();
        Node step = destino;
        if (caminho.get(destino.getAeroporto().getCodigo()) == null) {
            return null;
        }
        rota.add(caminho.get(step.getAeroporto().getCodigo()).get(0));
        while (caminho.get(step.getAeroporto().getCodigo()) != null) {
            step = caminho.get(step.getAeroporto().getCodigo()).get(1);
            rota.add(step);
        }
        Collections.reverse(rota);
        return rota;
    }
	
	/**
	 * Obtem distancia entre dois aeroportos (vertices) em um hashmap usando o indice
	 * do aerporto na L.V (lista de vertices),
	 * se o aeroporto nao esta nesse hashmap o adiciona com distancia de Double.MAX_VALUE 
	 * @param distancias
	 * @param key
	 * @return
	 */
	public double obtemDistancia(HashMap<Integer, Double> distancias, int key){
		if(!distancias.containsKey(key)){
			distancias.put(key, Double.MAX_VALUE);
		}
		return distancias.get(key);
	}
	
	/**
	 * Obtem nodo como a rota que tem a menor distancia entre os nodos adjacentes
	 * @param nodes
	 * @return
	 */
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
	
	/**
	 * Busca na L.V o aeroporto que possui determinado codigo
	 * @param codigo
	 * @return
	 */
	public Aeroporto obterAeroportoPorCodigo(String codigo){
		for(int i=0; i< this.lv.size(); i++ ){
			if(lv.get(i).getCodigo().equals(codigo)){
				return lv.get(i); 
			}
		}
		return null;
	}
	
	/**
	 * Obtem aeroportos (vertices) adjacentes do mesmo pais que o aeroporto origem
	 * @param origem
	 * @return
	 */
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
