package pt.tooyummytogo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pt.tooyummytogo.exceptions.NaoExistemComerciantesException;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

public class CatComerciantes {

	private List<Comerciante> comerciantes = new ArrayList<>();



	public void adicionaComerciante(String user, String pass, PosicaoCoordenadas pos) {
		comerciantes.add(new Comerciante(user, pass, pos));
	}


	public List<Comerciante> getCatalogo(){
		return new ArrayList<>(comerciantes);
	}

	
	public Comerciante getComerciante(String username) {
		for(Comerciante c: comerciantes) {
			if(username.equals(c.getUsername())){
				return c;
			}
		}		
		return null;
	}


	public boolean usernameEmUso(String username) {
		for(Comerciante c : comerciantes) {
			if (c.getUsername().toLowerCase().contentEquals(username.toLowerCase())) {
				return true;
			}
		}
		return false;
	}


	public boolean existeComerciante(String username, String password) {
		for(Comerciante u : comerciantes) {
			if (u.hasUsernameAndPassword(username, password)) {
				return true;
			}
		}
		return false;
	}

}
