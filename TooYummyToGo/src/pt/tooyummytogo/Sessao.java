package pt.tooyummytogo;

import pt.tooyummytogo.exceptions.AccaoInvalidaException;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;

/**
 * @author fc52786 && fc53483
 * Esta classe simula uma sessão de uma pessoaCorrente (Utilizador ou Comerciante)
 * É nesta classe que são criados os handlers AdicionarTipoDeProdutoHandler, 
 * ColocarProdutoHandler e EncomendarHandler
 *
 */
public abstract class Sessao {
		
	
	
	// UC4
	public AdicionarTipoDeProdutoHandler adicionarTipoDeProdutoHandler() throws AccaoInvalidaException {
		throw new AccaoInvalidaException("Acção inválida para este tipo de utilizador");
	}

	
	// UC5 
	public ColocarProdutoHandler getColocarProdutoHandler() throws AccaoInvalidaException {
		throw new AccaoInvalidaException("Acção inválida para este tipo de utilizador");
	}
	
	//UC6	
	public EncomendarHandler getEncomendarComerciantesHandler() throws AccaoInvalidaException {
		throw new AccaoInvalidaException("Acção inválida para este tipo de utilizador");
	}

	
}
