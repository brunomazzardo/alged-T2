package Model;

public class Rota {
	private CiaAerea cia;
	private Aeroporto origem;
	private Aeroporto destino;
	
	
	
	
	public Rota(CiaAerea cia, Aeroporto origem, Aeroporto destino) {
		this.cia = cia;
		this.origem = origem;
		this.destino = destino;
		
			
	}

	@Override
	public String toString()
	{
		return cia.getCodigo()+": "
				+origem.getCodigo()+" -> "+destino.getCodigo()
				+"]";
	}
	
	
	public CiaAerea getCia() {
		return cia;
	}
	
	public Aeroporto getDestino() {
		return destino;
	}
	
	public Aeroporto getOrigem() {
		return origem;
	}
	
	
}