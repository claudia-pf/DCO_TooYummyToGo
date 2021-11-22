package pt.tooyummytogo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.tooyummytogo.events.EventoEncomendaComSucesso;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;
import utils.observer.Observer;

public class Comerciante extends Pessoa implements Observer<EventoEncomendaComSucesso> {

	private PosicaoCoordenadas posicao;
	private HashMap<String, TipoProduto> tiposProdutos;
	private HashMap <LocalDate, PeriodoVenda> vendas;
	

	public Comerciante(String user, String pass, PosicaoCoordenadas pos) {
		super(user, pass);
		posicao = pos;
		tiposProdutos = new HashMap<String, TipoProduto>();
		vendas = new HashMap <LocalDate, PeriodoVenda>();
	}



	public void adicionaTipoProduto(String nome, double preco) {
		tiposProdutos.put(nome, new TipoProduto(nome, preco));
	}


	public List<String> getListaTiposProduto() {
		List<String> lista = new ArrayList<String>();
		tiposProdutos.forEach((key,value) -> lista.add(value.getNome()));
		return lista;
		
	}


	public boolean existeTipoProduto(String nome) {
		return tiposProdutos.containsKey(nome);
	}


	public TipoProduto getTipoProduto(String nome) {
		return tiposProdutos.get(nome);
	}

	/**
	 * Acrescenta um periodo de venda com os seus respectivos produtos do dia às vendas do comerciante.
	 * Se nao existir uma venda nesse dia, é criado um novo periodo de venda,
	 * com os produtos da lista dada e horario inicio-fim.
	 * Se ja existir uma venda nesse dia, todos os seus produtos passam para o horario da nova venda.
	 * Se houver o mesmo tipo de produto nas duas vendas, a quantidade que fica é a nova.
	 * @param produtosAdicionar - lista de produtos a adicionar
	 * @param inicio - inicio do periodo de venda
	 * @param fim - fim do periodo de venda
	 * @ensures em cada dia todos os produtos estao disponiveis no mesmo horario
	 */
	public void acrescentaVenda(List<ProdutosDia> produtosAdicionar, LocalDateTime inicio, LocalDateTime fim) {
		
		LocalDate data = inicio.toLocalDate();	
		
		if(!vendas.containsKey(data)) {
			PeriodoVenda pv = new PeriodoVenda(inicio, fim);
			for(ProdutosDia pd :produtosAdicionar) {
				pv.adicionaProduto(pd);
			}
			vendas.put(data, pv);			
		}else {
			PeriodoVenda pv = vendas.get(data);
			for(ProdutosDia pd :produtosAdicionar) {
				pv.adicionaProduto(pd); // se algum produto dos novos ja la estivesse fica com a quantidade nova
			}
			pv.setHorario(inicio, fim);
		}
		
	}


	
	public boolean temProdutosAVendaNesteHorario(LocalDateTime inicio, LocalDateTime fim) {
		
		boolean temProdutos = false;
		LocalDate data = inicio.toLocalDate();
		
		if(vendas.containsKey(data)) {
			PeriodoVenda pv = vendas.get(data);
			temProdutos = pv.horarioCompativel(inicio, fim) && pv.temProdutos();
		}
		return temProdutos;
	}



	public boolean estaNoRaio(PosicaoCoordenadas coordinate, double raio) {
		return posicao.distanciaEmMetros(coordinate) <= raio * 1000;
	}


	public List<ProdutoInfo> getListaProdutosDisponiveis(LocalDate data) {
		
		ArrayList<ProdutoInfo> res = new ArrayList<ProdutoInfo>();
		List<ProdutosDia> disponiveis = vendas.get(data).produtosDisponiveis();
		
		for(ProdutosDia pd : disponiveis) {
			res.add(new ProdutoInfo (pd.getNome(), pd.getPreco()));
		}
		
		return res;
	}


	public PosicaoCoordenadas getPosicao() {
		return this.posicao;
	}

	
	/**
	 * @param nome - nome do produto
	 * @param data - data em que o produto esta a venda
	 * @return
	 */
	public ProdutosDia getProdutosDia(String nome, LocalDate data) {
		
		PeriodoVenda pv = vendas.get(data);
		
		return pv.getProduto(nome);
	}



	@Override
	public void handleNewEvent(EventoEncomendaComSucesso e) {
		
		System.out.println("--------------------------------------------------------\nMensagem recebida pelo comerciante:\n"
				+ e.getInfo() + "--------------------------------------------------------");

	}





	
}
