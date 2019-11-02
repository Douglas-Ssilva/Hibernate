package br.com.test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.model.Configuracao;
import br.com.model.Usuario;

public class Joins {

	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Usuarios-PU");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

//		join(entityManager);
//		leftJoin(entityManager);
//		joinFetch(entityManager);
//		filter(entityManager);
//		usingOperatorLogicos(entityManager);
//		usingOperatorLogicosIn(entityManager);
//		order(entityManager);
		paginatorResults(entityManager);
		
		entityManager.close();
		entityManagerFactory.close();

	}

	private static void paginatorResults(EntityManager entityManager) {
		List<Usuario> list = entityManager.createQuery("select u from Usuario u", Usuario.class)
				.setFirstResult(0) //traga a partir de
				.setMaxResults(2) //Máximo de resultados
				.getResultList();
		list.forEach(System.out::println);
	}

	private static void order(EntityManager entityManager) {
		List<Usuario> list = entityManager.createQuery("select u from Usuario u order by u.dominio.nome", Usuario.class).getResultList();
		list.forEach(System.out::println);
	}

	private static void usingOperatorLogicosIn(EntityManager entityManager) {
		List<Usuario> list = entityManager.createQuery("select u from Usuario u where u.id in (:ids)", Usuario.class).setParameter("ids", Arrays.asList(1,2)).getResultList();
		list.forEach(System.out::println);
	}

	private static void usingOperatorLogicos(EntityManager entityManager) {
		List<Usuario> list = entityManager.createQuery("select u from Usuario u where u.ultimoAcesso > :ontem and u.ultimoAcesso < :hoje", Usuario.class).setParameter("ontem", LocalDateTime.now().minusDays(1))
				.setParameter("hoje", LocalDateTime.now())
				.getResultList(); //get user que acessaram o sistema de ontem p hoje
		list.forEach(System.out::println);
	}

	private static void filter(EntityManager entityManager) {
//		LIKE, IS NULL, IS EMPTY, BETWEEN, >, <, >=, <=, <>
//		select d from Dominio d where d.usuarios is empty Trazendo todos os dominios que possuem a lista de user vazia
		
//		Usuario usuario = entityManager.createQuery("select u from Usuario u where u.nome like :nome", Usuario.class).setParameter("nome", "Other%").getSingleResult();
//		Usuario usuario = entityManager.createQuery("select u from Usuario u where u.nome like concat(:nome, '%')", Usuario.class).setParameter("nome", "Other").getSingleResult();
		List<Usuario> list = entityManager.createQuery("select u from Usuario u where u.ultimoAcesso between :ontem and :hoje", Usuario.class).setParameter("ontem", LocalDateTime.now().minusDays(1))
				.setParameter("hoje", LocalDateTime.now())
				.getResultList(); //get user que acessaram o sistema de ontem p hoje
		list.forEach(System.out::println);
	}

	private static void joinFetch(EntityManager entityManager) {
//		List<Usuario> users= entityManager.createQuery("select u from Usuario u", Usuario.class).getResultList();
//		users.forEach(System.out::println);
//		Hibernate: select usuario0_.id as id1_2_, usuario0_.dominio_id as dominio_6_2_, usuario0_.login as login2_2_, usuario0_.nome as nome3_2_, usuario0_.senha as senha4_2_, usuario0_.ultimoAcesso as ultimoAc5_2_ from TB_USUARIO usuario0_
//		Hibernate: select dominio0_.id as id1_1_0_, dominio0_.nome as nome2_1_0_ from TB_DOMINIO dominio0_ where dominio0_.id=?
//		Hibernate: select configurac0_.usuario_id as usuario_3_0_0_, configurac0_.encerrarSessaoAutomaticamente as encerrar1_0_0_, configurac0_.receberNotificacoes as receberN2_0_0_ from TB_CONFIGURACAO configurac0_ where configurac0_.usuario_id=?
//		Hibernate: select configurac0_.usuario_id as usuario_3_0_0_, configurac0_.encerrarSessaoAutomaticamente as encerrar1_0_0_, configurac0_.receberNotificacoes as receberN2_0_0_ from TB_CONFIGURACAO configurac0_ where configurac0_.usuario_id=?
//		Hibernate: select dominio0_.id as id1_1_0_, dominio0_.nome as nome2_1_0_ from TB_DOMINIO dominio0_ where dominio0_.id=?
//		Hibernate: select configurac0_.usuario_id as usuario_3_0_0_, configurac0_.encerrarSessaoAutomaticamente as encerrar1_0_0_, configurac0_.receberNotificacoes as receberN2_0_0_ from TB_CONFIGURACAO configurac0_ where configurac0_.usuario_id=?
//		Executa só uma query no dominio e guarda no cache de primeiro nivel, caso seja a mesma
//		Configuração ele fica tentando trazer as config, por isso p cada user um select na config
		
//		List<Usuario> usersJoinFetch= entityManager.createQuery("select u from Usuario u join fetch u.configuracao", Usuario.class).getResultList();
//		usersJoinFetch.forEach(System.out::println);
//		Hibernate: select usuario0_.id as id1_2_0_, configurac1_.usuario_id as usuario_3_0_1_, usuario0_.dominio_id as dominio_6_2_0_, usuario0_.login as login2_2_0_, usuario0_.nome as nome3_2_0_, usuario0_.senha as senha4_2_0_, usuario0_.ultimoAcesso as ultimoAc5_2_0_, configurac1_.encerrarSessaoAutomaticamente as encerrar1_0_1_, configurac1_.receberNotificacoes as receberN2_0_1_ from TB_USUARIO usuario0_ inner join TB_CONFIGURACAO configurac1_ on usuario0_.id=configurac1_.usuario_id
//		Hibernate: select dominio0_.id as id1_1_0_, dominio0_.nome as nome2_1_0_ from TB_DOMINIO dominio0_ where dominio0_.id=?
		
		List<Usuario> usersJoinFetch= entityManager.createQuery("select u from Usuario u join fetch u.configuracao join fetch u.dominio" , Usuario.class).getResultList();
		usersJoinFetch.forEach(System.out::println);
//		Hibernate: select usuario0_.id as id1_2_0_, configurac1_.usuario_id as usuario_3_0_1_, dominio2_.id as id1_1_2_, usuario0_.dominio_id as dominio_6_2_0_, usuario0_.login as login2_2_0_, usuario0_.nome as nome3_2_0_, usuario0_.senha as senha4_2_0_, usuario0_.ultimoAcesso as ultimoAc5_2_0_, configurac1_.encerrarSessaoAutomaticamente as encerrar1_0_1_, configurac1_.receberNotificacoes as receberN2_0_1_, dominio2_.nome as nome2_1_2_ from TB_USUARIO usuario0_ inner join TB_CONFIGURACAO configurac1_ on usuario0_.id=configurac1_.usuario_id inner join TB_DOMINIO dominio2_ on usuario0_.dominio_id=dominio2_.id
	}

	private static void leftJoin(EntityManager entityManager) {
		//Inner join traz somentes os que possui relacionamento. Left traz os que possuem relacionamento e também todos user que nao possui
		List<Object[]> args = entityManager.createQuery("select u, c from Usuario u left join u.configuracao c", Object[].class).getResultList();
		args.forEach(obj -> {
			String out= ((Usuario) obj[0]).getNome() + ", ";
			
			if(obj[1] != null) {
				out += ((Configuracao) obj[1]).getId();
			}else {
				out+= "NULL";
			}
			System.out.println(out);
		});
//		Other, 1
//		Admin, NULL
//		Test, NULL
	}

	private static void join(EntityManager entityManager) {
//		slq= "select u.* from usuario u join dominio d on u.dominio.id= d.id;" Trazendo os usuarios do dominio X
		List<Usuario> usuarios = entityManager.createQuery("select u from Usuario u join u.dominio d where d.id= :pId", Usuario.class).setParameter("pId", 1).getResultList();
		usuarios.forEach(System.out::println);
//		Hibernate: select usuario0_.id as id1_2_, usuario0_.dominio_id as dominio_6_2_, usuario0_.login as login2_2_, usuario0_.nome as nome3_2_, usuario0_.senha as senha4_2_, usuario0_.ultimoAcesso as ultimoAc5_2_ from TB_USUARIO usuario0_ inner join TB_DOMINIO dominio1_ on usuario0_.dominio_id=dominio1_.id where dominio1_.id=?
//		Hibernate: select dominio0_.id as id1_1_0_, dominio0_.nome as nome2_1_0_ from TB_DOMINIO dominio0_ where dominio0_.id=?
	}

}
