package pt.tooyummytogo.main;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import pt.portugueseexpress.InvalidCardException;
import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.exceptions.AccaoInvalidaException;
import pt.tooyummytogo.exceptions.ErroCartaoException;
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


// Neste cliente podem-se ver as excepcoes em uso

public class ClienteExemplo2 {
	public static void main(String[] args) {
		TooYummyToGo ty2g = new TooYummyToGo();
		LocalDateTime agora = LocalDateTime.now().plusMinutes(3);

		// UC1
		String [] utilizadores = {"Felismina", "Felismina"};
		String [] pass = {"hortadafcul", "password"};		
		RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();

		for(int i = 0; i < utilizadores.length; i++) {
			try {
				regHandler.registarUtilizador(utilizadores[i], pass[i]);
				System.out.println("Utilizador "+ utilizadores[i] + " registado");
			} catch (UsernameJaEmUsoException e) {

				System.out.println(e.getMessage() + "\n"); //a segunda felismina vai cair aqui
			}		
		}

		// UC3
		RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();

		PosicaoCoordenadas campoGrande = new PosicaoCoordenadas(38.755974, -9.156863); 
		PosicaoCoordenadas martimMoniz = new PosicaoCoordenadas(38.715994, -9.136909);
		PosicaoCoordenadas odivelas = new PosicaoCoordenadas(38.792958, -9.172891); 

		String [] comerciantes = {"Espiga Dourada", "Panzi", "Espiga Dourada"};
		PosicaoCoordenadas [] posicoes = {odivelas, martimMoniz, campoGrande};
		String [] passwords = {"odivelas", "martimMoniz", "tomar"};

		for(int i = 0; i < comerciantes.length; i++) {
			try {
				regComHandler.registarComerciante(comerciantes[i], passwords[i], posicoes[i]);
				System.out.println("Comerciante " + comerciantes[i] + " registado");
			} catch (UsernameJaEmUsoException e) {			
				System.out.println(e.getMessage() + "\n"); //a segunda espiga dourada vai cair aqui		
				try {
					regComHandler.registarComerciante("FELISMINA", "pass", campoGrande);
				}catch(UsernameJaEmUsoException e1) {			
					System.out.println(e1.getMessage() + "\n"); //ja existe uma felismina
				}		
			}
		}

		// UC4 Espiga Dourada
		Optional<Sessao> talvezSessao = ty2g.autenticar("Espiga Dourada", "odivelas");
		talvezSessao.ifPresent( (Sessao s) -> {
			AdicionarTipoDeProdutoHandler atp;
			try {
				atp = s.adicionarTipoDeProdutoHandler();

				Random r = new Random();
				for (String tp : new String[] {"Pão", "Pão de Ló", "Mil-folhas"}) {
					atp.registaTipoDeProduto(tp, r.nextDouble() * 10);
					System.out.println("Registado " + tp);
				}
			} catch (AccaoInvalidaException e) {
				System.out.println(e.getMessage());
			}
		});


		// UC5 Espiga Dourada
		Optional<Sessao> talvezEspiga = ty2g.autenticar("Espiga Dourada", "odivelas");
		talvezEspiga.ifPresent( (Sessao s) -> {
			try {
				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();

				List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();

				for(String tp : listaTiposDeProdutos) {
					try {
						cpv.indicaProduto(tp, 5);
						System.out.println("Posto à venda " + tp);
					} catch (NaoExisteTipoProdException e) {
						System.out.println(e.getMessage());
					} 

				}

				//bola de berlim vai levantar excepção pois nao está registada

				String tp = "Bola de Berlim";
				try {		
					cpv.indicaProduto(tp, 10);
				} catch (NaoExisteTipoProdException e) { //extensao 3a
					System.out.println("\n" + e.getMessage());
					AdicionarTipoDeProdutoHandler atp;
					try {
						atp = s.adicionarTipoDeProdutoHandler();

						atp.registaTipoDeProduto(tp, 0.80);
						System.out.println("Registado " + tp);
					} catch (AccaoInvalidaException e1) {
						System.out.println(e1.getMessage());
					}
					try {
						cpv.indicaProduto(tp, 10);
						System.out.println("Posto à venda " + tp + "\n");
					} catch (NaoExisteTipoProdException e1) {
						System.out.println(e1.getMessage());
					}
				}
				cpv.confirma(agora, agora.plusHours(1));
				System.out.println("Produtos disponíveis no horario indicado");
			} catch (AccaoInvalidaException e) {
				System.out.println(e.getMessage());
			}
		});


		// UC4 + UC5 Panzi
		Optional<Sessao> talvezPanzi = ty2g.autenticar("Panzi", "martimMoniz");
		talvezPanzi.ifPresent( (Sessao s) -> {

			AdicionarTipoDeProdutoHandler atp;
			try {
				atp = s.adicionarTipoDeProdutoHandler();

				String [] nome = {"gyozas", "ramen", "arroz frito"};
				Float [] preco = {2f, 4f, 3f};

				for(int i = 0; i < nome.length; i++) {
					atp.registaTipoDeProduto(nome[i], preco[i]);
				}

				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();
				List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();
				for(int i = 0; i< listaTiposDeProdutos.size(); i++) {
					try {
						cpv.indicaProduto(listaTiposDeProdutos.get(i), i);
					} catch (NaoExisteTipoProdException e) {
						System.out.println(e.getMessage());
					} 
				}
				cpv.confirma(agora.plusHours(1), agora.plusHours(2));
				System.out.println("Produtos disponíveis no horario indicado");
			} catch (AccaoInvalidaException e) {
				System.out.println(e.getMessage());
			}

		});

		// UC6 + UC7
		Optional<Sessao> talvezCliente = ty2g.autenticar("Felismina", "hortadafcul");
		talvezCliente.ifPresent( (Sessao s) -> {

			//UC6
			try {
				EncomendarHandler lch = s.getEncomendarComerciantesHandler();			
				try {
					List<ComercianteInfo> cs;

					cs = lch.indicaLocalizacaoActual(campoGrande);
					System.out.println("\nComerciantes disponiveis na proxima hora num raio de 5km:");
					for (ComercianteInfo i : cs) {
						System.out.println(i);
					}

					System.out.println("\nComerciantes disponiveis na proxima hora no novo raio:");
					cs = lch.redefineRaio(1000);
					for (ComercianteInfo i : cs) {
						System.out.println(i);
					}

					System.out.println("\nComerciantes disponiveis no novo horario no raio da ultima pesquisa:");
					cs = lch.redefinePeriodo(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(4));
					for (ComercianteInfo i : cs) {
						System.out.println(i);
					}

					// UC7
					List<ProdutoInfo> ps = lch.escolheComerciante(cs.get(0));
					System.out.println("\nComerciante escolhido: "+ cs.get(0));

					for (ProdutoInfo p : ps) {
						try {
							lch.indicaProduto(p, 1);// Um de cada
							System.out.println("Adicionada 1 unidade de "+ p + " ao carrinho");
						} catch (QuantidadeIndisponivelException e) {
							System.out.println(e.getMessage());
						} 
					}

					// EXTENSAO 4A
					// quantidade de bolas de berlim pedidas > quantidade disponivel
					// originalmente havia 10, pediu-se 1, ficaram 9
					// quando se pedem 10 a seguir da erro porque ele sabe q 1 ja foi pedida

					ProdutoInfo p = ps.get(0);
					try {					
						lch.indicaProduto(p, 10);  
					} catch (QuantidadeIndisponivelException e) { 
						System.out.println("\n"+e.getMessage());		
						try {
							lch.indicaProduto(p, 1);
							System.out.println("Adicionada 1 unidade de "+ p + " ao carrinho");
						} catch (QuantidadeIndisponivelException e1) { //extensao 4a
							System.out.println(e1.getMessage());
						} 				
					} 

					String codigoReserva;

					try {
						codigoReserva = lch.indicaPagamento("365782312312", "02/21", "765"); //cvc errado
						System.out.println("Reserva feita com sucesso\nCodigo da reserva: " + codigoReserva);  
					} catch (InvalidCardException | ErroCartaoException e) {
						System.out.println("Pagamento inválido");
						try {
							System.out.println("Nova tentativa de pagamento:");
							codigoReserva = lch.indicaPagamento("365782312312", "02/21", "766"); //cvc certo
							System.out.println("Reserva feita com sucesso. Codigo: " + codigoReserva); 
						} catch (InvalidCardException | ErroCartaoException e1) {
							System.out.println("Pagamento inválido");

						}
					}

				}catch(NaoExistemComerciantesException e) {  
					System.out.println(e.getMessage()); 
				}
			} catch (AccaoInvalidaException e1) {
				System.out.println(e1.getMessage());
			}

		});

	}
}
