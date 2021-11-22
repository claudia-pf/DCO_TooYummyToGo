package pt.tooyummytogo.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Pessoa {
	
	protected String username;
	protected String password;
	protected List<Reserva> reservas;

	

	public Pessoa(String username, String password) {
		this.username = username;
		this.password = password;
		reservas = new ArrayList<>();
	} 
	

	public String getUsername() {
		return username;
	}


	public boolean hasUsernameAndPassword(String u, String p) {
		return username.contentEquals(u) && password.contentEquals(p);
	}
	

	public boolean hasUsername(String u) {
		return username.contentEquals(u);
	}
	

	public void adicionaReserva(Reserva res) {
		reservas.add(res);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if(obj instanceof Pessoa) {
			Pessoa c = (Pessoa) obj;
			return c.username.equals(this.username) && c.password.equals(this.password);
		}
		return false;	
	}
	

}
