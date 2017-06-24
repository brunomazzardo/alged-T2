package Model;

public class Aeroporto {
	private String codigo;
	private String nome;
	private Pais pais;
	
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

	@Override
	public String toString() {
		return "Aeroporto [codigo=" + codigo + ", nome=" + nome +  ", pais=" + pais.getCodigo()
				+ "]";
	}
}