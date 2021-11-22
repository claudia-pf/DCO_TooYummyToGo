package pt.tooyummytogo.pagamento;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import pt.tooyummytogo.config.Configuration;


public class MeioPagamentoOwner {

	@SuppressWarnings("unchecked")
	public static MeioPagamento getMeioPagamento(String numero, String validade, String ccv) {
		
		Configuration config = Configuration.getInstance();	
		String className = config.getProperty("meioPagamentoAUsar");
		Class<MeioPagamento> klass;
		try {
			klass = (Class<MeioPagamento>) Class.forName(className);
			Constructor<MeioPagamento> cons;
			cons = klass.getConstructor(String.class, String.class, String.class);
			return cons.newInstance(numero, validade, ccv);
		} catch (NullPointerException | ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException 
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// do nothing
		} 

		return new MonsterCardAdapter(numero, validade, ccv);
	}
	
	

}
