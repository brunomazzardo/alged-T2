package leitura;

import java.util.ArrayList;

import Model.Aeroporto;
import Model.CiaAerea;
import Model.Pais;
import Model.Rota;

public class LeituraGeral {
	private LeituraAeroporto la;
	private LeituraCiaAerea lca;
	private LeituraPais lp;
	private LeituraRota lr;

	public LeituraGeral(){
		la=new LeituraAeroporto();
		lca=new LeituraCiaAerea();
		lp=new LeituraPais();
		lr=new LeituraRota();
	}
	
	public ArrayList<Aeroporto> getLa(ArrayList<Pais> paises) {
		return la.CarregaDados(paises);
	}
	
	public ArrayList<CiaAerea> getLca() {
		return lca.CarregaDados();
	}
	
	public ArrayList<Pais> getLp() {
		return lp.CarregaDados();
	}
	
	public ArrayList<Rota> getLr(ArrayList<Aeroporto> aeroportos, ArrayList<CiaAerea> cias) {
		return lr.CarregaDados(aeroportos,cias);
	}

	
	
	

}
