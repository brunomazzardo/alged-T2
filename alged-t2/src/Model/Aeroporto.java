package Model;

public class Aeroporto {
	private String codigo;
	private String nome;
	private Pais pais;
	private int quantidadeVoosChegada;
	private int quantidadeVoosPartida;
	
	public Aeroporto(String codigo, String nome, Pais pais) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.pais = pais;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public int getQuantidadeVoosChegada() {
		return quantidadeVoosChegada;
	}

	public void setQuantidadeVoosChegada(int quantidadeVoosChegada) {
		this.quantidadeVoosChegada = quantidadeVoosChegada;
	}

	public int getQuantidadeVoosPartida() {
		return quantidadeVoosPartida;
	}

	public void setQuantidadeVoosPartida(int quantidadeVoosPartida) {
		this.quantidadeVoosPartida = quantidadeVoosPartida;
	}

	@Override
	public String toString() {
		return "\nAeroporto [codigo=" + codigo + ", nome=" + nome +  ", pais=" + pais.getCodigo()
				+ "]";
	}
}