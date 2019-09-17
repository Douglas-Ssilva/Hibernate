package br.com.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.projeto.modelo.Account;
import br.com.projeto.modelo.TipoMovimentacao;

public class MovimentacaoDAO {
	
	private EntityManager manager;

	public MovimentacaoDAO(EntityManager manager) {
		this.manager = manager;
	}
	
	public List<Double> getMedias(TipoMovimentacao saida, Account conta){
		List<Double> list;
		TypedQuery<Double> query= manager.createQuery("select avg(m.valor) from Movimentacao m where m.tipoMovimentacao= :pTipo "
				+ "and m.account= :pAccount group by day(m.data), month(m.data), year(m.data)", Double.class);
		query.setParameter("pTipo", saida);
		query.setParameter("pAccount", conta);
		
		list = query.getResultList();
		manager.close();
		return list;
	}
	
	public List<Double> getMediasNamed(TipoMovimentacao saida, Account account){
		TypedQuery<Double> typedQuery = this.manager.createNamedQuery("MediasDiaTipo", Double.class);
		typedQuery.setParameter("pTipo", saida);
		typedQuery.setParameter("pConta", account);
		return typedQuery.getResultList();
	}

}
