package Model;

public class Rota {
	private Aeroporto origem;
	private Aeroporto destino;
	private double distancia;
	private CiaAerea cia;
	
	public Rota(Aeroporto origem, Aeroporto destino, double distancia, CiaAerea cia) {
		super();
		this.origem = origem;
		this.destino = destino;
		this.distancia = distancia;
		this.cia = cia;
	}

	public Aeroporto getOrigem() {
		return origem;
	}

	public void setOrigem(Aeroporto origem) {
		this.origem = origem;
	}

	public Aeroporto getDestino() {
		return destino;
	}

	public void setDestino(Aeroporto destino) {
		this.destino = destino;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public CiaAerea getCia() {
		return cia;
	}

	public void setCia(CiaAerea cia) {
		this.cia = cia;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Origem: "+this.getOrigem().getCodigo());
		sb.append("Destino: "+this.getDestino().getCodigo());
		sb.append("Distancia: "+this.getDistancia());
		sb.append("Origem: "+this.getCia().getCodigo());
		return sb.toString();
	}
}