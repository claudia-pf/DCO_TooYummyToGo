package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.domain.CatComerciantes;
import pt.tooyummytogo.domain.CatUtilizadores;

public abstract class AbstractRegistarHandler {
	
	protected CatComerciantes catComerciantes;
	protected CatUtilizadores catUtilizadores;

	public AbstractRegistarHandler(CatComerciantes catC, CatUtilizadores catU) {
		this.catComerciantes = catC;
		this.catUtilizadores = catU;
	}
	
	public boolean usernameJaExiste(String username) {
		return catComerciantes.usernameEmUso(username) || catUtilizadores.usernameEmUso(username);
	}

}
