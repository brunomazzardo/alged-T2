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

public class LeituraAeroporto {
	
	

	public  static void CarregaDados() {
		Path c1 = Paths.get("dat\\airports.dat");
		ArrayList<Aeroporto> aeroportos= new ArrayList<>();
		try {
			BufferedReader leitor = Files.newBufferedReader(c1, Charset.defaultCharset());
			
			String lAtual;
			leitor.readLine();
          
           while ((lAtual = leitor.readLine()) != null){
        	  
				Scanner sc = new Scanner(lAtual);
				sc.useDelimiter("[;\n]");

				String iataCode = sc.next();
				 sc.next();
			 sc.next();
				String airportName = sc.next();
				String identificador = sc.next();

				aeroportos.add(new Aeroporto(iataCode, airportName,identificador));

				sc.close();
			}

		} catch (IOException e) {

		}

	}

}
