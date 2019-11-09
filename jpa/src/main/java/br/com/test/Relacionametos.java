package br.com.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mapeamentos.Curso;
import br.com.mapeamentos.Modulo;

public class Relacionametos {
	
    public static void main(String[] args) {
        //    LISTAS -> LAZY
    	// <> LISTAS -> EAGER
    	//    MANYTOMANY EM PROJETOS REAIS É POUCO USADO, VISTO QUE SEMPRE NECESSITA DE ATRIBUTOS A MAIS, ASSIM CRIA-SE MAIS UMA ENTIDADE E ESSE RELACIONAMENTO VIRA ONETOMANY
    	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Cursos-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Curso curso = new Curso();
        curso.setNome("Especialista JPA");

        Modulo modulo = new Modulo();
        modulo.setNome("Introdução ao JPA");
        modulo.setCurso(curso); //Para que o JPA faça o relacionamento é necessário que seja setado o atributo que é o main da relação. Senao nao faz o relacionamento

        //curso.setModulos(Arrays.asList(modulo));

        entityManager.getTransaction().begin();

        entityManager.persist(curso);
        entityManager.persist(modulo);

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

}
