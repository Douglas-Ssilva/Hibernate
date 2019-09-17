package br.com.projeto.crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.projeto.modelo.Account;
import br.com.projeto.modelo.Movimentacao;
import br.com.projeto.modelo.TipoMovimentacao;
import br.com.projeto.util.JPAUtil;

public class JPQL {

	public static void main(String[] args) {
//		EntityManager entityManager= new JPAUtil().getEntityManager();
//		Account account= entityManager.find(Account.class, 1L);
//		String jpql="select m from Movimentacao m where m.account= :pAccount and m.tipoMovimentacao= :pTipo order by m.valor";
//		Query query= entityManager.createQuery(jpql);
//		query.setParameter("pAccount", account);
//		query.setParameter("pTipo", TipoMovimentacao.ENTRADA);
//		List<Movimentacao> list  = query.getResultList();
//		
//		for (Movimentacao movimentacao : list) {
//			System.out.println("Movimentação: " + movimentacao.getDescricao());
//			System.out.println("Conta.ID: " + movimentacao.getAccount().getId());
//		}
//		
//		entityManager.close();
		
		EntityManager manager= new JPAUtil().getEntityManager();
		Account account = manager.find(Account.class, 1L);
		Query query = manager.createQuery("select m from Movimentacao m where m.account= :pAccount and m.tipoMovimentacao= :pTipo");
		query.setParameter("pAccount", account);
		query.setParameter("pTipo", TipoMovimentacao.ENTRADA);
		List<Movimentacao> list = query.getResultList();
		for (Movimentacao movimentacao : list) {
			System.out.println(movimentacao.getDescricao());
		}
		manager.close();
	}

}
