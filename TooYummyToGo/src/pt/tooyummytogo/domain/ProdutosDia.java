package pt.tooyummytogo.domain;

public class ProdutosDia {
	
	private int quantidade;
	private TipoProduto tp;
	

	public ProdutosDia(TipoProduto tp, int qtdd) {
		this.quantidade = qtdd;
		this.tp = tp;
	}


	public String getNome() {
		return tp.getNome();
	}


	public int getQuantidade() {
		return quantidade;
	}


	public Double getPreco() {
		return tp.getPreco();
		
	}
	

	@Override
	public String toString() {
		return getNome() + ", " + quantidade;
	}

	/**
	 * Diminui 'qtdd' à quantidade do produto do dia
	 * @param qtdd - quantidade a ser subtraida à quantidade que este produto tem de momento
	 */
	public void diminuiQuantidada(int qtdd) {
		this.quantidade -= qtdd;
		
	}

}
