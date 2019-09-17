package br.com.projeto.crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.projeto.modelo.Categoria;
import br.com.projeto.modelo.Movimentacao;
import br.com.projeto.modelo.TipoMovimentacao;
import br.com.projeto.util.JPAUtil;

public class QueryManyToMany {

	public static void main(String[] args) {
		
		EntityManager manager= new JPAUtil().getEntityManager();
		Categoria categoria= new Categoria();
		categoria.setId(1L);
		Query query= manager.createQuery("select m from Movimentacao m join m.categoria c where c= :pCategoria and m.tipoMovimentacao= :pTipo and m.valor > 500");
		query.setParameter("pCategoria", categoria);
		query.setParameter("pTipo", TipoMovimentacao.SAIDA);
		
		List<Movimentacao> list= query.getResultList();
		for (Movimentacao m : list) {
			System.out.println(m.getDescricao()+" teve categoria: "+ m.getCategoria());
		}
		manager.close();
	}

}
