package pt.tooyummytogo.facade;


import java.util.Optional;

import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.SessaoComerciante;
import pt.tooyummytogo.SessaoUtilizador;
import pt.tooyummytogo.domain.CatComerciantes;
import pt.tooyummytogo.domain.CatUtilizadores;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

/**
 * Esta é a classe do sistema.
 */
public class TooYummyToGo {
	
	private CatUtilizadores catUtilizadores = new CatUtilizadores();
	private CatComerciantes catComerciantes = new CatComerciantes();

	
	/**
	 * Returns an optional Session representing the authenticated user.
	 * @param username
	 * @param password
	 * @return Optional de Sessao do utilizador/comerciante autenticado
	 * 
	 * UC2
	 */
	public Optional<Sessao> autenticar(String username, String password) {
		if (catUtilizadores.existeUtilizador(username, password)) {
			return Optional.of(new SessaoUtilizador(catUtilizadores.getUtilizador(username), catComerciantes));	
		} else if (catComerciantes.existeComerciante(username, password)) {
			return Optional.of(new SessaoComerciante(catComerciantes.getComerciante(username)));	
		} else {
			return Optional.empty();
		}
	}
	
	

	// UC1
	public RegistarUtilizadorHandler getRegistarUtilizadorHandler() {
		return new RegistarUtilizadorHandler(catComerciantes, catUtilizadores);
	}

	// UC3
	public RegistarComercianteHandler getRegistarComercianteHandler() {
		return new RegistarComercianteHandler(catComerciantes, catUtilizadores);
	}
	
	

}
