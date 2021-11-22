package pt.tooyummytogo.domain;

import java.util.ArrayList;
import java.util.List;


public class CatUtilizadores {
	private List<Utilizador> utilizadores = new ArrayList<>();


	public void addUser(String username, String password) {
		utilizadores.add(new Utilizador(username, password));
	}
	
	

	public boolean usernameEmUso(String username) {
		for(Utilizador u : utilizadores) {
			if (u.getUsername().toLowerCase().contentEquals(username.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	

	
	public boolean existeUtilizador(String username, String password) {
		for(Utilizador u : utilizadores) {
			if (u.hasUsernameAndPassword(username, password)) {
				return true;
			}
		}
		return false;
	}
	
	

	public Utilizador getUtilizador(String username) {
		for(Utilizador u : utilizadores) {
			if (u.hasUsername(username)) {
				return u;
			}
		}
		return null;
	}
}
