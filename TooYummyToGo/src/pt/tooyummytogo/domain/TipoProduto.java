package pt.tooyummytogo.domain;

public class TipoProduto {
	
	private String nome;
	private Double preco;
	

	public TipoProduto(String nome, double d) {
		this.nome = nome;
		this.preco = d;
	}
	

	public String getNome() {
		return nome;
	}


	public Double getPreco() {
		return preco;
	}
	

	@Override
	public String toString() {
		return nome;
	}

}
