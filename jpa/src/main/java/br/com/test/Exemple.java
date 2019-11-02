package br.com.test;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.model.Dominio;
import br.com.model.Usuario;
import br.com.model.UsuarioDTO;

public class Exemple {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Usuarios-PU");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

//        Dominio dominio = new Dominio();
//        dominio.setId(2);
//        dominio.setNome("Dados de banco");

        Usuario usuario = new Usuario();
        usuario.setId(3);
        usuario.setLogin("teste@email.com");
        usuario.setSenha("565");
        usuario.setUltimoAcesso(LocalDateTime.now());
        usuario.setDominio(entityManager.find(Dominio.class, 2));
        
        entityManager.getTransaction().begin();
        entityManager.persist(usuario);
        entityManager.getTransaction().commit();

//		primeiraConsulta(entityManager);
//		escolhendoRetorno(entityManager);
//		somenteLogins(entityManager);
//		fazendoProjecoes(entityManager);

		entityManager.close();
		entityManagerFactory.close();
	}

	private static void fazendoProjecoes(EntityManager entityManager) {
		TypedQuery<Object[]> createQuery = entityManager.createQuery("select id ,login, senha from Usuario", Object[].class);
		List<Object[]> args = createQuery.getResultList();
		args.forEach(arr -> System.out.println(String.format("%s, %s, %s", arr)));
//		Hibernate:  select  usuario0_.id as col_0_0_,  usuario0_.login as col_1_0_,  usuario0_.senha as col_2_0_  from TB_USUARIO usuario0_
		
		System.out.println("=========================");
		
		TypedQuery<UsuarioDTO> createQuery2 = entityManager.createQuery("select new br.com.model.UsuarioDTO(id ,login, senha) from Usuario", UsuarioDTO.class);
		List<UsuarioDTO> usuarioDTOs = createQuery2.getResultList();
		usuarioDTOs.forEach(System.out::println);
	}

	private static void somenteLogins(EntityManager entityManager) {
		TypedQuery<String> createQuery = entityManager.createQuery("select u.login from Usuario u", String.class);
		List<String> login = createQuery.getResultList();
		login.forEach(System.out::println);
//		Hibernate:  select usuario0_.login as col_0_0_  from  TB_USUARIO usuario0_
	}

	private static void escolhendoRetorno(EntityManager entityManager) {
		TypedQuery<Dominio> createQuery = entityManager.createQuery("select u.dominio from Usuario u where u.id= 1", Dominio.class);
		Dominio dominio = createQuery.getSingleResult();
		System.out.println(dominio.getNome());
//		select  dominio1_.id as id1_1_, dominio1_.nome as nome2_1_  from  TB_USUARIO usuario0_  inner join TB_DOMINIO dominio1_  on usuario0_.dominio_id=dominio1_.id where
//        usuario0_.id=1
	}

//Um select que gera outros vários é o problema do N + 1
	private static void primeiraConsulta(EntityManager entityManager) {
		TypedQuery<Usuario> createQuery = entityManager.createQuery("select u from Usuario u", Usuario.class);
		List<Usuario> resultList = createQuery.getResultList();
		resultList.forEach(System.out::println);
//		select  usuario0_.id as id1_2_,  usuario0_.dominio_id as dominio_6_2_, usuario0_.login as login2_2_,  usuario0_.nome as nome3_2_,  usuario0_.senha as senha4_2_,  usuario0_.ultimoAcesso as ultimoAc5_2_ 
//		from    TB_USUARIO usuario0_ Hibernate: select  dominio0_.id as id1_1_0_,  dominio0_.nome as nome2_1_0_  from  TB_DOMINIO dominio0_  where  dominio0_.id=?
//		Hibernate:  select  dominio0_.id as id1_1_0_,  dominio0_.nome as nome2_1_0_ from     TB_DOMINIO dominio0_  where     dominio0_.id=?

		System.out.println("=================================");

		TypedQuery<Usuario> createQuery2 = entityManager.createQuery("select u from Usuario u where u.id= 1",
				Usuario.class);
		Usuario usuario = createQuery2.getSingleResult();
		System.out.println(usuario);
		System.out.println("=================================");

		Query query = entityManager.createQuery("select u from Usuario u where u.id= 1");
		Usuario usuario2 = (Usuario) query.getSingleResult();
		System.out.println(usuario2);
	}

}
