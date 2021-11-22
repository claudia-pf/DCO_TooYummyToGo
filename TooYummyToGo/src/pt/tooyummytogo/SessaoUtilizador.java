package pt.tooyummytogo;

import pt.tooyummytogo.domain.CatComerciantes;
import pt.tooyummytogo.domain.Utilizador;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;

public class SessaoUtilizador extends Sessao {

	private Utilizador u;
	private CatComerciantes catC;

	public SessaoUtilizador(Utilizador ut, CatComerciantes catC) {
		this.catC = catC;
		this.u = ut;
	}

	//UC6 + UC7
	public EncomendarHandler getEncomendarComerciantesHandler() {

		return new EncomendarHandler(u, catC);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((u == null) ? 0 : u.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if(obj instanceof SessaoUtilizador) {
			SessaoUtilizador s = (SessaoUtilizador) obj;
			return s.u.equals(this.u);
		}
		return false;	
	}


}
