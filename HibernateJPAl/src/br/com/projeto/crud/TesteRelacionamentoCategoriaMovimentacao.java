package br.com.projeto.crud;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.projeto.modelo.Account;
import br.com.projeto.modelo.Categoria;
import br.com.projeto.modelo.Movimentacao;
import br.com.projeto.modelo.TipoMovimentacao;
import br.com.projeto.util.JPAUtil;

public class TesteRelacionamentoCategoriaMovimentacao {
	
	public static void main(String[] args) {
		EntityManager manager= new JPAUtil().getEntityManager();	
		manager.getTransaction().begin();
		
		Categoria categoria= manager.find(Categoria.class, 1L);
		Categoria categoria2= manager.find(Categoria.class, 3L);
		
		Account account= manager.find(Account.class, 2L);
		
		
		Movimentacao movimentacao= new Movimentacao();
		movimentacao.setData(Calendar.getInstance());
		movimentacao.setDescricao("Espanha");
		movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao.setValor(new BigDecimal("100.0"));
		movimentacao.setAccount(account);
		movimentacao.setCategoria(Arrays.asList(categoria, categoria2));
		
		manager.persist(categoria);
		manager.persist(categoria2);
		manager.persist(movimentacao);
		manager.getTransaction().commit();
		manager.close();
	}

}
