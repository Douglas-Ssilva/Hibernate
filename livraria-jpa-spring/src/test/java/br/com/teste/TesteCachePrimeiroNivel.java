package br.com.teste;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.com.caelum.JpaConfigurator;
import br.com.caelum.model.Produto;

public class TesteCachePrimeiroNivel {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JpaConfigurator.class);
		EntityManagerFactory entityManagerFactory = applicationContext.getBean(EntityManagerFactory.class);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();
		
		//Por serem entityManagers diferentes, faz 2 selects. Cache de primeiro nivel é por EntityManager
		Produto find = entityManager.find(Produto.class, 1);
		Produto find2 = entityManager2.find(Produto.class, 1);
		
		entityManager.close();

	}

}
