package pt.tooyummytogo.pagamento;

import pt.portugueseexpress.InvalidCardException;
import pt.tooyummytogo.exceptions.ErroCartaoException;

public abstract class MeioPagamento {
	
	protected String ccv;
	protected String mes;
	protected String ano;
	protected String numero;
	
	public MeioPagamento(String numero, String validade, String ccv) {
		this.numero = numero;
		this.ccv = ccv;
		this.mes = validade.substring(0,2);
		this.ano = "20"+validade.substring(3, 5);		
	}
	
	public abstract boolean validate();
	
	public abstract void block(Double montante) throws InvalidCardException, ErroCartaoException;
	
	public abstract void charge(Double montante) throws InvalidCardException, ErroCartaoException;

}
