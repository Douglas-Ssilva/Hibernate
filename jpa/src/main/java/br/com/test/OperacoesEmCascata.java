package br.com.test;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mapeamentos.Aluno;
import br.com.mapeamentos.Curso;
import br.com.mapeamentos.Modulo;

public class OperacoesEmCascata {
	
	public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Cursos-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        gravacaoEmCascata(entityManager);
//        gravacaoEmCascataUpdate(entityManager);
//        gravacaoEmCascataManyToMany(entityManager);
//        exclusaoEmCascata(entityManager);
//        exclusaoEmCascataComManyToMany(entityManager);
        exclusaoEmCascataRemovendoOrfaos(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    public static void gravacaoEmCascata(EntityManager entityManager) {
        Curso curso = new Curso();
        curso.setNome("Especialista Spring REST");

        Modulo modulo = new Modulo();
        modulo.setNome("Introdução ao Spring");
        modulo.setCurso(curso); //Transient

        curso.setModulos(Arrays.asList(modulo));

        entityManager.getTransaction().begin();
        entityManager.persist(curso);
//        entityManager.persist(modulo);
        entityManager.getTransaction().commit();
    }

    public static void gravacaoEmCascataUpdate(EntityManager entityManager) {
        Curso curso = new Curso();
        curso.setId(1);
        curso.setNome("Especialista JPA");

        Modulo modulo = new Modulo();
        modulo.setId(1);
        modulo.setNome("Nome alterado");
        modulo.setCurso(curso);

        curso.setModulos(Arrays.asList(modulo));

        entityManager.getTransaction().begin();
        entityManager.merge(curso);
        entityManager.getTransaction().commit();
    }

    public static void gravacaoEmCascataManyToMany(EntityManager entityManager) {
        Curso curso = new Curso();
        curso.setNome("Especialista Spring REST");

        Aluno aluno = new Aluno();
        aluno.setNome("Maria Carla");

        curso.setAlunos(Arrays.asList(aluno));

        entityManager.getTransaction().begin();
        entityManager.persist(curso);
//        entityManager.persist(aluno);
        entityManager.getTransaction().commit();
    }

    public static void exclusaoEmCascata(EntityManager entityManager) {
        Modulo modulo = entityManager.find(Modulo.class, 1);

        entityManager.getTransaction().begin();

//        modulo.getAulas().forEach(a -> entityManager.remove(a)); uso assim ou o cascade
        entityManager.remove(modulo); //cannot delete or update a parent row. Faz se necessário cascade

        entityManager.getTransaction().commit();
    }

    public static void exclusaoEmCascataComManyToMany(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        Curso curso = entityManager.find(Curso.class, 1);
        curso.getAlunos().clear(); //limpando a tabela que guarda os IDs
        entityManager.merge(curso);

        entityManager.getTransaction().commit();
    }

    public static void exclusaoEmCascataRemovendoOrfaos(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        Modulo modulo = entityManager.find(Modulo.class, 1);
        modulo.getAulas().clear();
//        entityManager.remove(modulo);

        entityManager.getTransaction().commit();
    }

}


































