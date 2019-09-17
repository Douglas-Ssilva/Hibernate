package br.com.projeto.crud;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.projeto.modelo.TipoMovimentacao;
import br.com.projeto.util.JPAUtil;

public class MaisQuerys {

	public static void main(String[] args) {
		
		EntityManager manager= new JPAUtil().getEntityManager();
		Query query= manager.createQuery("select max(m.valor) from Movimentacao m where m.tipoMovimentacao= :pTipo");
		query.setParameter("pTipo", TipoMovimentacao.SAIDA);
		BigDecimal soma =(BigDecimal) query.getSingleResult();		
		System.out.println("Soma: " + soma);
		manager.close();

	}

}
