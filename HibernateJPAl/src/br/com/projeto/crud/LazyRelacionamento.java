package br.com.projeto.crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.projeto.modelo.Account;
import br.com.projeto.modelo.Movimentacao;
import br.com.projeto.util.JPAUtil;

public class LazyRelacionamento {

	public static void main(String[] args) {
		EntityManager em = new JPAUtil().getEntityManager();
        Account conta = em.find(Account.class, 1L);
        List<Movimentacao> movimentacoes = conta.getMovimentacoes();
        em.close();

        for (Movimentacao movimentacao : movimentacoes) {
            System.out.println("Movimentação: " + movimentacao.getDescricao());
        }
		
	}
	
	public static void main2() {
		EntityManager manager= new JPAUtil().getEntityManager();
		Query query= manager.createQuery("select c from Account c join fetch c.movimentacoes");
		List<Account> list= query.getResultList();
		for (Account account : list) {
			System.out.println("Titular: " + account.getTitular());
			System.out.println("Movimentações: ");
			System.out.println(account.getMovimentacoes());
		}
		manager.close();
		
	}

}
