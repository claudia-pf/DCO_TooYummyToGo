package pt.tooyummytogo.strategy;

import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;

public class CriterioRaio implements CriterioPesquisa {
	
	private final double RAIO_DEFAULT = 5; 
	private double raio;
	private PosicaoCoordenadas pos;

	
	public CriterioRaio(PosicaoCoordenadas pos) {
		this.raio = RAIO_DEFAULT;	
		this.pos = pos;
	}
	
	public CriterioRaio(PosicaoCoordenadas pos, double raio) {
		this.raio = raio;	
		this.pos = pos;
	}

	@Override
	public boolean corresponde(Comerciante c) {
		return c.estaNoRaio(pos, raio);
	}
	
	

}
