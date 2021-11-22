
package pt.tooyummytogo.facade.dto;

public class ComercianteInfo {

	private String username;
	private PosicaoCoordenadas pos;
	
	public ComercianteInfo(String nome, PosicaoCoordenadas pos) {
		this.username = nome;
		this.pos = pos;
	}
	
	@Override
	public String toString() {
		return username;
	}

	public String getUsername() {
		return username;
	}
	
	

}