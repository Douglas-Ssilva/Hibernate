package br.com.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.model.Dominio;
import br.com.model.Usuario;
import br.com.model.UsuarioDTO;

public class ConsultasFormaProgramaticaCriteria {

	public static void main(String[] args) {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Usuarios-PU");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

//		primeirasConsultas(entityManager);
//		choosingReturnEntity(entityManager);
//		choosingReturnAttributeSimples(entityManager);
//		selectManyFields(entityManager);
//		selectManyFieldsDTO(entityManager);
//		passingParameters(entityManager);
//		ordersResults(entityManager);
		paginator(entityManager);
		
		entityManager.close();
		entityManagerFactory.close();
		
	}

	private static void paginator(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> createQuery = criteriaBuilder.createQuery(Usuario.class); 
		Root<Usuario> root = createQuery.from(Usuario.class);
		createQuery.select(root);
		TypedQuery<Usuario> typedQuery = entityManager.createQuery(createQuery)
				.setFirstResult(0) //Formula= (pagina - 1) * QTDE pag(nesse caso 2)
				.setMaxResults(2);
		List<Usuario> list = typedQuery.getResultList();
		list.forEach(System.out::println);
		
	}

	private static void ordersResults(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> createQuery = criteriaBuilder.createQuery(Usuario.class); 
		Root<Usuario> root = createQuery.from(Usuario.class);
		createQuery.select(root);
		createQuery.orderBy(criteriaBuilder.asc(root.get("nome")));
		TypedQuery<Usuario> typedQuery = entityManager.createQuery(createQuery);
		List<Usuario> list = typedQuery.getResultList();
		list.forEach(System.out::println);
	}

	private static void passingParameters(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> createQuery = criteriaBuilder.createQuery(Usuario.class); 
		Root<Usuario> root = createQuery.from(Usuario.class);
		createQuery.select(root);
		createQuery.where(criteriaBuilder.equal(root.get("id"), 1));
		TypedQuery<Usuario> typedQuery = entityManager.createQuery(createQuery);
		Usuario usuario = typedQuery.getSingleResult();
		System.out.println(usuario);
	}

	private static void selectManyFieldsDTO(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UsuarioDTO> createQuery = criteriaBuilder.createQuery(UsuarioDTO.class); 
		Root<Usuario> root = createQuery.from(Usuario.class);
		createQuery.select(criteriaBuilder.construct(UsuarioDTO.class ,root.get("id"), root.get("login"), root.get("senha"))); //passar campos conforme construtor do DTO 
		TypedQuery<UsuarioDTO> typedQuery = entityManager.createQuery(createQuery);
		List<UsuarioDTO> list = typedQuery.getResultList();
		list.forEach(System.out::println);
//		Hibernate: select usuario0_.id as col_0_0_, usuario0_.login as col_1_0_, usuario0_.senha as col_2_0_ from TB_USUARIO usuario0_
	}

	private static void selectManyFields(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> createQuery = criteriaBuilder.createQuery(Object[].class); 
		Root<Usuario> root = createQuery.from(Usuario.class);
		createQuery.multiselect(root.get("nome"), root.get("senha"), root.get("login")); 
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(createQuery);
		List<Object[]> list = typedQuery.getResultList();
		list.forEach(obj -> System.out.println(String.format("%s, %s, %s", obj)));
//		Hibernate: select usuario0_.nome as col_0_0_, usuario0_.senha as col_1_0_, usuario0_.login as col_2_0_ from TB_USUARIO usuario0_
	}

	private static void choosingReturnAttributeSimples(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<String> createQuery = criteriaBuilder.createQuery(String.class); 
		Root<Usuario> root = createQuery.from(Usuario.class);
		createQuery.select(root.get("nome")); 
		TypedQuery<String> typedQuery = entityManager.createQuery(createQuery);
		typedQuery.getResultList().forEach(System.out::println);
//		Hibernate: select usuario0_.nome as col_0_0_ from TB_USUARIO usuario0_
	}

	private static void choosingReturnEntity(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Dominio> createQuery = criteriaBuilder.createQuery(Dominio.class); 
		Root<Usuario> root = createQuery.from(Usuario.class);
		createQuery.select(root.get("dominio")); 
		TypedQuery<Dominio> typedQuery = entityManager.createQuery(createQuery);
		typedQuery.getResultList().forEach(System.out::println);
	}

	private static void primeirasConsultas(EntityManager entityManager) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder(); //metodos equivalentes as clausulas, fazer filtros, usar funções do sql, equals, like
		CriteriaQuery<Usuario> createQuery = criteriaBuilder.createQuery(Usuario.class); //metodos equivalentes as clausulas select, from, order by, having
		Root<Usuario> root = createQuery.from(Usuario.class);
		createQuery.select(root); //opcional
		TypedQuery<Usuario> typedQuery = entityManager.createQuery(createQuery);
		typedQuery.getResultList().forEach(System.out::println);
		//varios selects
	}

}
