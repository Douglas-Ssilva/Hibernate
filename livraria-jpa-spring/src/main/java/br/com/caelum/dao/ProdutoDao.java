package br.com.caelum.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.caelum.model.Categoria;
import br.com.caelum.model.Loja;
import br.com.caelum.model.Produto;

@Repository
public class ProdutoDao {

	@PersistenceContext
	private EntityManager em;

	public List<Produto> getProdutos() {
//		return em.createQuery("from Produto", Produto.class)
//				.setHint("javax.persistence.loadgraph", em.createEntityGraph("produtoComCategoria"))
//				.getResultList();
//		return em.createQuery("FROM Produto p JOIN FETCH p.loja", Produto.class).getResultList();
		return em.createQuery("FROM Produto", Produto.class)
				.setHint("org.hibernate.cacheable", "true")
				.getResultList();
	}	
		
	public Produto getProduto(Integer id) {
		Produto produto = em.find(Produto.class, id);
		return produto;
	}

	public List<Produto> getProdutos(String nome, Integer categoriaId, Integer lojaId) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = query.from(Produto.class);//traçamos o caminho p chegar em cada atributo

		Path<String> nomePath = root.get("nome");
		Path<Integer> lojaPath = root.get("loja").get("id");
		Path<Integer> categoriaPath = root.join("categorias").get("id");

		List<Predicate> predicates = new ArrayList<>();

		if (!nome.isEmpty()) {
			Predicate nomeIgual = criteriaBuilder.like(nomePath, "%" + nome + "%");
			predicates.add(nomeIgual);
		}
		if (categoriaId != null) {
			Predicate categoriaIgual = criteriaBuilder.equal(categoriaPath, categoriaId);
			predicates.add(categoriaIgual);
		}
		if (lojaId != null) {
			Predicate lojaIgual = criteriaBuilder.equal(lojaPath, lojaId);
			predicates.add(lojaIgual);
		}

		query.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Produto> typedQuery = em.createQuery(query);
		typedQuery.setHint("org.hibernate.cacheable", "true");//fazendo cache de queries
		return typedQuery.getResultList();

	}
	
	public List<Produto> getProdutosOhter(String nome, Integer categoriaId, Integer lojaId) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> produtoRoot = query.from(Produto.class);

		Predicate conjuncao = builder.conjunction();

		if (!nome.isEmpty()) {
		    Path<String> nomeProduto = produtoRoot.<String> get("nome");
		    Predicate nomeIgual = builder.like(nomeProduto, "%" + nome + "%");
		    conjuncao = builder.and(nomeIgual);
		}

		if (categoriaId != null) {
		    Join<Produto, List<Categoria>> join = produtoRoot.join("categorias");
		    Path<Integer> categoriaProduto = join.get("id");
		    conjuncao = builder.and(conjuncao, builder.equal(categoriaProduto, categoriaId));
		}

		if (lojaId != null) {
		    Path<Loja> loja = produtoRoot.<Loja> get("loja");
		    Path<Integer> id = loja.<Integer> get("id");
		    conjuncao = builder.and(conjuncao, builder.equal(id, lojaId));
		}

		TypedQuery<Produto> typedQuery = em.createQuery(query.where(conjuncao));
		return typedQuery.getResultList();

	}
	
	@Transactional
	public List<Produto> getProdutosCriteriaHibernate(String nome, Integer categoriaId, Integer lojaId) {
	    Session session = em.unwrap(Session.class);
	    Criteria criteria = session.createCriteria(Produto.class);

	    if (!nome.isEmpty()) {
	        criteria.add(Restrictions.like("nome", "%" + nome + "%"));
	    }

	    if (lojaId != null) {
	        criteria.add(Restrictions.like("loja.id", lojaId));
	    }

	    if (categoriaId != null) {
	        criteria.setFetchMode("categorias", FetchMode.JOIN)
	            .createAlias("categorias", "c")
	            .add(Restrictions.like("c.id", categoriaId));
	    }

	    return criteria.list();
	}

	public void insere(Produto produto) {
		if (produto.getId() == null)
			em.persist(produto);
		else
			em.merge(produto);
	}

}
