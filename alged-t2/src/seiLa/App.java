package seiLa;

import leitura.LeituraCiaAerea;
import leitura.LeituraGeral;
import java.util.ArrayList;

import Model.Aeroporto;
import Model.CiaAerea;
import Model.Grafo;
import Model.Pais;
import Model.Rota;

public class App{
	
	public static void main(String args[]){
	LeituraGeral lg = new LeituraGeral();
	
	System.out.println("Lendo arquivos ... ");
	
	ArrayList<Pais> paises = lg.getLp();
	ArrayList<CiaAerea> cias = lg.getLca();
	ArrayList<Aeroporto> aeroportos = lg.getLa(paises);
	ArrayList<Rota> rotas = lg.getLr(aeroportos,cias);
	
	System.out.println("Construindo grafo ... ");
	Grafo grafo = new Grafo(rotas);
	
	System.out.println(grafo.grauDeSaida(1));
	
	//System.out.println(paises.toString());
	//System.out.println(cias.toString());
	//System.out.println(aeroportos.toString());
	//System.out.println(rotas.toString());
	
	}
}
