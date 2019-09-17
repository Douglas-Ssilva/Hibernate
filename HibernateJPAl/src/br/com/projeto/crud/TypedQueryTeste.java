package br.com.projeto.crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.projeto.util.JPAUtil;

public class TypedQueryTeste {

	public static void main(String[] args) {
		
		EntityManager manager= new JPAUtil().getEntityManager();
		Query query= manager.createQuery("select avg(m.valor) from Movimentacao m group by day(m.data), month(m.data), year(m.data)");
		List<Double> resultList =(List<Double>) query.getResultList();
		System.out.println("Dia 11: "+resultList.get(0));
		System.out.println("Dia 13: "+resultList.get(1));
		
		manager.close();

	}

}
