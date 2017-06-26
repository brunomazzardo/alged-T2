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

public class LeituraRota {

	public  ArrayList<Rota> CarregaDados(ArrayList<Aeroporto> aeroportos, ArrayList<CiaAerea> cias) {
		Path c1 = Paths.get("dat\\testToTXT\\routes.txt");
		ArrayList<Rota> rotas = new ArrayList<>();
		try {
			BufferedReader leitor = Files.newBufferedReader(c1, Charset.defaultCharset());

			String lAtual;
			leitor.readLine();

			while ((lAtual = leitor.readLine()) != null) {

				Scanner sc = new Scanner(lAtual);
				sc.useDelimiter("[;\n]");

				String origemCodigo = sc.next();
				String destinoCodigo = sc.next();
				double distancia = Double.parseDouble(sc.next());
				String ciaCodigo = sc.next();
				
				//procura aeroportos origem e destino para associar com a rota
				Aeroporto origem = null;
				Aeroporto destino = null;
				for(int i=0; i<aeroportos.size(); i++){
					if(aeroportos.get(i).getCodigo().equals(origemCodigo)){
						origem = aeroportos.get(i);
					}else if(aeroportos.get(i).getCodigo().equals(destinoCodigo)){
						destino = aeroportos.get(i);
					}else if(origem != null && destino != null){
						break;
					}
				}
				
				//procura cia aerea para associar com a rota
				CiaAerea cia = null;
				for(int i=0; i<cias.size(); i++){
					if(cias.get(i).getCodigo().equals(ciaCodigo)){
						cia = cias.get(i);
						break;
					}
				}

				rotas.add(new Rota(origem,destino,distancia,cia));

				sc.close();
			}

		} catch (IOException e) {

		}
		return rotas;

	}

}
