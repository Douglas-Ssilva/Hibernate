package br.com.projeto.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory factory= Persistence.createEntityManagerFactory("financas");
	
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}
