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

public class ClienteExemplo {
	public static void main(String[] args) {
		TooYummyToGo ty2g = new TooYummyToGo();

		// UC1
		RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
		try {
			regHandler.registarUtilizador("Felismina", "hortadafcul");
		} catch (UsernameJaEmUsoException e) {
			System.out.println(e.getMessage());
		}


		// UC3
		RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();

		try {
			regComHandler.registarComerciante("Silvino", "bardoc2", new PosicaoCoordenadas(34.5, 45.2));
		} catch (UsernameJaEmUsoException e2) {
			System.out.println(e2.getMessage());
		}
		try {
			regComHandler.registarComerciante("Maribel", "torredotombo", new PosicaoCoordenadas(33.5, 45.2));
		} catch (UsernameJaEmUsoException e1) {
			System.out.println(e1.getMessage());
		}

		// UC4
		Optional<Sessao> talvezSessao = ty2g.autenticar("Silvino", "bardoc2");
		talvezSessao.ifPresent((Sessao s) -> {
			AdicionarTipoDeProdutoHandler atp;
			try {
				atp = s.adicionarTipoDeProdutoHandler();

				Random r = new Random();
				for (String tp : new String[] {"Pão", "Pão de Ló", "Mil-folhas"}) {
					atp.registaTipoDeProduto(tp, r.nextDouble() * 10);
				}
			} catch (AccaoInvalidaException e) {
				System.out.println(e.getMessage());
			}
		});

		// UC5
		Optional<Sessao> talvezSessao2 = ty2g.autenticar("Silvino", "bardoc2");
		talvezSessao2.ifPresent( (Sessao s) -> {

			ColocarProdutoHandler cpv;
			try {
				cpv = s.getColocarProdutoHandler();


				List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();


				//	cpv.indicaProduto(listaTiposDeProdutos.get(0), 10); // Pão
				//	cpv.indicaProduto(listaTiposDeProdutos.get(2), 5); // Mil-folhas

				try {
					cpv.indicaProduto(listaTiposDeProdutos.get(0), 10); // Pão
				} catch (NaoExisteTipoProdException e) {
					System.out.println(e.getMessage());
				} 
				try {
					cpv.indicaProduto(listaTiposDeProdutos.get(2), 5);// Mil-folhas
				} catch (NaoExisteTipoProdException e) {
					System.out.println(e.getMessage());
				} 


				cpv.confirma(LocalDateTime.now(), LocalDateTime.now().plusHours(2));

				System.out.println("Produtos disponíveis");
			} catch (AccaoInvalidaException e1) {
				System.out.println(e1.getMessage());
			}
		});

		// UC6 + UC7
		Optional<Sessao> talvezSessao3 = ty2g.autenticar("Felismina", "hortadafcul");
		talvezSessao3.ifPresent( (Sessao s) -> {
			EncomendarHandler lch;
			try {
				lch = s.getEncomendarComerciantesHandler();

				try {
					List<ComercianteInfo> cs = lch.indicaLocalizacaoActual(new PosicaoCoordenadas(34.5, 45.2));

					for (ComercianteInfo i : cs) {
						System.out.println(i);
					}

					boolean redefineRaio = false;
					if (redefineRaio) {
						cs = lch.redefineRaio(100);
						for (ComercianteInfo i : cs) {
							System.out.println(i);
						}
					}

					boolean redefinePeriodo = false;
					if (redefinePeriodo) {
						cs = lch.redefinePeriodo(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
						for (ComercianteInfo i : cs) {
							System.out.println(i);
						}
					}

					// A partir de agora é UC7
					List<ProdutoInfo> ps = lch.escolheComerciante(cs.get(0));
					for (ProdutoInfo p : ps) {
						try {
							lch.indicaProduto(p, 1);// Um de cada
						} catch (QuantidadeIndisponivelException e) {
							System.out.println(e.getMessage());
						} 
					}
					String codigoReserva;

					try {
						codigoReserva = lch.indicaPagamento("365782312312", "02/21", "766");
						System.out.println("Reserva feita com sucesso. Codigo: " + codigoReserva); 
					} catch (InvalidCardException e) {
						System.out.println("Erro no pagamento (PortugueseExpress)");
					} catch (ErroCartaoException e) {
						System.out.println(e.getMessage());
					} 

				}catch(NaoExistemComerciantesException e) {
					System.out.println(e.getMessage());
				}
			} catch (AccaoInvalidaException e) {
				System.out.println(e.getMessage());
			}

		});

	}
}
