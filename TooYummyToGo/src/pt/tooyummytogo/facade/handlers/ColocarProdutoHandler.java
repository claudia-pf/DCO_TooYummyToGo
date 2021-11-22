package pt.tooyummytogo.facade.handlers;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.ProdutosDia;
import pt.tooyummytogo.domain.TipoProduto;
import pt.tooyummytogo.exceptions.NaoExisteTipoProdException;


public class ColocarProdutoHandler {

	private Comerciante c;
	private ArrayList<ProdutosDia> produtosAdicionar;


	public ColocarProdutoHandler(Comerciante c) {
		this.c = c;
		produtosAdicionar = new ArrayList<ProdutosDia>();
	}

	/**
	 * Devolve lista de tipos de produtos associados ao comerciante
	 * @return lista com os nomes dos tipos de produtos associados ao comerciante
	 */
	public List<String> inicioDeProdutosHoje() {
		return c.getListaTiposProduto();
	}



	/**
	 * Cria produto do dia com nome e quantidade dadas e adiciona-o à lista de produtos a meter para venda
	 * @param nome - nome do produto a adicionar
	 * @param qtdd - quantidade de produto a adicionar
	 * @throws NaoExisteTipoProdException quando o nome do produto nao corresponde a nenhum tipo de produto previamente registado
	 * @ensures só sao postos produtos à venda cujo tipo de produto já tenha sido registado
	 */
	public void indicaProduto(String nome, int qtdd) throws NaoExisteTipoProdException {	

		if (c.existeTipoProduto(nome)) {
			TipoProduto tp = c.getTipoProduto(nome);	
			produtosAdicionar.add(new ProdutosDia(tp, qtdd));
		}else {
			throw new NaoExisteTipoProdException("O tipo de produto " + nome + " não existe");
		}


	}

	/**
	 * Confirma que se querem meter produtos à venda, indicando o horário para o qual os mesmos vao estar disponiveis para recolha
	 * @param inicio - data de inicio dada
	 * @param fim - data de fim dada
	 * @ensures o comerciante tem mais produtos disponiveis no horario dado
	 */
	public void confirma(LocalDateTime inicio, LocalDateTime fim) {

		c.acrescentaVenda(produtosAdicionar, inicio, fim);

	}




}
