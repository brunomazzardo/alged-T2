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
		Rota rota;
		Aeroporto aeroporto;
	
		public Node(Rota rota, Aeroporto aeroporto) {
			super();
			this.rota = rota;
			this.aeroporto = aeroporto;
		}

		@Override
		public String toString() {
			return "Node [rota=" + rota.getOrigem().getCodigo() + " - " + rota.getDestino().getCodigo() + "]";
		}
	}
	
	//public Grafo(ArrayList<Rota> rotas, ArrayList<Aeroporto> aeroportos){
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
	
	public int findAirportIndex(String codigo){
		for(int i=0; i<lv.size(); i++){
			if((lv.size()-1 <= i) && lv.get(i).getCodigo().equals(codigo)){
				return i;
			}
		}
		return -1;
	}

	public void addAresta(Aeroporto origem, Aeroporto aeroporto, Rota rota) {
		int origemIndex = this.findAirportIndex(origem.getCodigo());
		
		if(origemIndex < 0){
			lv.add(origem);
			la.add(new LinkedList<>());
			origemIndex = la.size()-1;
		}
		
		la.get(origemIndex).add(new Node(rota, aeroporto));
	}
		
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
	
	/*public int getAdjacenteProximo(int nodo){
		if(la.get(nodo).size() == 0){
			return -1;
		}
		int vDest = la.get(nodo).get(0).vDest;
		int menorPeso = la.get(nodo).get(0).peso;
		for(int i = 0; i < la.get(nodo).size(); i++){
			if(menorPeso > la.get(nodo).get(i).peso){
				menorPeso = la.get(nodo).get(i).peso;
				vDest = la.get(nodo).get(i).vDest;
			}
		}
		return vDest;
	}*/
	
	//	
//	public List<Integer> maiorGrau(){
//		List<Integer> graus = new ArrayList<>();
//		int maiorGrau = 0;
//		for (int i = 0; i < ma.length; i++) {
//			int grau = grau(i);
//			if(grau > maiorGrau){
//				graus = new ArrayList<>();
//				maiorGrau = grau;
//				graus.add(i);
//			}else if (grau == maiorGrau){
//				graus.add(i);
//			}
//		}		
//		return graus;
//	}
//	
//	//percorre abaixo da diagonal principal
//	public int contAresta(){
//		int i, j;
//		int numAresta = 0;
//		for (i=1; i<ma.length; i++) {
//			for (j=0; j<=i; j++) {
//				if (ma[i][j] == 1)
//					numAresta++;
//			}
//			
//		}
//		return numAresta;
//	}
//	
//	public ArrayList<Integer> percursoAmplitude(int nodoInicial) {
//		ArrayList<Integer> listaAmplitude = new ArrayList<>();
//		ArrayList<Integer> fila = new ArrayList<>();
//		
//		fila.add(nodoInicial);
//		listaAmplitude.add(nodoInicial);
//		while(!fila.isEmpty()){
//			int nodo = fila.remove(0);		
//			for (int i = 0; i < ma.length; i++) {
//				if(ma[nodo][i] == 1){
//					if(!listaAmplitude.contains(i)){
//						fila.add(i);
//						listaAmplitude.add(i);
//					}
//				}
//			}
//		}
//		return listaAmplitude;
//	}
//	
//	public List<Integer> percursoProfundidade(int nodoInicial) {
//		List<Integer> lista = new ArrayList<>();
//		percursoProfundidade(nodoInicial, lista);
//		return lista;
//	}
//
//	private void percursoProfundidade(int nodo, List<Integer> percurso) {
//		percurso.add(nodo);
//		for (int i = 0; i < ma.length; i++) {
//			if (ma[nodo][i] != 0) {
//				if (!percurso.contains(i)) { // Evita ciclos!
//					percursoProfundidade(i, percurso);
//				}
//			}
//		}
//	}
	
	@Override
	public String toString() {
		String toString ="";
		for (int i = 0; i < la.size(); i++) {
			toString+= la.get(i).toString() + "\n";
		}
		return toString;
	}

	
	
}
