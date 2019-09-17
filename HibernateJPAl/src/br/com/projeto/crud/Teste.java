package br.com.projeto.crud;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.projeto.modelo.Account;
import br.com.projeto.modelo.Movimentacao;
import br.com.projeto.util.JPAUtil;

public class Teste {

	public static void main(String[] args) {
		EntityManager entityManager= new JPAUtil().getEntityManager();
		Query query= entityManager.createQuery("select count(m) from Movimentacao m where m.account= :pConta");
		query.setParameter("pConta", entityManager.find(Account.class, 2L));
		Long total= (Long)query.getSingleResult();
		System.out.println(total);
		entityManager.close();
	}
	
	

}
