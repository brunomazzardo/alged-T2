package Model;

public class Aeroporto {
	private String codigo;
	private String nome;
	
	private String identificador;
	
	public Aeroporto(String codigo, String nome,String identificador) {
		this.codigo = codigo;
		this.nome = nome;
		
		this.identificador=identificador;
				
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNome() {
		return nome;
	}
	


	@Override
	public String toString() {
		return "Aeroporto [codigo=" + codigo + ", nome=" + nome +  ", identificador=" + identificador
				+ "]";
	}
}