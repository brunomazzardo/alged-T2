package app;

import leitura.LeituraCiaAerea;
import leitura.LeituraGeral;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import Model.Aeroporto;
import Model.CiaAerea;
import Model.Grafo;
import Model.Pais;
import Model.Rota;
import Model.Node;

public class App{
	
	private static Grafo grafo;

	public static void main(String args[]){
		try{
			LeituraGeral lg = new LeituraGeral();

			System.out.println("Lendo arquivos ... ");

			ArrayList<Pais> paises = lg.getLp();
			ArrayList<CiaAerea> cias = lg.getLca();
			ArrayList<Aeroporto> aeroportos = lg.getLa(paises);
			ArrayList<Rota> rotas = lg.getLr(aeroportos,cias);

			System.out.println("Construindo grafo ... ");
			grafo = new Grafo(rotas);

			//System.out.println(paises.toString());
			//System.out.println(cias.toString());
			//System.out.println(aeroportos.toString());
			//System.out.println(rotas.toString());

			Scanner scanner = new Scanner(System.in);
			int opt = 1;

			while (opt != 0) {
				System.out.println("Digite: " + "\n 1- Para encontrar a menor rota entre dois aeroportos"
						+ "\n 2- Para procurar uma rota exclusiva de uma companhia aérea" 
						+ "\n 3- Para verificar a autonomia de voo de uma avião"
						+ "\n 4- Para verificar a probabilidade de congestionamento de um aeroporto");
		
				opt = scanner.nextInt();

				switch (opt) {
				case 1:
					determinarRotaDeMenorCusto();;
					break;
				case 2:
					verificarRotaExclusiva();;
					break;
				case 3:
					verificarAutonomiaDeVoo();;
					break;
				case 4:
					verificarProbabilidadeDeCongestionamento();;
					break;			
				default:
					System.out.println("Opção inválida.");
					break;
				}
			}

		} catch (InputMismatchException e) {
			System.out.println("Opção inválida.\nEncerrando.");
		}

	}

	/**
	 * Determinar a rota de menor custo, em termos de distância total,
	 * entre dois aeroportos fornecidos pelo usuário (considerar aeroportos e
	 * rotas apenas no mesmo país). Quais os nomes das companhias aéreas
	 * que realizam essa rota.
	 */
	public static void determinarRotaDeMenorCusto(){
		try{
			Scanner s = new Scanner(System.in);
			System.out.println("\nInforme o código do primeiro aeroporto:\n");
			String id1 = s.next();
			System.out.println("\nInforme o código do segundo aeroporto:\n");
			String id2 = s.next();
			ArrayList<Node> rota = grafo.determinarRotaDeMenorCusto(id1,id2);
			if(rota == null || rota.size() == 0){
				System.out.println("\nNão há uma rota entre esses dois aeroportos\n");
			}else{
				//Exibe rota
				boolean exibirDistancia = false;
				for(int i=rota.size()-1; i>=0; i--){
					if(exibirDistancia){
						System.out.print("Companhia: "+rota.get(i).getRota().getCia().getNome());
						System.out.println(" - Distancia entre os aeroportos: "+rota.get(i).getRota().getDistancia());
					}
					System.out.println("Aeroporto: "+rota.get(i).getAeroporto().getCodigo()+" - "+rota.get(i).getAeroporto().getNome() +" ("+rota.get(i).getAeroporto().getPais().getCodigo()+")");
					exibirDistancia = true;
					
				}				
			}			
		} catch (InputMismatchException e) {
			System.out.println("\nInformação inválida:\n");
		}
	}

	/**
	 * Fornecer o nome de uma companhia aérea e verificar se ela realiza
	 * uma rota exclusiva entre dois aeroportos. Se existir, informar a rota
	 * com as conexões.
	 */
	public static void verificarRotaExclusiva(){
		try{
			Scanner s = new Scanner(System.in);
			System.out.println("\nInforme o nome de uma companhia aérea:\n");
			String nome = s.next();
		
			System.out.println("\nInforme o nome de uma origem:\n");
			String origem= s.next();
			
			System.out.println("\nInforme o nome de um destino:\n");
			String destino = s.next();
			
			ArrayList<Node> rota = grafo.verificarRotaExclusiva(nome,origem,destino);
			if(rota == null || rota.size() == 0){
				System.out.println("\nNão há uma rota entre esses dois aeroportos\n");
			}else{
				//Exibe rota
				boolean exibirDistancia = false;
				for(int i=rota.size()-1; i>=0; i--){
					if(exibirDistancia){
						System.out.print("Companhia: "+rota.get(i).getRota().getCia().getNome());
						System.out.println(" - Distancia entre os aeroportos: "+rota.get(i).getRota().getDistancia());
					}
					System.out.println("Aeroporto: "+rota.get(i).getAeroporto().getCodigo()+" - "+rota.get(i).getAeroporto().getNome() +" ("+rota.get(i).getAeroporto().getPais().getCodigo()+")");
					exibirDistancia = true;
					
				}				
			}			
		} catch (InputMismatchException e) {
			System.out.println("\nInformação inválida:\n");
		} catch (Exception e) {
			System.out.println("\nOcorreu um erro:\n" + e.getMessage() + "\n");
		}
	}

	/**
	 * Uma companhia deseja adquirir um avião de médio porte, com uma
	 * determinada autonomia de voo (fornecida em Km). A tomada de
	 * decisão da companhia está baseada no percentual de suas rotas onde
	 * poderá utilizar esse avião. Se esse percentual for superior a 70%,
	 * então a compra está autorizada. Fornecer a autonomia de voo de um
	 * avião e informar se a compra será realizada ou não.
	 */
	public static void verificarAutonomiaDeVoo(){
		
		try{
			Scanner s = new Scanner(System.in);
			System.out.println("\nInforme o código da ciaAerea:\n");
			String codCIA = s.next();
			System.out.println("\nInforme a autonomia do avião:\n");
			double id2 = s.nextDouble();
			boolean foi=grafo.verificaAutonomiaVoo(id2, codCIA);
		
			if(foi){
				System.out.println("\nConseguiu\n");
			}else{
				System.out.println("Putz,não deu");
			}			
		} catch (InputMismatchException e) {
			System.out.println("\nInformação inválida:\n");
		}
	}
	

	/**
	 * Qual o aeroporto, de um determinado país, que possui a maior
	 * probabilidade de congestionamento no futuro? Como medida utiliza-se
	 * o número total de voos que chegam no aeroporto.
	 */
	public static void verificarProbabilidadeDeCongestionamento(){
		try{
			Scanner s = new Scanner(System.in);
			System.out.println("\nInforme o código de um pais:\n");
			String nome = s.next();
			
			Aeroporto a = grafo.verificarProbabilidadeDeCongestionamento(nome);
			if(a == null){
				System.out.println("Este país não possui aeroportos");
			}else{
				System.out.println("O aeroporto "+a.getNome()+" possui a maior probabilidade de congestionamento"
						+ "no futuro \n com o total de "+a.getQuantidadeVoosChegada()+" voos que chegam.");
			}
		} catch (InputMismatchException e) {
			System.out.println("\nInformação inválida:\n");
		} catch (Exception e) {
			System.out.println("\nOcorreu um erro:\n" + e.getMessage() + "\n");
		}
	}

}
