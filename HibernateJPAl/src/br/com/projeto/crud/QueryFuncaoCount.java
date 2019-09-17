package br.com.projeto.crud;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import br.com.projeto.modelo.Account;
import br.com.projeto.util.JPAUtil;

public class QueryFuncaoCount {
	
	public static void main(String[] args) {
		EntityManager manager= new JPAUtil().getEntityManager();
		Account conta = manager.find(Account.class, 1L);
		Query query= manager.createQuery("select count(m) from Movimentacao m where m.account= :pAccount");
		query.setParameter("pAccount", conta);
		Long total = (Long) query.getSingleResult();
		JOptionPane.showMessageDialog(null, "Soma:" + total, "Function Count", JOptionPane.INFORMATION_MESSAGE);
		manager.close();
	}
}
