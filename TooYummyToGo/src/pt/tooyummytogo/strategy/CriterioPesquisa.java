package pt.tooyummytogo.strategy;

import pt.tooyummytogo.domain.Comerciante;

public interface CriterioPesquisa {
	
	public boolean corresponde(Comerciante c);

}
