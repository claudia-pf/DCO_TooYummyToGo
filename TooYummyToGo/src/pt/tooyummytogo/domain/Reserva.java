package pt.tooyummytogo.domain;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pt.tooyummytogo.events.EventoEncomendaComSucesso;
import pt.tooyummytogo.exceptions.QuantidadeIndisponivelException;
import utils.observer.Observable;

public class Reserva extends Observable<EventoEncomendaComSucesso>{

	private String codigo;
	private double total;
	private Estado estado;
	private List<ProdutosReservados> produtosReservados;

	/**
	 * Construtor de uma reserva com o nome do utilizador que esta a fazer a reserva
	 * @param nomeUtilizador dado
	 */
	public Reserva(String nomeUtilizador) {
		codigo = nomeUtilizador + LocalDateTime.now().toString();
		total = 0;
		estado = Estado.PENDENTE;
		produtosReservados = new ArrayList<>();	
	}

	/**
	 * @param pdia - produto do dia que se pretende reservar
	 * @param quantidade do produto que se pretende reservar
	 * @throws QuantidadeIndisponivelException
	 * @ensures Adiciona produtos à lista de produtos reservados apenas se a quantidade do produto do dia
	 * for igual ou inferior à quantidade pedida
	 */
	public void adicionaProduto(ProdutosDia pdia, int quantidade) throws QuantidadeIndisponivelException {

		int quantidadePdia = pdia.getQuantidade();
		String nomeProduto = pdia.getNome();
		ProdutosReservados pr = getProduto(nomeProduto);
		int quantidadeDisponivel = (pr == null)? quantidadePdia : quantidadePdia - pr.getQuantidade();

		if(quantidadeDisponivel >= quantidade) {
			if (pr == null) {
				produtosReservados.add(new ProdutosReservados(pdia, quantidade));
			}else {
				pr.acrescentaQuantidade(quantidade);
			}
			total = total + (quantidade * pdia.getPreco());
		}else {
			throw new QuantidadeIndisponivelException("Quantidade pedida indisponível. Quantidade disponivel de "
					+ nomeProduto + ": " + quantidadeDisponivel + " unidade(s)");
		}

	}


	public double getTotal() {
		return total;
	}

	/**
	 * Atualiza a quantidade disponivel dos produtos do dia associados aos produtos reservados desta reserva
	 * e devolve o codigo da reserva
	 * @return codigo da reserva
	 */
	public String completaReserva() {
		for (ProdutosReservados pr : produtosReservados) {
			pr.atualizaProdDia();
		}
		dispatchEvent(new EventoEncomendaComSucesso(codigo, getInfo()));
		return codigo;
	}

	/**
	 * Altera o estado da reserva para completa
	 */
	public void reservaRecolhida() {
		estado = Estado.COMPLETA;
	}
	
	
	public String getInfo() {
		StringBuilder info = new StringBuilder();
		info.append("Código da reserva: " + codigo);
		info.append("\nProdutos reservados:\n");
		
		for(ProdutosReservados pr : produtosReservados) {
			info.append(pr.getNome() + ", " + pr.getQuantidade() + " unidade(s)\n");
		}
		
		return info.toString();
	}


	private ProdutosReservados getProduto (String nomeProduto) {

		for(ProdutosReservados pr: produtosReservados) {
			if(pr.getNome().equals(nomeProduto)) {
				return pr;
			}
		}
		return null;
	}

}
