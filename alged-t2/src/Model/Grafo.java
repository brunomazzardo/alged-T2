package Model;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
	
	static class Node {
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
			if((lv.size()-1 <= i) && lv.get(i).getCodigo().equals(codigo)){
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
