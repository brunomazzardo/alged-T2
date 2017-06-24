package seiLa;

import leitura.LeituraCiaAerea;
import leitura.LeituraGeral;
import java.util.ArrayList;

import Model.Aeroporto;
import Model.CiaAerea;
import Model.Pais;
import Model.Rota;

public class App{
	
	public static void main(String args[]){
	LeituraGeral lg = new LeituraGeral();
	
	ArrayList<Pais> paises = lg.getLp();
	ArrayList<CiaAerea> cias = lg.getLca();
	ArrayList<Aeroporto> aeroportos = lg.getLa(paises);
	ArrayList<Rota> rotas = lg.getLr(aeroportos,cias);
	
	//System.out.println(paises.toString());
	//System.out.println(cias.toString());
	//System.out.println(aeroportos.toString());
	//System.out.println(rotas.toString());
	
	/*for(int i=0; i<cias.size(); i++){
		//System.out.println(cias.get(i).getCodigo());
		if(cias.get(i).getCodigo().equals("GS")){
			System.out.println(cias.get(i).getCodigo());
		}
	}*/
	
	for(int i=0; i<rotas.size(); i++){
		if(rotas.get(i).getCia() == null){
			System.out.println(rotas.get(i).getDistancia());
		}
	}
	
	}
}
