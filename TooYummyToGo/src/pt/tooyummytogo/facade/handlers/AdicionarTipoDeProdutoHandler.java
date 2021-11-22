package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.domain.Comerciante;


public class AdicionarTipoDeProdutoHandler {
	
	private Comerciante c;


	public AdicionarTipoDeProdutoHandler(Comerciante c) {
		this.c = c;
	}

	/**
	 * Regista tipo de produto com o nome e o preco dados
	 * @param nome - nome do tipo de produto a registar
	 * @param d - preço do tipo de produto a registar
	 * @ensures existe um tipo de produto com o nome dado e o preço dado
	 */
	public void registaTipoDeProduto(String nome, double d) {
		c.adicionaTipoProduto(nome, d);			
	}

}
