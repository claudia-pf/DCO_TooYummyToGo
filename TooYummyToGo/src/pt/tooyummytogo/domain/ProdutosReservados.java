package pt.tooyummytogo.domain;

public class ProdutosReservados {

	private ProdutosDia pdia;
	private int quantidade;


	public ProdutosReservados(ProdutosDia pdia, int quantidade) {

		this.pdia = pdia;
		this.quantidade = quantidade;
	}

	/**
	 * Atualiza a quantidade do produto do dia associado a este produto reservado
	 * @requires usar apenas quando ja se sabe que nao se vai acrescentar mais quantidade deste tipo de produto
	 */
	public void atualizaProdDia() {
		pdia.diminuiQuantidada(quantidade);
	}


	public String getNome() {
		return pdia.getNome();
	}


	public int getQuantidade() {
		return this.quantidade;
	}

	/**
	 * Aumenta a quantidade do produto reservado, consoante a quantidade dada
	 * @param quantidade dada
	 */
	public void acrescentaQuantidade(int quantidade) {
		this.quantidade += quantidade;
		
	}
	
}
