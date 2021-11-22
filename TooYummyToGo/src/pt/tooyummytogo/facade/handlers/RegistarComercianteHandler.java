package pt.tooyummytogo.facade.handlers;

import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;


import pt.tooyummytogo.domain.CatComerciantes;
import pt.tooyummytogo.domain.CatUtilizadores;
import pt.tooyummytogo.exceptions.UsernameJaEmUsoException;


public class RegistarComercianteHandler extends AbstractRegistarHandler{


	public RegistarComercianteHandler(CatComerciantes catC, CatUtilizadores catU) {
		super(catC, catU);
	}

	/**
	 * Regista um Comerciante.
	 * @param Username
	 * @param Password
	 * @param pos - localizacao do estabelecimento do comerciante
	 * @throws UsernameJaEmUsoException caso ja esteja registado um utilizador ou comerciante com esse username
	 * @ensures Nao ha comerciantes/utilizadores com usernames duplicados. Comerciante só é registado se o username nao estiver ja em uso
	 */

	public void registarComerciante(String username, String password, PosicaoCoordenadas pos) throws UsernameJaEmUsoException {

		if(usernameJaExiste(username)) {
			throw new UsernameJaEmUsoException("Já existe um utilizador com o username " + username + ". Escolha outro username.");
		}else {
			catComerciantes.adicionaComerciante(username, password, pos);
		}
	}

}
