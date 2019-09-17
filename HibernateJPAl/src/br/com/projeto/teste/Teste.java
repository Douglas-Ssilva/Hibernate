package br.com.projeto.teste;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.projeto.dao.MovimentacaoDAO;
import br.com.projeto.modelo.Account;
import br.com.projeto.modelo.TipoMovimentacao;
import br.com.projeto.util.JPAUtil;

public class Teste {

	public static void main(String[] args) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
//
		Account conta =manager.find(Account.class, 2L);
//
//		conta1.setBanco("009 - Intermedium");
//		conta1.setNumero("16987-8");
//		conta1.setAgencia("6543");
//		conta1.setTitular("Joana");
//
//	
//		manager.persist(conta1);
//		conta1.setTitular("Inez");
		
		MovimentacaoDAO dao= new MovimentacaoDAO(manager);
		List<Double> medias = dao.getMediasNamed(TipoMovimentacao.SAIDA, conta);
		for (Double double1 : medias) {
			System.out.println("Média: " + double1);
		}
	}

}
