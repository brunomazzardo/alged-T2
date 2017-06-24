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
import Model.CiaAerea;
import Model.Rota;

public class LeituraCiaAerea {

	public  ArrayList<CiaAerea> CarregaDados() {
		Path c1 = Paths.get("dat\\airlines.dat");
		ArrayList<CiaAerea> cias = new ArrayList<>();
		try {
			BufferedReader leitor = Files.newBufferedReader(c1, Charset.defaultCharset());

			String lAtual;
			leitor.readLine();

			while ((lAtual = leitor.readLine()) != null) {

				Scanner sc = new Scanner(lAtual);
				sc.useDelimiter("[;\n]");

				String codigo = sc.next();
				String nome = sc.next();

				cias.add(new CiaAerea(codigo, nome));

				sc.close();
			}

		} catch (IOException e) {
			System.out.println("Error on LeituraCiaAerea: "+e.getMessage());
		}
		return cias;

	}

}
