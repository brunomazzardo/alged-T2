package leitura;

import java.util.ArrayList;

import Model.Aeroporto;

public class LeituraGeral {
	private LeituraAeroporto la;

	public LeituraGeral(){
		la=new LeituraAeroporto();
	}
	public ArrayList<Aeroporto> getLa() {
		return la.CarregaDados();
	}

	
	
	

}
