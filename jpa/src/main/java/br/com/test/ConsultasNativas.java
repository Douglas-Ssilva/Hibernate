package br.com.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.model.User;
import br.com.model.UserDTO;
import br.com.model.Usuario;

class ConsultasNativas {

	public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Usuarios-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        consultaSimplesMapeandoParaEntidade(entityManager);
//        mapeandoResultado(entityManager);
//        mapeandoResultadoParaUmDTO(entityManager);
        usandoNamedNativeQuery(entityManager);
        entityManager.close();
        entityManagerFactory.close();
    }

    public static void usandoNamedNativeQuery(EntityManager entityManager) {
        Query query = entityManager.createNamedQuery("namedQuery.Usuario");
        List<User> lista = query.getResultList();
//        lista.stream().forEach(u -> System.out.println(String.format("Usuário => Id: %s, Nome: %s", u.getId(), u.getNome())));
    }

    public static void mapeandoResultadoParaUmDTO(EntityManager entityManager) { //usamos DTO quando nao queremos retornar todos os atributos da entidade
        Query query = entityManager.createNativeQuery("select usu_codigo, usu_email, usu_nome from sis_usuario","mapeamento.UsuarioDTO");
        List<UserDTO> lista = query.getResultList();
//        lista.stream().forEach(u -> System.out.println(String.format("Usuário => Id: %s, Nome: %s", u.getId(), u.getNome())));
    }

    public static void mapeandoResultado(EntityManager entityManager) {
//    	Query query = entityManager.createNativeQuery("select * fromusuario","mapeamento.Usuario");
        Query query = entityManager.createNativeQuery("select * from sis_usuario","mapeamento.Usuario");
        List<User> lista = query.getResultList();
//        lista.stream().forEach(u -> System.out.println(String.format("Usuário => Id: %s, Nome: %s", u.getId(), u.getNome())));
    }

	public static void consultaSimplesMapeandoParaEntidade(EntityManager entityManager) {
		//Posso fazer vários joins, o importante é que coloquemos alias igualmente o tipo de retorno da class
//		Query query = entityManager.createNativeQuery("select * from usuario",  Usuario.class); p usar o parameter da class, é preciso no select retornar todos os  atributos da class. Posso usar o * pois o select é na propria tabela de usuario
        Query query = entityManager.createNativeQuery("select usu_codigo id, usu_email login, usu_senha senha, usu_nome nome " + "from sis_usuario",  User.class);// select Other Table que não está amarrada a uma entidade JPA. Coloco alias referentes a minha entidade p JPA reconhecer
		List<User> lista = query.getResultList();
//        lista.stream().forEach(u -> System.out.println(String.format("Usuário => Id: %s, Nome: %s", u.getId(), u.getNome())));
	}

}
