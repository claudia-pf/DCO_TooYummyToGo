package pt.tooyummytogo.main;

// Serviu para testar UC7

/*
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import pt.portugueseexpress.InvalidCardException;
import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.exceptions.NaoExisteTipoProdException;
import pt.tooyummytogo.exceptions.NaoExistemComerciantesException;
import pt.tooyummytogo.exceptions.QuantidadeIndisponivelException;
import pt.tooyummytogo.exceptions.UsernameJaEmUsoException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;


public class TesteUC7 {
	public static void main(String[] args){
		TooYummyToGo ty2g = new TooYummyToGo();

		//Posicoes
		PosicaoCoordenadas campoGrande = new PosicaoCoordenadas(38.755974, -9.156863); 
		PosicaoCoordenadas tomar = new PosicaoCoordenadas(39.606255, -8.406678); 
		PosicaoCoordenadas martimMoniz = new PosicaoCoordenadas(38.715994, -9.136909);
		PosicaoCoordenadas odivelas = new PosicaoCoordenadas(38.792958, -9.172891); 
		PosicaoCoordenadas lagos = new PosicaoCoordenadas(37.084002, -8.679318); 
		PosicaoCoordenadas faro = new PosicaoCoordenadas(37.018603, -7.938162); 
		PosicaoCoordenadas porto = new PosicaoCoordenadas(41.152459, -8.630403); 		

		//horas
		LocalDateTime agora = LocalDateTime.now().plusMinutes(2);
		LocalDateTime hora1 = agora.plusHours(1);
		LocalDateTime hora2 = agora.plusHours(2);
		LocalDateTime hora3 = agora.plusHours(3);
		LocalDateTime hora4 = agora.plusHours(4);

		String [] comerciantes = {"Espiga Dourada", "Panzi", "Sabores Tomar", "Meia Praia", "Palmeiras", "Capa Negra"};
		PosicaoCoordenadas [] posicoes = {odivelas, martimMoniz, tomar, lagos, faro, porto};
		String [] passwords = {"odivelas", "martimMoniz", "tomar", "lagos", "faro", "porto"};


		// UC1

		try {
			RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
			regHandler.registarUtilizador("Felismina", "hortadafcul");

			// UC3

			RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();

			for(int i = 0; i < comerciantes.length; i++) {
				regComHandler.registarComerciante(comerciantes[i], passwords[i], posicoes[i]);
			}
		} catch (UsernameJaEmUsoException e) {
			System.out.println(e.getMessage());
		}
		// UC4 + UC5 Espiga Dourada
		Optional<Sessao> talvezEspiga = ty2g.autenticar("Espiga Dourada", "odivelas");
		talvezEspiga.ifPresent( (Sessao s) -> {
			try {

				AdicionarTipoDeProdutoHandler atp = s.adicionarTipoDeProdutoHandler();		
				String [] nome = {"Pão", "Pão de Ló", "Mil-folhas", "Floresta Negra", "Duchese"};
				Double [] preco = {0.10, 3.0, 1.0, 1.50, 0.80};

				for(int i = 0; i < nome.length; i++) {
					atp.registaTipoDeProduto(nome[i], preco[i]);
				}

				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();
				List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();
				for(int i = 0; i< listaTiposDeProdutos.size(); i++) {
					cpv.indicaProduto(listaTiposDeProdutos.get(i), 3); 
				}

				cpv.confirma(agora, hora1);
			} catch (NaoExisteTipoProdException e) {
				System.out.println(e.getMessage());
			} 
		});


		// UC4 + UC5 Panzi
		Optional<Sessao> talvezPanzi = ty2g.autenticar("Panzi", "martimMoniz");
		talvezPanzi.ifPresent( (Sessao s) -> {
			try {

				AdicionarTipoDeProdutoHandler atp = s.adicionarTipoDeProdutoHandler();		
				String [] nome = {"gyozas", "ramen", "arroz frito"};
				Float [] preco = {2f, 4f, 3f};

				for(int i = 0; i < nome.length; i++) {
					atp.registaTipoDeProduto(nome[i], preco[i]);
				}

				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();
				List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();
				for(int i = 0; i< listaTiposDeProdutos.size(); i++) {
					cpv.indicaProduto(listaTiposDeProdutos.get(i), 3); 
				}

				cpv.confirma(hora1, hora2);
			} catch (NaoExisteTipoProdException e) {
				System.out.println(e.getMessage());
			} 
		});	

		// UC4 + UC5 SaboresTomar
		Optional<Sessao> talvezTomar = ty2g.autenticar("Sabores Tomar", "tomar");
		talvezTomar.ifPresent( (Sessao s) -> {
			try {

				AdicionarTipoDeProdutoHandler atp = s.adicionarTipoDeProdutoHandler();		
				String [] nome = {"toureiro", "fatia dourada", "beijinhos"};
				Float [] preco = {1f, 0.50f, 1.30f};

				for(int i = 0; i < nome.length; i++) {
					atp.registaTipoDeProduto(nome[i], preco[i]);
				}

				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();
				List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();
				for(int i = 0; i< listaTiposDeProdutos.size(); i++) {
					cpv.indicaProduto(listaTiposDeProdutos.get(i), 3); 
				}

				cpv.confirma(hora2, hora3);
			} catch (NaoExisteTipoProdException e) {
				System.out.println(e.getMessage());
			} 

		});	

		// UC4 + UC5 Meia Praia
		Optional<Sessao> talvezPraia = ty2g.autenticar("Meia Praia", "lagos");
		talvezPraia.ifPresent( (Sessao s) -> {
			try {

				AdicionarTipoDeProdutoHandler atp = s.adicionarTipoDeProdutoHandler();		
				String [] nome = {"peixe espada", "salmao", "conquilhas"};
				Float [] preco = {4f, 3.50f, 4f};

				for(int i = 0; i < nome.length; i++) {
					atp.registaTipoDeProduto(nome[i], preco[i]);
				}

				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();
				List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();
				for(int i = 0; i< listaTiposDeProdutos.size(); i++) {
					cpv.indicaProduto(listaTiposDeProdutos.get(i), 3); 
				}

				cpv.confirma(agora, hora2);
			} catch (NaoExisteTipoProdException e) {
				System.out.println(e.getMessage());
			} 
		});	

		// UC4 + UC5 Palmeiras
		Optional<Sessao> talvezPalmeiras = ty2g.autenticar("Palmeiras", "faro");
		talvezPalmeiras.ifPresent( (Sessao s) -> {
			try {
				AdicionarTipoDeProdutoHandler atp = s.adicionarTipoDeProdutoHandler();		
				String [] nome = {"dom rodrigo", "estrelas", "tarte de alfarroba"};
				Float [] preco = {1f, 0.50f, 0.80f};

				for(int i = 0; i < nome.length; i++) {
					atp.registaTipoDeProduto(nome[i], preco[i]);
				}


				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();
				List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();
				for(int i = 0; i< listaTiposDeProdutos.size(); i++) {
					cpv.indicaProduto(listaTiposDeProdutos.get(i), 3); 
				}

				cpv.confirma(hora2, hora4);
			} catch (NaoExisteTipoProdException e) {
				System.out.println(e.getMessage());
			} 
		});		

		// UC4 + UC5 Capa Negra
		Optional<Sessao> talvezCapa = ty2g.autenticar("Capa Negra", "porto");
		talvezCapa.ifPresent( (Sessao s) -> {
			try {
				AdicionarTipoDeProdutoHandler atp = s.adicionarTipoDeProdutoHandler();		
				String [] nome = {"francesinha", "alheira"};
				Float [] preco = {6f, 6f};

				for(int i = 0; i < nome.length; i++) {
					atp.registaTipoDeProduto(nome[i], preco[i]);
				}

				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();
				List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();
				for(int i = 0; i< listaTiposDeProdutos.size(); i++) {

					cpv.indicaProduto(listaTiposDeProdutos.get(i), 3);

				}
				cpv.confirma(agora, hora3);
			} catch (NaoExisteTipoProdException e) {
				System.out.println(e.getMessage());
			} 
		});	

		// UC6 + UC7

		Optional<Sessao> talvezCliente = ty2g.autenticar("Felismina", "hortadafcul");
		talvezCliente.ifPresent( (Sessao s) -> {
			try {
				EncomendarHandler lch = s.getEncomendarComerciantesHandler();
				System.out.println("Utilizador localizado em: Campo Grande");

				List<ComercianteInfo> cs;

				cs = lch.indicaLocalizacaoActual(campoGrande);

				System.out.println("\nComerciantes disponiveis na proxima hora num raio de 5km:");
				imprimeComerciantes(cs);

				System.out.println("\nComerciantes disponiveis na proxima hora num raio de 10km:");
				cs = lch.redefineRaio(10);
				imprimeComerciantes(cs);


				//UC7


				List<ProdutoInfo> ps = lch.escolheComerciante(cs.get(0));

				System.out.println("\nComerciante escolhido: " + cs.get(0));

				for (ProdutoInfo p : ps) {
					try {
						lch.indicaProduto(p, 1);
						System.out.println("Encomendar: " + p + " 1");
					} catch (QuantidadeIndisponivelException e) {
						System.out.println(e.getMessage());
					} 
				}
				String codigoReserva;

				try {
					codigoReserva = lch.indicaPagamento("365782312312", "02/21", "765"); //cvc errado
					System.out.println("Reserva feita com sucesso\nCodigo da reserva: " + codigoReserva);  
				} catch (InvalidCardException e) {
					System.out.println("Pagamento inválido");
					try {
						System.out.println("Nova tentativa de pagamento:");
						codigoReserva = lch.indicaPagamento("365782312312", "02/21", "766"); //cvc certo
						System.out.println("Reserva feita com sucesso\nCodigo da reserva: " + codigoReserva);
					} catch (InvalidCardException e1) {
						System.out.println("Pagamento inválido");

					}
				} 

			} catch (NaoExistemComerciantesException e1) {

				System.out.println(e1.getMessage());
			}
		});

	}

	private static void imprimeComerciantes(List<ComercianteInfo> cs) {
		if(cs.isEmpty()) {
			System.out.println("nenhum");
		}else {
			for (ComercianteInfo c : cs) {
				System.out.println(c);
			}
		}
	}


}
*/