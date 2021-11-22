package pt.tooyummytogo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PeriodoVenda {
	
	private LocalDateTime inicio;
	private LocalDateTime fim;
	private HashMap<String, ProdutosDia> produtos = new HashMap<String, ProdutosDia>();
	
	

	public PeriodoVenda(LocalDateTime inicio, LocalDateTime fim) {
		
		this.inicio = inicio;
		this.fim = fim;
	}
	


	public void setHorario(LocalDateTime inicio, LocalDateTime fim) {
		
		this.inicio = inicio;
		this.fim = fim;	
	}



	public void adicionaProduto(ProdutosDia pd) {
		
		produtos.put(pd.getNome(), pd);		
	}


	/**
	 * @return true se existirem produtos do dia com pelo menos uma unidade
	 */
	public boolean temProdutos() {
		
		return !produtosDisponiveis().isEmpty();
	}


	
	public boolean horarioCompativel(LocalDateTime inicio, LocalDateTime fim) {
				
		boolean inicioAntesOuIgual = this.inicio.isBefore(inicio) || this.inicio.isEqual(inicio);
		boolean inicioEntre = this.inicio.isAfter(inicio) && this.inicio.isBefore(fim);
		
		boolean fimEntre = this.fim.isBefore(fim) && this.fim.isAfter(inicio);
		boolean fimDepoisOuIgual = this.fim.isAfter(fim) || this.fim.isEqual(fim);
		
		return (inicioAntesOuIgual && fimDepoisOuIgual) || (inicioAntesOuIgual && fimEntre)
				|| (inicioEntre && fimEntre) || (inicioEntre && fimDepoisOuIgual);		
	}
	
	/**
	 * @return lista de Produtos do dia desta venda que têm pelo menos uma unidade disponivel
	 */
	public List<ProdutosDia> produtosDisponiveis(){
		
		List<ProdutosDia> lista = new ArrayList<>();
		produtos.forEach((key,value) -> lista.add(value));	
		return lista.stream().filter(pd -> (pd.getQuantidade() > 0)).collect(Collectors.toList());
	}
	

	public ProdutosDia getProduto(String nome) {
		
		return produtos.get(nome);
	}

}
