package pt.tooyummytogo.main;

// Serviu para testar UC5

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


public class TesteUC5 {
	public static void main(String[] args){
		TooYummyToGo ty2g = new TooYummyToGo();

		//Posicoes
		PosicaoCoordenadas campoGrande = new PosicaoCoordenadas(38.755974, -9.156863); ;
		PosicaoCoordenadas odivelas = new PosicaoCoordenadas(38.792958, -9.172891); 	

		//horas
		LocalDateTime agora = LocalDateTime.now().plusMinutes(2);
		LocalDateTime hora1 = agora.plusHours(1);
		LocalDateTime hora2 = agora.plusHours(2);

		// UC1

		try {
			RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
			regHandler.registarUtilizador("Felismina", "hortadafcul");

			// UC3

			RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();
			regComHandler.registarComerciante("Espiga Dourada", "odivelas", odivelas);

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
				
				// venda 1 hoje
				
				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();
				List<String> ltp = cpv.inicioDeProdutosHoje();
				
				cpv.indicaProduto("Pão", 10);
				cpv.indicaProduto("Pão de Ló", 10);			
				cpv.confirma(agora, hora1);
				
				// venda 2 hoje ---> venda 1 vai desaparecer e os produtos da venda 1 que nao estao na venda 2 vao passar para a venda 2
				
				ColocarProdutoHandler cpv2 = s.getColocarProdutoHandler();
				List<String> listaTiposDeProdutos2 = cpv2.inicioDeProdutosHoje();
				
				cpv2.indicaProduto("Pão de Ló", 3);  //pao de lo como estava nas duas vendas vai ficar com a quantidade nova
				cpv2.indicaProduto("Mil-folhas", 10);
				cpv2.indicaProduto("Floresta Negra", 10);	
			
				cpv2.confirma(hora1, hora2);
				
				
				// venda 3 amanha 
				
				ColocarProdutoHandler cpv3 = s.getColocarProdutoHandler();
				List<String> listaTiposDeProdutos3 = cpv3.inicioDeProdutosHoje();
				
				cpv3.indicaProduto("Duchese", 10);
				
				cpv3.confirma(agora.plusDays(1), hora1.plusDays(1));				
				
			} catch (NaoExisteTipoProdException e) {
				System.out.println(e.getMessage());
			} 
			
		});



		// UC6 + UC7

		Optional<Sessao> talvezCliente = ty2g.autenticar("Felismina", "hortadafcul");
		talvezCliente.ifPresent( (Sessao s) -> {
			EncomendarHandler lch = s.getEncomendarComerciantesHandler();
			System.out.println("Utilizador localizado em: Campo Grande");

			List<ComercianteInfo> cs;
			try {
			cs = lch.indicaLocalizacaoActual(campoGrande);		
			} catch (NaoExistemComerciantesException e) {
				System.out.println(e.getMessage());
				System.out.println("\nComerciantes disponiveis na proxima hora num raio de 5km:\nNenhum");
			}			
			
			try {
				cs = lch.redefinePeriodo(hora1, hora2);		
					System.out.println("\nComerciantes disponiveis entre daqui a 1 hora e 2 horas num raio de 5km:");
					imprimeComerciantes(cs);
				

				//UC7

			//só aparecem os produtos da venda 2. a venda 3 nao aparece e a venda 1 ja nao existe
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

				try {
					String codigoReserva = lch.indicaPagamento("365782312312", "02/21", "766");
					System.out.println("Reserva feita com sucesso\nCodigo da reserva: " + codigoReserva);  
				} catch (InvalidCardException e) {
					System.out.println("Pagamento inválido");
				}

			} catch (NaoExistemComerciantesException e) {
				System.out.println(e.getMessage());
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