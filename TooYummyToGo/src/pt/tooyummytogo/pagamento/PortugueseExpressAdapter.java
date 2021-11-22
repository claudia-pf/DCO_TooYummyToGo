package pt.tooyummytogo.pagamento;

import pt.portugueseexpress.InvalidCardException;
import pt.portugueseexpress.PortugueseExpress;

public class PortugueseExpressAdapter extends MeioPagamento{
	
	private PortugueseExpress api;
	
	public PortugueseExpressAdapter(String numero, String validade, String ccv) {
		super(numero, validade, ccv);
		this.api = new PortugueseExpress();
		api.setNumber(numero);
		api.setCcv(Integer.valueOf(ccv));
		api.setMonth(Integer.valueOf(this.mes));
		api.setYear(Integer.valueOf(this.ano));
	}


	@Override
	public boolean validate() {
		return api.validate();
	}

	@Override
	public void block(Double montante) throws InvalidCardException {
		api.block(montante);
		
	}

	@Override
	public void charge(Double montante) throws InvalidCardException {
		api.charge(montante);
		
	}

}
