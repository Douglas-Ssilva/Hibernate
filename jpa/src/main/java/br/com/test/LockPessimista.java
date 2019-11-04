package br.com.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

import br.com.model.Artigo;

//Usa-se recursos do BD
public class LockPessimista {

	private static final Integer ID = 1;

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Blog-PU");
	        entendendoAsOpcoes(entityManagerFactory);
//	        javaEOWorkbench(entityManagerFactory);
//		casoMaisPratico(entityManagerFactory);
		entityManagerFactory.close();
	}

	public static void casoMaisPratico(EntityManagerFactory entityManagerFactory) {
		EntityManager entityManager1 = entityManagerFactory.createEntityManager();
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();

		Runnable runnable1Joao = () -> {
			entityManager1.getTransaction().begin();
			log(1, "Imediatamente antes find.");
			Artigo artigo1 = entityManager1.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
			log(1, "Imediatamente ap�s find.");

			artigo1.setConteudo("Altera��o do Jo�o (TH1)");

			log(1, "Esperando 3 segundos...");
			esperar(3000);
			log(1, "Espera dos 3 segs terminada.");

			log(1, "Imediatamente antes do commit.");
			entityManager1.getTransaction().commit();
			log(1, "Imediatamente ap�s o commit.");
		};

		Runnable runnable2Maria = () -> {
			log(2, "Esperando 100 milis...");
			esperar(100);
			log(2, "Espera dos 100 milis terminada.");

			entityManager2.getTransaction().begin();
			log(2, "Imediatamente antes find.");
			Artigo artigo2 = entityManager2.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
			log(2, "Imediatamente ap�s find.");

			artigo2.setConteudo(artigo2.getConteudo() + " + Altera��o da Maria (TH2)");

			log(2, "Imediatamente antes do commit.");
			entityManager2.getTransaction().commit();
			log(2, "Imediatamente ap�s o commit.");
		};

		Thread thread1 = new Thread(runnable1Joao);
		Thread thread2 = new Thread(runnable2Maria);

		thread1.start();
		thread2.start();
	}

	public static void javaEOWorkbench(EntityManagerFactory entityManagerFactory) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();
		log(1, "Imediatamente antes find.");
		Artigo artigo1 = entityManager.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
		log(1, "Imediatamente ap�s find.");

		artigo1.setConteudo("Altera��o do Jo�o (TH1)");

		log(1, "Esperando 25 segundos...");
		esperar(25000);
		log(1, "Espera dos 25 segs terminada.");

		log(1, "Imediatamente antes do commit.");
		entityManager.getTransaction().commit();
		log(1, "Imediatamente ap�s o commit.");
	}

	public static void entendendoAsOpcoes(EntityManagerFactory entityManagerFactory) {
		EntityManager entityManager1 = entityManagerFactory.createEntityManager();
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();

		entityManager1.getTransaction().begin();
		Artigo artigo1 = entityManager1.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE); //P usar esse recurso necessito de uma transaction, ainda que use o find
		artigo1.setConteudo("Altera��o do Jo�o");
		entityManager1.getTransaction().commit();

		entityManager2.getTransaction().begin();
		Artigo artigo2 = entityManager2.find(Artigo.class, ID, LockModeType.PESSIMISTIC_WRITE);
		artigo2.setConteudo(artigo2.getConteudo() + " + Altera��o da Maria");
		entityManager2.getTransaction().commit();

//	        entityManager1.getTransaction().commit();
	}

	private static void log(Integer thread, String msg) {
		System.out.println("[THREAD_" + thread + "] " + msg);
	}

	private static void esperar(long milesegundos) {
		try {
			Thread.sleep(milesegundos);
		} catch (Exception e) {
		}
	}

}
