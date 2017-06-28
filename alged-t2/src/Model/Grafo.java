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
			if (lv.get(i).getCodigo().equals(codigo)) {
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
			lv.add(origem);
			la.add(new LinkedList<>());
			origemIndex = la.size() - 1;
		}
		if (destinoIndex < 0) {
			lv.add(aeroporto);
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
		Aeroporto a = this.lv.get(nodo);
		for (int i = 0; i < this.la.size(); i++) {
			for (int j = 0; j < this.la.get(i).size(); j++) {
				if (this.la.get(i).get(j).getAeroporto().getCodigo().equals(a.getCodigo())) {
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
	public void determinarRotaDeMenorCusto(String codigo1, String codigo2) {
		Aeroporto origem = this.obterAeroportoPorCodigo(codigo1);
		Aeroporto destino = this.obterAeroportoPorCodigo(codigo2);
		if (origem == null || destino == null) {
			System.out.println("Aeroporto não encontrado.");
			return;
		}
		this.dijsktra(origem, destino);
	}

	/**
	 * Algoritimo de dijsktra recursivo
	 * 
	 * @param origem
	 * @param destino
	 */
	public void dijsktra(Aeroporto origem, Aeroporto destino) {
		ArrayList<Node> visitados = new ArrayList<Node>();
		this.distancias = new HashMap<Integer, Double>();
		distancias.put(this.findAirportIndex(origem.getCodigo()), 0.0);

		HashMap<String, ArrayList<Node>> caminho = new HashMap<String, ArrayList<Node>>();
		LinkedList<Node> adjacentes = this.obterAdjacentesDoMesmoPais(origem);
		Node u = new Node(null, origem);
		System.out.println("Mapeando rotas no país " + origem.getPais().getNome());
		this.dijsktraAux(u, adjacentes, visitados, distancias, caminho);
		System.out.println("Determinando melhor rota ...");
		this.menorRota = this.obterCaminho(caminho, new Node(null, destino));
	}

	/**
	 * Metodo auxiliar para o algoritimo de dijsktra recursivo
	 * 
	 * @param node
	 * @param adjacentes
	 * @param visitados
	 * @param distancias
	 * @param caminho
	 */
	public void dijsktraAux(Node node, LinkedList<Node> adjacentes, ArrayList<Node> visitados,
			HashMap<Integer, Double> distancias, HashMap<String, ArrayList<Node>> caminho) {
		visitados.add(node);
		for (int i = 0; i < adjacentes.size(); i++) {
			Node v = this.obterNodoComMenorDistancia(adjacentes, visitados, node.getAeroporto().getPais());
			if (v != null) {
				int vIndex = this.findAirportIndex(v.getAeroporto().getCodigo());
				int uIndex = this.findAirportIndex(node.getAeroporto().getCodigo());
				double vDist = this.obtemDistancia(distancias, vIndex);
				double uDist = this.obtemDistancia(distancias, uIndex) + v.getRota().getDistancia();
				if (vDist > uDist) {
					ArrayList<Node> a = new ArrayList<>();
					a.add(v);
					a.add(node);
					if (vDist == Double.MAX_VALUE) {
						distancias.put(vIndex, uDist);
						caminho.put(v.getAeroporto().getCodigo(), a);
					} else {
						distancias.put(vIndex, distancias.get(vIndex) + uDist);
						caminho.put(v.getAeroporto().getCodigo(), a);
					}
				}

				LinkedList<Node> adj = this.obterAdjacentesDoMesmoPais(v.getAeroporto());
				this.dijsktraAux(v, adj, visitados, distancias, caminho);
			}
		}
	}

	/**
	 * Obtem o caminho mais curto dado o vertice destino e os caminhos
	 * 
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
	 * Obtem distancia entre dois aeroportos (vertices) em um hashmap usando o
	 * indice do aerporto na L.V (lista de vertices), se o aeroporto nao esta
	 * nesse hashmap o adiciona com distancia de Double.MAX_VALUE
	 * 
	 * @param distancias
	 * @param key
	 * @return
	 */
	public double obtemDistancia(HashMap<Integer, Double> distancias, int key) {
		if (!distancias.containsKey(key)) {
			distancias.put(key, Double.MAX_VALUE);
		}
		return distancias.get(key);
	}

	/**
	 * Obtem nodo como a rota que tem a menor distancia entre os nodos
	 * adjacentes
	 * 
	 * @param nodes
	 * @return
	 */
	public Node obterNodoComMenorDistancia(LinkedList<Node> nodes, ArrayList<Node> visistados, Pais pais) {
		Node n = null;
		double d = Double.MAX_VALUE;
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getAeroporto().getPais().getCodigo().equals(pais.getCodigo())
					&& nodes.get(i).getRota().getDistancia() < d && !visistados.contains(nodes.get(i))) {
				n = nodes.get(i);
				d = nodes.get(i).getRota().getDistancia();
			}
		}
		return n;
	}

	/**
	 * Busca na L.V o aeroporto que possui determinado codigo
	 * 
	 * @param codigo
	 * @return
	 */
	public Aeroporto obterAeroportoPorCodigo(String codigo) {
		for (int i = 0; i < this.lv.size(); i++) {
			if (lv.get(i).getCodigo().equals(codigo)) {
				return lv.get(i);
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
			if (this.lv.get(i).getPais().getCodigo().equals(pais)) {
				int grauEntrada = this.grauDeEntrada(i);
				if (grauEntrada > maiorGrauEntrada) {
					maiorGrauEntrada = grauEntrada;
					aeroporto = this.lv.get(i);
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
