package pt.tooyummytogo;


import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;

public class SessaoComerciante extends Sessao {

	private Comerciante c;

	public SessaoComerciante(Comerciante c) {
		this.c = c;
	}

	//UC4
	public AdicionarTipoDeProdutoHandler adicionarTipoDeProdutoHandler() {

		return new AdicionarTipoDeProdutoHandler(c);	
	}

	//UC5
	public ColocarProdutoHandler getColocarProdutoHandler() {

		return new ColocarProdutoHandler(c);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if(obj instanceof SessaoComerciante) {
			SessaoComerciante s = (SessaoComerciante) obj;
			return s.c.equals(this.c);
		}
		return false;	
	}


}
