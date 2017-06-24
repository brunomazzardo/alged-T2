package leitura;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Aeroporto;
import Model.Pais;

public class LeituraAeroporto {

	public  ArrayList<Aeroporto> CarregaDados(ArrayList<Pais> paises) {
		Path c1 = Paths.get("dat\\airports.dat");
		ArrayList<Aeroporto> aeroportos = new ArrayList<>();
		try {
			BufferedReader leitor = Files.newBufferedReader(c1, Charset.defaultCharset());

			String lAtual;
			leitor.readLine();

			while ((lAtual = leitor.readLine()) != null) {

				Scanner sc = new Scanner(lAtual);
				sc.useDelimiter("[;\n]");

				String iataCode = sc.next();
				sc.next();
				sc.next();
				String airportName = sc.next();
				String paisCodigo = sc.next();
				
				//procura pais para fazer associação
				Pais pais = null;
				for(int i=0; i<paises.size(); i++){
					if(paises.get(i).getCodigo().equals(paisCodigo)){
						pais = paises.get(i);
						break;
					}
				}
				
				aeroportos.add(new Aeroporto(iataCode, airportName, pais));

				sc.close();
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return aeroportos;

	}

}
