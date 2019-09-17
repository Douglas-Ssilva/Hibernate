package br.com.projeto.crud;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.projeto.modelo.Account;
import br.com.projeto.modelo.Movimentacao;
import br.com.projeto.modelo.TipoMovimentacao;
import br.com.projeto.util.JPAUtil;

public class TesteRelacionamento {
	
	public static void main(String[] args) {
		
		EntityManager manager= new JPAUtil().getEntityManager();
		
		Account account= new Account();
		account.setId(4L);
		
		Movimentacao movimentacao= new Movimentacao();
		movimentacao.setData(Calendar.getInstance());
		movimentacao.setDescricao("Faculdade");
		movimentacao.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		movimentacao.setValor(new BigDecimal("1000.0"));
		movimentacao.setAccount(account);
		
		manager.getTransaction().begin();
		manager.persist(movimentacao);
		manager.getTransaction().commit();
		manager.close();
	}

}
