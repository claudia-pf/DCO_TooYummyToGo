package pt.tooyummytogo.strategy;

import java.time.LocalDateTime;

import pt.tooyummytogo.domain.Comerciante;

public class CriterioHorario implements CriterioPesquisa{
	
	private LocalDateTime inicio;
	private LocalDateTime fim;
	
	public CriterioHorario() {
		inicio = LocalDateTime.now();
		fim = inicio.plusHours(1);
	}
	
	public CriterioHorario(LocalDateTime inicio, LocalDateTime fim) {
		this.inicio = inicio;
		this.fim = fim;
	}
		

	@Override
	public boolean corresponde(Comerciante c) {
		return c.temProdutosAVendaNesteHorario(inicio, fim);
	}

}
