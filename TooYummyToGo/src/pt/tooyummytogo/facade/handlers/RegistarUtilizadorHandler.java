package pt.tooyummytogo.facade.handlers;


import pt.tooyummytogo.domain.CatComerciantes;
import pt.tooyummytogo.domain.CatUtilizadores;
import pt.tooyummytogo.exceptions.UsernameJaEmUsoException;


public class RegistarUtilizadorHandler extends AbstractRegistarHandler {

	public RegistarUtilizadorHandler(CatComerciantes catC, CatUtilizadores catU) {
		super(catC, catU);
	}

	/**
	 * Regista um utilizador nao comerciante.
	 * @param Username
	 * @param Password
	 * @throws UsernameJaEmUsoException caso ja esteja registado um utilizador ou comerciante com esse username
	 * @ensures Nao ha comerciantes/utilizadores com usernames duplicados. Utilizador só é registado se o username nao estiver ja em uso
	 */
	 public void registarUtilizador(String username, String password) throws UsernameJaEmUsoException {
		
		 if(usernameJaExiste(username)) {
			 throw new UsernameJaEmUsoException("Já existe um utilizador com o username " + username + ". Escolha outro username.");
		 }else {
			 catUtilizadores.addUser(username, password);
		 }
	}

}
