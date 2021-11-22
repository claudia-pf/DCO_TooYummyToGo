package pt.tooyummytogo.events;

import utils.observer.Event;

public class EventoEncomendaComSucesso implements Event {
	private String codigo;
	private String info;

	public EventoEncomendaComSucesso(String codigo, String info) {
		this.codigo = codigo;
		this.info = info;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getInfo() {
		return info;
	}

}
