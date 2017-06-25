package Model;

import java.util.Comparator;

public class Node implements Comparator<Node>{
	private Rota rota;
	private Aeroporto aeroporto;

	public Node(Rota rota, Aeroporto aeroporto) {
		super();
		this.rota = rota;
		this.aeroporto = aeroporto;
	}

	public Rota getRota() {
		return rota;
	}

	public void setRota(Rota rota) {
		this.rota = rota;
	}

	public Aeroporto getAeroporto() {
		return aeroporto;
	}

	public void setAeroporto(Aeroporto aeroporto) {
		this.aeroporto = aeroporto;
	}

	@Override
	public String toString() {
		return "Node [rota=" + rota.getOrigem().getCodigo() + " - " + rota.getDestino().getCodigo() + "]";
	}

	@Override
	public int compare(Node n1, Node n2) {
		if(n1.getRota().getDistancia() < n1.getRota().getDistancia()){
			return -1;
		}else if(n1.getRota().getDistancia() > n1.getRota().getDistancia()){
			return 1;
		}
		return 0;
	}
}
