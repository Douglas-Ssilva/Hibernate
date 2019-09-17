package br.com.projeto.crud;

import javax.persistence.EntityManager;

import br.com.projeto.modelo.Account;
import br.com.projeto.modelo.Movimentacao;
import br.com.projeto.util.JPAUtil;

public class RelacionamentoBidirecional {

	public static void main(String[] args) {
		
		EntityManager manager= new JPAUtil().getEntityManager();
		Movimentacao movimentacao = manager.find(Movimentacao.class, 2L);
		Account account = movimentacao.getAccount();
		System.out.println(account.getTitular() + " fez "+ account.getMovimentacoes().size()+" movimentações.");
	}

}
