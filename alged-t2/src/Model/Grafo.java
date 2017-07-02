package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Grafo dirigido com arestas valoradas representado pela Lista de Adjacência.
 * 
 * Autor: alunos da turma 137 - Alg. e Est. Dados II - ES Data: 23/06/2017
 * 
 */
public class Grafo {

	private LinkedList<LinkedList<Node>> la = new LinkedList<LinkedList<Node>>();
	private ArrayList<Node> lv = new ArrayList<Node>();

	private LinkedList<Node> menorRota;
	private HashMap<Integer, Double> distancias;
	
	private class Caminho{
		int vertice;
		double peso;
		int predecessor;
		
		public Caminho(int vertice, double peso, int predecessor) {
			super();
			this.vertice = vertice;
			this.peso = peso;
			this.predecessor = predecessor;
		}
	}

	public LinkedList<LinkedList<Node>> getLa() {
		return la;
	}

	public void setLa(LinkedList<LinkedList<Node>> la) {
		this.la = la;
	}

	public ArrayList<Node> getLv() {
		return lv;
	}

	public void setLv(ArrayList<Node> lv) {
		this.lv = lv;
	}

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
	 * 
	 * @param rotas
	 */
	public Grafo(ArrayList<Rota> rotas) {
		for (int i = 0; i < rotas.size(); i++) {
			Aeroporto origem = rotas.get(i).getOrigem();
			Aeroporto destino = rotas.get(i).getDestino();
			Rota rota = rotas.get(i);

			if (origem != null && destino != null) {
				this.addAresta(origem, destino, rota);
			}
		}
	}

	/**
	 * Encontra o indice de um aeroporto na lv (lista de vértices)
	 * 
	 * @param codigo
	 * @return
	 */
	public int findAirportIndex(String codigo) {
		for (int i = 0; i < lv.size(); i++) {
			if (lv.get(i).getAeroporto().getCodigo().equals(codigo)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Adiciona nodos na L.A
	 * 
	 * @param origem
	 * @param aeroporto
	 * @param rota
	 */
	public void addAresta(Aeroporto origem, Aeroporto aeroporto, Rota rota) {
		int origemIndex = this.findAirportIndex(origem.getCodigo());
		int destinoIndex = this.findAirportIndex(aeroporto.getCodigo());

		// Se o aeroporto de origem não existe na L.V o adiciona na L.V e
		// adciona uma lista na L.A
		if (origemIndex < 0) {
			lv.add(new Node(rota, aeroporto));
			la.add(new LinkedList<>());
			origemIndex = la.size() - 1;
		}
		if (destinoIndex < 0) {
			lv.add(new Node(rota, aeroporto));
			la.add(new LinkedList<>());
		}
		// Adiciona aeroporto destino na lista do aeroporto origem
		boolean contains = false;
		for (int i = 0; i < this.la.get(origemIndex).size(); i++) {
			if (this.la.get(origemIndex).get(i).getAeroporto().getCodigo().equals(aeroporto.getCodigo())) {
				contains = true;
			}
		}
		if (!contains) {
			la.get(origemIndex).add(new Node(rota, aeroporto));
		}
	}

	/**
	 * Obtem grau de saida de um nodo
	 * 
	 * @param nodo
	 * @return
	 */
	public int grauDeSaida(int nodo) {
		return la.get(nodo).size();
	}

	/**
	 * Obtem grau de entrada de um nodo
	 * 
	 * @param nodo
	 * @return
	 */
	public int grauDeEntrada(int nodo) {
		int grau = 0;
		Node a = this.lv.get(nodo);
		for (int i = 0; i < this.la.size(); i++) {
			for (int j = 0; j < this.la.get(i).size(); j++) {
				if (this.la.get(i).get(j).getAeroporto().getCodigo().equals(a.getAeroporto().getCodigo())) {
					grau++;
				}
			}
		}
		return grau;
	}

	public int getArestas() {
		int cont = 0;
		for (int i = 0; i < la.size(); i++) {
			cont += la.get(i).size();
		}
		return cont;
	}

	/**
	 * Obtem a menor rota entre dois aeroportos
	 * 
	 * @param codigo1
	 * @param codigo2
	 */
	public ArrayList<Node> determinarRotaDeMenorCusto(String codigo1, String codigo2) {
		Aeroporto origem = this.obterAeroportoPorCodigo(codigo1);
		Aeroporto destino = this.obterAeroportoPorCodigo(codigo2);
		if (origem == null || destino == null) {
			System.out.println("Aeroporto não encontrado.");
			return null;
		}
		int origemIndex = this.findAirportIndex(origem.getCodigo());
		int destinoIndex = this.findAirportIndex(destino.getCodigo());
		
		ArrayList<Caminho> caminhos = this.dijsktra2(origem, destino, origemIndex, destinoIndex);
		return this.obterMenorRota(caminhos, origemIndex, destinoIndex);
	}

	/**
	 * 
	 * @param pais
	 * @return
	 */
	public ArrayList<Node> getVerticesDoMesmoPais(Pais pais){
		ArrayList<Node> vertices = new ArrayList<Node>();
		for(int i=0; i<this.lv.size(); i++){
			if(this.lv.get(i).getAeroporto().getPais().getCodigo().equals(pais.getCodigo())){
				vertices.add(this.lv.get(i));
			}
		}
		return vertices;
	}
	
	/**
	 * Algoritimo de dijsktra 
	 * @param origem
	 * @param destino
	 * @param origemIndex
	 * @param destinoIndex
	 * @return
	 */
	public ArrayList<Caminho> dijsktra2(Aeroporto origem, Aeroporto destino, int origemIndex, int destinoIndex) {
		ArrayList<Caminho> caminhos = new ArrayList<Caminho>();
		ArrayList<Integer> visitados = new ArrayList<Integer>();
		LinkedList<Node> adjacentes = this.obterAdjacentesDoMesmoPais(origem);
		
		visitados.add(this.findAirportIndex(origem.getCodigo()));
		caminhos.add(new Caminho(origemIndex, 0, origemIndex));
		
		//Montar caminhos		
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for(int i=0; i<adjacentes.size(); i++){
			Node a = adjacentes.get(i);
			int index = this.findAirportIndex(a.getAeroporto().getCodigo());
			indexes.add(index);
			caminhos.add(new Caminho(index,a.getRota().getDistancia(),origemIndex));
		}
		
		ArrayList<Node> vertices = this.getVerticesDoMesmoPais(origem.getPais());
		for(int i=0; i<vertices .size(); i++){
			//Se não é adjacente do aeroporto origem já adicionado
			if(!indexes.contains(i)){
				caminhos.add(new Caminho(this.findAirportIndex(vertices.get(i).getAeroporto().getCodigo()),Double.MAX_VALUE,origemIndex));
			}
		}
		
		//Percorrer todos os vertices do mesmo país
		for(int i=0; i<lv.size(); i++){
			//Se é do mesmo país e não é a origem
			if(lv.get(i).getAeroporto().getPais().getCodigo().equals(origem.getPais().getCodigo()) && i!=origemIndex){
				//Marca vertice U
				visitados.add(i);
				//Obtem adjacentes do vertice U
				int indexU = this.findAirportIndex(lv.get(i).getAeroporto().getCodigo());
				LinkedList<Node> adj = this.obterAdjacentesDoMesmoPais(origem);
				
				for(int j=0; j<adj.size(); j++){
					if(!adj.get(j).getAeroporto().getCodigo().equals(origem.getCodigo())){
						int indexZ = this.findAirportIndex(adj.get(j).getAeroporto().getCodigo());
						//Se vertice Z não foi marcado
						if(!visitados.contains(indexZ)){
							//Peso do vertice U mais distancia de U->Z
							int iU = this.getIndexFromCaminhosByVertice(indexU, caminhos);
							int iZ = this.getIndexFromCaminhosByVertice(indexZ, caminhos);
							if(iZ == -1){
								iZ = -1;
							}
							if(iU == -1){
								iU = -1;
							}
							double pesoUPlusRotaDistancia = caminhos.get(iU).peso+adj.get(j).getRota().getDistancia();
							//Se (Peso do vertice U mais distancia de U->Z) < peso do vertice Z
							if(pesoUPlusRotaDistancia < caminhos.get(iZ).peso){
								//Atualiza peso do vertice Z e seu predecessor passa a ser o vertice U
								caminhos.get(iZ).peso = pesoUPlusRotaDistancia;
								caminhos.get(iZ).predecessor = indexU;
							}
						}
					}
				}
			}
		}		
		return caminhos;
	}
	
	public int getIndexFromCaminhosByVertice(int verticeIndex, ArrayList<Caminho> caminhos){
		for(int i=0; i<caminhos.size(); i++){
			if(caminhos.get(i).vertice == verticeIndex){
				return i;
			}
		}
		return -1;
	}
	
	public ArrayList<Node> obterMenorRota(ArrayList<Caminho> caminhos, int origemIndex, int destinoIndex){
		ArrayList<Node> vertices = new ArrayList<Node>();
		vertices.add(lv.get(destinoIndex));
		int predecessor = 0;
		//Obter predecessor do vertice destino
		for(int i=0; i<caminhos.size(); i++){
			if(caminhos.get(i).vertice == destinoIndex){
				predecessor = caminhos.get(i).predecessor;
				break;
			}
		}
		//Obter vertices ate a origem
		while(predecessor != origemIndex){
			for(int i=0; i<caminhos.size(); i++){
				if(caminhos.get(i).vertice == predecessor){
					int index = caminhos.get(i).vertice;
					vertices.add(lv.get(index));
					predecessor = caminhos.get(i).predecessor; 
				}
			}
		}
		vertices.add(this.lv.get(origemIndex));
		return vertices;
	}

	/**
	 * Busca na L.V o aeroporto que possui determinado codigo
	 * 
	 * @param codigo
	 * @return
	 */
	public Aeroporto obterAeroportoPorCodigo(String codigo) {
		for (int i = 0; i < this.lv.size(); i++) {
			if (lv.get(i).getAeroporto().getCodigo().equals(codigo)) {
				return lv.get(i).getAeroporto();
			}
		}
		return null;
	}

	/**
	 * Obtem aeroportos (vertices) adjacentes do mesmo pais que o aeroporto
	 * origem
	 * 
	 * @param origem
	 * @return
	 */
	public LinkedList<Node> obterAdjacentesDoMesmoPais(Aeroporto origem) {
		LinkedList<Node> adj = this.la.get(this.findAirportIndex(origem.getCodigo()));
		for (int i = 0; i < adj.size(); i++) {
			if (!adj.get(i).getAeroporto().getPais().getCodigo().equals(origem.getPais().getCodigo())) {
				adj.remove(i);
			}
		}
		return adj;
	}

	/**
	 * Verifica a possibilidade de fazer uma rota entre dois aeroportodos usando
	 * somente uma companhia aerea;
	 * 
	 * @param aeroporto1
	 * @param aeroporto2
	 * @param ciaNome
	 */
	public void verificarRotaExclusiva(String ciaNome, String aeroporto1, String aeroporto2) {
		Aeroporto origem = this.obterAeroportoPorCodigo(aeroporto1);
		Aeroporto destino = this.obterAeroportoPorCodigo(aeroporto2);
		if (origem == null || destino == null) {
			System.out.println("Aeroporto não encontrado.");
			return;
		}
		/*
		 * Esperando o ricardo arrumar o dijsktra para poder fazer essa,já que
		 * vou reaproveitar o método dele
		 */
	}

	public boolean verificaAutonomiaVoo(double autonomia, String codCIA) {
		int quantVoosCia = 0;
		int quantVoosCiaConsegue = 0;
		double distTotal = 0;

		for (int i = 0; i < la.size(); i++) {
			for (int j = 0; j < la.get(i).size(); j++) {

				if (la.get(i).get(j).getRota().getCia().getCodigo().equals(codCIA)) {
					quantVoosCia++;
					distTotal += la.get(i).get(j).getRota().getDistancia();
					if (la.get(i).get(j).getRota().getDistancia() < autonomia) {
						quantVoosCiaConsegue++;

					}

				}
			}
		}

		/*
		 * É necessario ainda implementas frufrus para o método,como quanto
		 * faltou para completar,quantidades de rotas que conseguiu e arrumar a
		 * formatação dos dados.É Nois.
		 */
		if (quantVoosCia * 0.70 > quantVoosCiaConsegue)
			return false;

		return true;
	}

	/**
	 * Obtem o maior grau de entrada entre os aeroporto de um determinado pais
	 * 
	 * @param pais
	 */
	public Aeroporto verificarProbabilidadeDeCongestionamento(String pais) {
		Aeroporto aeroporto = null;
		int maiorGrauEntrada = 0;
		for (int i = 0; i < this.lv.size(); i++) {
			if (this.lv.get(i).getAeroporto().getPais().getCodigo().equals(pais)) {
				int grauEntrada = this.grauDeEntrada(i);
				if (grauEntrada > maiorGrauEntrada) {
					maiorGrauEntrada = grauEntrada;
					aeroporto = this.lv.get(i).getAeroporto();
				}
			}
		}
		aeroporto.setQuantidadeVoosChegada(maiorGrauEntrada);
		return aeroporto;
	}

	@Override
	public String toString() {
		String toString = "";
		for (int i = 0; i < la.size(); i++) {
			toString += la.get(i).toString() + "\n";
		}
		return toString;
	}

}
