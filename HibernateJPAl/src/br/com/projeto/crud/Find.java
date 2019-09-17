package br.com.projeto.crud;

import javax.persistence.EntityManager;

import br.com.projeto.modelo.Account;
import br.com.projeto.util.JPAUtil;

public class Find {

	public static void main(String[] args) {

		Account conta1 = new Account();
		conta1.setId(1L);

		EntityManager manager = new JPAUtil().getEntityManager();

		manager.getTransaction().begin();
		conta1 = manager.find(Account.class, 1L);
		manager.remove(conta1);
		manager.getTransaction().commit();
		manager.close();

	}
}
