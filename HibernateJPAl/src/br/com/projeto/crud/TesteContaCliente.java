package br.com.projeto.crud;

import javax.persistence.EntityManager;

import br.com.projeto.modelo.Account;
import br.com.projeto.modelo.Cliente;
import br.com.projeto.util.JPAUtil;

public class TesteContaCliente {

	public static void main(String[] args) {
		
		EntityManager manager= new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		
		Cliente cliente= new Cliente();
		cliente.setEndereco("Viena");
		cliente.setNome("Geraldo");
		cliente.setProfissao("Professor");
		
		Account account= manager.find(Account.class, 1L);
		cliente.setAccount(account);
		manager.persist(cliente);
		manager.getTransaction().commit();
		manager.close();
	}

}
