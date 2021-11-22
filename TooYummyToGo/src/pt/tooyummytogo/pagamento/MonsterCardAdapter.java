package pt.tooyummytogo.pagamento;

import com.monstercard.Card;
import com.monstercard.MonsterCardAPI;
import pt.tooyummytogo.exceptions.ErroCartaoException;

public class MonsterCardAdapter extends MeioPagamento{
	
	private MonsterCardAPI api;
	private Card c;

	public MonsterCardAdapter(String numero, String validade, String ccv) {
		super(numero, validade, ccv);
		this.c = new Card(numero, ccv, this.mes, this.ano);
		this.api = new MonsterCardAPI();

	}

	@Override
	public boolean validate() {
		return api.isValid(c);
	}

	@Override
	public void block(Double montante) throws ErroCartaoException {
		
		if(!api.block(c, montante)) {
			throw new ErroCartaoException("Erro no pagamento (MonsterCard)");
		}
	}

	@Override
	public void charge(Double montante) throws ErroCartaoException {
		
		if(!api.charge(c, montante)) {
			throw new ErroCartaoException("Erro no pagamento (MonsterCard)");
		}
	}


}
