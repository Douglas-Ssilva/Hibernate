package br.com.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.projeto.modelo.Account;
import br.com.projeto.modelo.Category;
import br.com.projeto.modelo.Client;
import br.com.projeto.modelo.Movement;

public class DAO<T> {
	
	private EntityManager manager;
	private Class<T> classe;

	public DAO(EntityManager manager,Class<T> classe) {
		this.manager = manager;
		this.classe = classe;
	}
	
	public void insert(T t) {
		this.manager.getTransaction().begin();
		this.manager.persist(t);
		this.manager.getTransaction().commit();
		this.manager.close();
	}
	
	public T find(Long id) {
		return this.manager.find(classe, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll(){
		return this.manager.createQuery("select t from "+ classe.getName() +" t").getResultList();
	}
	
	public void delete(T t) {
		this.manager.getTransaction().begin();
		this.manager.remove(t);
		this.manager.getTransaction().commit();
		this.manager.close();
	}
	
	public void update(T t) {
		this.manager.getTransaction().begin();
		this.manager.merge(t);
		this.manager.getTransaction().commit();
		this.manager.close();
	}
	
	public List<Movement> getMovementAccount(Account account){
		TypedQuery<Movement> query= this.manager.createQuery("select m from Movement m where m.account= :pAccount", Movement.class);
		query.setParameter("pAccount", account);
		return query.getResultList();
	}
	
	public Long getMovementAccountInt(Account account) {
		TypedQuery<Long> query= this.manager.createQuery("select count(m) from Movement m where m.account= :pAccount", Long.class);
		query.setParameter("pAccount", account);
		return query.getSingleResult();
	}
	
	public Double getMedia(Account account) {
		TypedQuery<Double> query= this.manager.createQuery("select avg(m.value) from Movement m where m.account= :pAccount", Double.class);
		query.setParameter("pAccount", account);
		return query.getSingleResult();
	}
	
	public List<Movement> getMovementCategory(Category category){
		TypedQuery<Movement> query= this.manager.createQuery("select m from Movement m join fetch m.category c where c= :pCategory", Movement.class);
		query.setParameter("pCategory", category);
		return query.getResultList();
	}
	
	public Account getAccountClient(Client client) {
		TypedQuery<Account> query= this.manager.createQuery("select a from Account a where a.client= :pClient", Account.class);
		query.setParameter("pClient", client);
		return query.getSingleResult();
	}
	
	public List<Double> getMediaForDay(Account account) {
		TypedQuery<Double> query= this.manager.createQuery("select avg(m.value) from Movement m where m.account= :pAccount group by day(m.date), month(m.date), year(m.date)",Double.class);
		query.setParameter("pAccount", account);
		return query.getResultList();
	}
	
	public List<Client> getListClient(){
		TypedQuery<Client> query= this.manager.createQuery("select c from Client c join fetch c.account a join fetch a.listMovement l where l.value= 1000.00", Client.class);
		return query.getResultList();
	}

}
