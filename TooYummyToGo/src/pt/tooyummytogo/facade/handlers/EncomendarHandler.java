package pt.tooyummytogo.facade.handlers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import pt.tooyummytogo.domain.CatComerciantes;
import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.ProdutosDia;
import pt.tooyummytogo.domain.Reserva;
import pt.tooyummytogo.domain.Utilizador;

import pt.tooyummytogo.exceptions.ErroCartaoException;
import pt.tooyummytogo.exceptions.NaoExistemComerciantesException;
import pt.tooyummytogo.exceptions.QuantidadeIndisponivelException;
import pt.portugueseexpress.InvalidCardException;

import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;

import pt.tooyummytogo.strategy.Pesquisa;

public class EncomendarHandler {

	private Utilizador utilizadorCorrente;
	private CatComerciantes catC;
	private Pesquisa pesquisa;
	private LocalDate dataEncomenda;
	private PosicaoCoordenadas posicaoActual;

	private Comerciante comercianteEscolhido;
	private Reserva res;

	public EncomendarHandler(Utilizador userCorrente, CatComerciantes catC) {
		this.utilizadorCorrente = userCorrente;
		this.catC = catC;
		dataEncomenda = LocalDate.now();
		res = new Reserva(userCorrente.getUsername());
		pesquisa = new Pesquisa(catC.getCatalogo());
			
	}


	/**
	 * @param pos - localicazao actual do utilizador
	 * @return lista de comerciantes com produtos disponiveis num raio de 5km dentro da proxima hora
	 * @throws NaoExistemComerciantesException
	 */
	public List<ComercianteInfo> indicaLocalizacaoActual(PosicaoCoordenadas pos) throws NaoExistemComerciantesException {
		this.posicaoActual = pos;
		return pesquisa.pesquisaDefault(posicaoActual);
	}

	/**
	 * @param raio - raio de pesquisa, em km
	 * @return lista de comerciantes no raio definido e outros criterios anteriormente seleccionados
	 * @throws NaoExistemComerciantesException
	 * @requires ter sido invocado indicaLocalizacaoActual(PosicaoCoordenadas pos) anteriormente
	 */
	public List<ComercianteInfo> redefineRaio(double raio) throws NaoExistemComerciantesException {
		
		return pesquisa.redefineRaio(posicaoActual, raio);
	}
	
	/**
	 * @param inicio da janela temporal de pesquisa
	 * @param fim da janela temporal de pesquisa
	 * @return lista de comerciantes com produtos disponiveis no horario definido e outros criterios anteriormente seleccionados
	 * @throws NaoExistemComerciantesException
	 */

	public List<ComercianteInfo> redefinePeriodo(LocalDateTime inicio, LocalDateTime fim) throws NaoExistemComerciantesException {
		
		dataEncomenda = inicio.toLocalDate();
		return pesquisa.redefinePeriodo(inicio, fim);
	}


	public List<ProdutoInfo> escolheComerciante(ComercianteInfo comercianteInfo) {
		comercianteEscolhido = catC.getComerciante(comercianteInfo.getUsername());
		res.addObserver(comercianteEscolhido);
		return comercianteEscolhido.getListaProdutosDisponiveis(dataEncomenda);
	}

	/**
	 * Adiciona o produto do dia correspondente a produtoInfo dada na quantidade dada 
	 * à reserva 
	 * @param p - produtoInfo dada
	 * @param quantidade dada
	 * @throws QuantidadeIndisponivelException quando o utilizador pede uma quantidade de produtos superior à disponivel
	 * @ensures A reserva fica com mais produtos associados apenas se a quantidade do produto do dia
	 * for igual ou inferior à quantidade pedida
	 */
	public void indicaProduto(ProdutoInfo p, int quantidade) throws QuantidadeIndisponivelException {
		ProdutosDia pdia = comercianteEscolhido.getProdutosDia(p.getNome(), dataEncomenda);
		res.adicionaProduto(pdia, quantidade);
	}


	/**
	 * Indica os dados do cartao para efetuar o pagamento
	 * @param numero do cartao
	 * @param validade do cartao
	 * @param ccv do cartao
	 * @return devolve o codigo da reserva caso o pagamento seja efetuado e uma mensagem de erro
	 * caso contrario
	 * @throws InvalidCardException
	 * @throws ErroCartaoException 
	 */
	public String indicaPagamento(String numero, String validade, String ccv) throws InvalidCardException, ErroCartaoException  {		
		
		utilizadorCorrente.efetuaPagamento(res.getTotal(), numero, validade, ccv);
		String codigo = res.completaReserva();
		utilizadorCorrente.adicionaReserva(res);
		comercianteEscolhido.adicionaReserva(res);
		return codigo;		
	}


}
