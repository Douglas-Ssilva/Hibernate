package br.com.test;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import br.com.operations.em.lote.Contato;
import br.com.operations.em.lote.Envio;
import br.com.operations.em.lote.Mensagem;
import br.com.operations.em.lote.TemperaturaContato;

public class OperacoesEmLote {
	 public static void main(String[] args) {
	        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Projetos-PU");
	        EntityManager entityManager = factory.createEntityManager();

//	        atualizacaoDeVariosRegistros(entityManager);
//	        atualizacaoDeVariosRegistrosEmLote(entityManager);
//	        atualizacaoDeVariosRegistrosEmLoteCriteria(entityManager);
//	        remocaoDeVariosRegistrosEmLote(entityManager);
//	        remocaoDeVariosRegistrosEmLoteCriteria(entityManager);

//	        insercaoDeVariosRegistros(entityManager);
//	        insercaoDeVariosRegistrosEmLote(entityManager);

	        entityManager.close();
	        factory.close();
	    }

//	 Melhor forma de otimizar a inserção
	    public static void insercaoDeVariosRegistrosEmLote(EntityManager entityManager) {
	        entityManager.getTransaction().begin();

	        List<Mensagem> mensagens = entityManager.createQuery("select m from Mensagem m").getResultList();
	        List<Contato> contatos = entityManager.createQuery("select c from Contato c").getResultList();

	        int limiteInsercoesMemoria = 3;
	        int contadorLimite = 1;

	        for (Mensagem mensagem: mensagens) {
	            for (Contato contato: contatos) {
	                Envio envio = new Envio();
	                envio.setMensagem(mensagem);
	                envio.setContato(contato);
	                envio.setDataEnvio(LocalDateTime.now());
	                entityManager.persist(envio);

	                if ((contadorLimite++) == limiteInsercoesMemoria) {
	                    entityManager.flush(); //obrigando a enviar p BD o que está na memoria
	                    entityManager.clear(); //limpando da memoria do objeto os já persistidos

	                    contadorLimite = 1;
	                    System.out.println("----------------- flush");
	                }
	            }
	        }

	        entityManager.getTransaction().commit();
	    }

	    public static void insercaoDeVariosRegistros(EntityManager entityManager) {
	        entityManager.getTransaction().begin();

	        List<Mensagem> mensagens = entityManager.createQuery("select m from Mensagem m").getResultList();
	        List<Contato> contatos = entityManager.createQuery("select c from Contato c").getResultList();

	        mensagens.forEach(mensagem -> {
	            contatos.forEach(contato -> {
	                Envio envio = new Envio();
	                envio.setMensagem(mensagem);
	                envio.setContato(contato);
	                envio.setDataEnvio(LocalDateTime.now());
	                entityManager.persist(envio);
	            });
	        });

	        entityManager.getTransaction().commit();
	    }

//	    Remove vários com um comando
	    public static void remocaoDeVariosRegistrosEmLoteCriteria(EntityManager entityManager) {
	    	entityManager.getTransaction().begin();
	    	
	    	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    	CriteriaDelete<Contato> createCriteriaDelete = criteriaBuilder.createCriteriaDelete(Contato.class);
	    	Root<Contato> root = createCriteriaDelete.from(Contato.class);
	    	createCriteriaDelete.where(criteriaBuilder.le(root.get("id"), 10));
	    	
	    	entityManager.createQuery(createCriteriaDelete).executeUpdate();
	    	entityManager.getTransaction().commit();
	    }
	    
//	    Remove vários com um comando
	    public static void remocaoDeVariosRegistrosEmLote(EntityManager entityManager) {
	        entityManager.getTransaction().begin();
	        entityManager.createQuery("delete from Contato c").executeUpdate();
	        entityManager.getTransaction().commit();
	    }

//	    Independente do tamanho da lista, manda somente um update p banco
	    public static void atualizacaoDeVariosRegistrosEmLoteCriteria(EntityManager entityManager) {
	        entityManager.getTransaction().begin();

	        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	        CriteriaUpdate<Contato> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Contato.class);
	        Root<Contato> root = criteriaUpdate.from(Contato.class);

	        criteriaUpdate.set(root.get("temperatura"), TemperaturaContato.FRIO);

	        entityManager.createQuery(criteriaUpdate).executeUpdate();
	        entityManager.getTransaction().commit();
	    }

//	    Independente do tamanho da lista, manda somente um update p banco
	    public static void atualizacaoDeVariosRegistrosEmLote(EntityManager entityManager) {
	        entityManager.getTransaction().begin();
	        entityManager.createQuery("update Contato c set c.temperatura = :temperatura").setParameter("temperatura", TemperaturaContato.FRIO).executeUpdate();
	        entityManager.getTransaction().commit();
	    }

//	    Pra cada contato da lista ele faz um update no BD
	    public static void atualizacaoDeVariosRegistros(EntityManager entityManager) {
	        entityManager.getTransaction().begin();
	        List<Contato> contatos = entityManager.createQuery("select c from Contato c").getResultList();
	        contatos.forEach(c -> c.setTemperatura(TemperaturaContato.FRIO));
	        entityManager.getTransaction().commit();
	    }


}
