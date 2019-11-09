package br.com.test;

import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.elementcollection.Cliente;
import br.com.elementcollection.Endereco;
import br.com.elementcollection.TipoEndereco;

public class ElementCollection {
//	Criando relacionamentos com classes que nao sejam entidades, ex: String
//	Usamos quando queremos salvar alguma informa��o que nao seja na entidade main e que nao valha a pena criar uma entidade.
	

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Clientes-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua S�o Paulo");
        endereco.setNumero("3000");
        endereco.setBairro("Centro");
        endereco.setCidade("Belo Horizonte");
        endereco.setCep("00000-000");

        Cliente cliente = new Cliente();
        cliente.setNome("Jo�o da Silva");

//        cliente.setEnderecos(Arrays.asList("Rua Paran�, 10, Bairro Brasil - Belo Horizonte/MG"));
//        cliente.setEnderecos(Arrays.asList(endereco));
//        cliente.setEnderecos(Collections.singletonMap("casa", "Rua S�o Paulo, 5000, Brasil - Belo Horizonte/MG"));
//        cliente.setEnderecos(Collections.singletonMap("casa", endereco));
        cliente.setEnderecos(Collections.singletonMap(TipoEndereco.CASA, endereco));

        entityManager.persist(cliente);

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }

}
