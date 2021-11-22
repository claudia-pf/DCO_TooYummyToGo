package pt.tooyummytogo.tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.SessaoComerciante;
import pt.tooyummytogo.SessaoUtilizador;
import pt.tooyummytogo.domain.CatComerciantes;
import pt.tooyummytogo.domain.Comerciante;
import pt.tooyummytogo.domain.Utilizador;
import pt.tooyummytogo.exceptions.AccaoInvalidaException;
import pt.tooyummytogo.exceptions.UsernameJaEmUsoException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;


class TestLogin {

	private TooYummyToGo ty2g;

	private Optional<Sessao> sessaoEsperada;
	private Optional<Sessao> sessaoObtida;


	@BeforeEach
	public void setUp() {
		
		ty2g = new TooYummyToGo();
		RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
		RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();

		try {
			regHandler.registarUtilizador("Felismina", "hortadafcul");
			regComHandler.registarComerciante("Silvino", "bardoc2", new PosicaoCoordenadas(34.5, 45.2));
			regComHandler.registarComerciante("Maribel", "torredotombo", new PosicaoCoordenadas(33.5, 45.2));
		} catch (UsernameJaEmUsoException e) {
			System.out.println(e.getMessage());
		}
	}



	@Test
	void testUtilizadorNaoRegistado() {

		sessaoObtida = ty2g.autenticar("Anabela", "password123");
		sessaoEsperada = Optional.empty();

		assertEquals(sessaoEsperada, sessaoObtida);
	}

	@Test
	void testPasswordErrada() {

		sessaoObtida = ty2g.autenticar("Maribel", "password123");
		sessaoEsperada = Optional.empty();

		assertEquals(sessaoEsperada, sessaoObtida);
	}

	@Test
	void testUsernameErrado() {

		sessaoObtida = ty2g.autenticar("Maribela", "torredotombo");
		sessaoEsperada = Optional.empty();

		assertEquals(sessaoEsperada, sessaoObtida);
	}

	@Test
	void testUtilizadorRegistado() {

		sessaoObtida = ty2g.autenticar("Felismina", "hortadafcul");
		
		sessaoEsperada = Optional.of(new SessaoUtilizador(new Utilizador("Felismina", "hortadafcul"), new CatComerciantes()));	

		assertEquals(sessaoEsperada, sessaoObtida);
	}

	@Test
	void testComercianteRegistado() {

		sessaoObtida = ty2g.autenticar("Silvino", "bardoc2");
		sessaoEsperada = Optional.of(new SessaoComerciante(new Comerciante("Silvino", "bardoc2",new PosicaoCoordenadas(34.5, 45.2))));	

		assertEquals(sessaoEsperada, sessaoObtida);
	}


	@Test //utilizador tem acesso ao handler
	void testUtilizadorHandlerEncomendar() {

		Optional<Sessao> sessao = ty2g.autenticar("Felismina", "hortadafcul");
		if(!sessao.isPresent()) {
			fail();
		}else {
			sessao.ifPresent( (Sessao s) -> {
				EncomendarHandler handlerObtido;

				try {
					handlerObtido = s.getEncomendarComerciantesHandler();
					assertTrue(handlerObtido instanceof EncomendarHandler);
				} catch (AccaoInvalidaException e) {
					// do nothing
				}
			});
		}
	}


	@Test //utilizador nao tem acesso ao handler
	void testUtilizadorHandlerColocarProduto() {

		Optional<Sessao> sessao = ty2g.autenticar("Felismina", "hortadafcul");
		if(!sessao.isPresent()) {
			fail();
		}else {
			sessao.ifPresent( (Sessao s) -> {
				assertThrows(AccaoInvalidaException.class, () -> {
					s.getColocarProdutoHandler();
				});
			});
		}	
	}

	@Test //utilizador nao tem acesso ao handler
	void testUtilizadorHandlerAdicionarTipoProduto() {

		Optional<Sessao> sessao = ty2g.autenticar("Felismina", "hortadafcul");
		if(!sessao.isPresent()) {
			fail();
		}else {
			sessao.ifPresent( (Sessao s) -> {
				assertThrows(AccaoInvalidaException.class, () -> {
					s.adicionarTipoDeProdutoHandler();
				});
			});
		}	
	}

	@Test //comerciante tem acesso ao handler
	void testComercianteHandlerAdicionarTipoProduto() {

		Optional<Sessao> sessao = ty2g.autenticar("Silvino", "bardoc2");

		if(!sessao.isPresent()) {
			fail();
		}else {
			sessao.ifPresent( (Sessao s) -> {
				AdicionarTipoDeProdutoHandler handlerObtido;

				try {
					handlerObtido = s.adicionarTipoDeProdutoHandler();
					assertTrue(handlerObtido instanceof AdicionarTipoDeProdutoHandler);
				} catch (AccaoInvalidaException e) {
					// do nothing
				}
			});
		}
	}

	@Test //comerciante tem acesso ao handler
	void testComercianteHandlerColocarProduto() {

		Optional<Sessao> sessao = ty2g.autenticar("Silvino", "bardoc2");

		if(!sessao.isPresent()) {
			fail();
		}else {
			sessao.ifPresent( (Sessao s) -> {
				ColocarProdutoHandler handlerObtido;

				try {
					handlerObtido = s.getColocarProdutoHandler();
					assertTrue(handlerObtido instanceof ColocarProdutoHandler);
				} catch (AccaoInvalidaException e) {
					// do nothing
				}
			});
		}
	}


	@Test //comerciante nao tem acesso ao handler
	void testComercianteHandlerEncomendar() {

		Optional<Sessao> sessao = ty2g.autenticar("Silvino", "bardoc2");
		if(!sessao.isPresent()) {
			fail();
		}else {
			sessao.ifPresent( (Sessao s) -> {
				assertThrows(AccaoInvalidaException.class, () -> {
					s.getEncomendarComerciantesHandler();
				});
			});
		}	
	}


}
