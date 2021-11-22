package pt.tooyummytogo.domain;

import pt.portugueseexpress.InvalidCardException;
import pt.portugueseexpress.PortugueseExpress;
import pt.tooyummytogo.exceptions.ErroCartaoException;
import pt.tooyummytogo.pagamento.MeioPagamento;
import pt.tooyummytogo.pagamento.MeioPagamentoOwner;

public class Utilizador extends Pessoa{
	

	public Utilizador(String username, String password) {
		super(username, password);
	}

	/**
	 * Efetua pagamente com o valor dado, atraves dos dados do cartao dados
	 * @param total - total a pagar
	 * @param numero - numero do cartao
	 * @param validade - validade do cartao
	 * @param ccv - ccv do cartao
	 * @throws InvalidCardException
	 * @throws ErroCartaoException 
	 */
	public void efetuaPagamento(double total, String numero, String validade, String ccv) throws InvalidCardException, ErroCartaoException {
		
		MeioPagamento mp = MeioPagamentoOwner.getMeioPagamento(numero, validade, ccv);	
		if(mp.validate()) {
			mp.block(total);
			mp.charge(total);
		}else {
			throw new InvalidCardException();
		}
	}
	
}
