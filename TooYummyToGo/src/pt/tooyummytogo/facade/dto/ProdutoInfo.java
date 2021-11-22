package pt.tooyummytogo.facade.dto;

public class ProdutoInfo {

	private String nome;
	private Double preco;
	
	public ProdutoInfo(String nome, Double preco) {
		this.nome = nome;
		this.preco = preco;
	}
	
	public Double getPreco() {
		return preco;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String toString() {
		return nome;
	}

}
