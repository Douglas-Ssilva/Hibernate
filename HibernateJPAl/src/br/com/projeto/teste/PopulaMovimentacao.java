package br.com.projeto.teste;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.projeto.modelo.Account;
import br.com.projeto.modelo.Movimentacao;
import br.com.projeto.modelo.TipoMovimentacao;
import br.com.projeto.util.JPAUtil;

public class PopulaMovimentacao {
	
	public static void main(String[] args) {
	
	EntityManager manager = new JPAUtil().getEntityManager();

	manager.getTransaction().begin();

	Account conta1 = manager.find(Account.class,1L);
	Account conta2 = manager.find(Account.class,2L);


	// Movimentacoes da conta1
	Movimentacao movimentacao1 = new Movimentacao();
	Movimentacao movimentacao2 = new Movimentacao();
	Movimentacao movimentacao3 = new Movimentacao();
	Movimentacao movimentacao4 = new Movimentacao();

	movimentacao1.setData(Calendar.getInstance());
	movimentacao1.setDescricao("Conta de luz - ABRIL/2012");
	movimentacao1.setValor(new BigDecimal("135"));
	movimentacao1.setTipoMovimentacao(TipoMovimentacao.SAIDA);
	movimentacao1.setAccount(conta1);

	manager.persist(movimentacao1);

	movimentacao2.setData(Calendar.getInstance());
	movimentacao2.setDescricao("Almoco no Restaurante - AGOSTO/2012");
	movimentacao2.setValor(new BigDecimal("175.80"));
	movimentacao2.setTipoMovimentacao(TipoMovimentacao.SAIDA);
	movimentacao2.setAccount(conta1);

	manager.persist(movimentacao2);

	movimentacao3.setData(Calendar.getInstance());
	movimentacao3.setDescricao("Aluguel - MAIO/2012");
	movimentacao3.setValor(new BigDecimal("680.00"));
	movimentacao3.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
	movimentacao3.setAccount(conta1);

	manager.persist(movimentacao3);

	movimentacao4.setData(Calendar.getInstance());
	movimentacao4.setDescricao("Salario - FEVEREIRO/2012");
	movimentacao4.setValor(new BigDecimal("3830.68"));
	movimentacao4.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
	movimentacao4.setAccount(conta1);

	manager.persist(movimentacao4);

	// Movimentacoes da conta2
	Movimentacao movimentacao5 = new Movimentacao();
	Movimentacao movimentacao6 = new Movimentacao();

	movimentacao5.setData(Calendar.getInstance());
	movimentacao5.setDescricao("Conta de telefone - SETEMBRO/2011");
	movimentacao5.setValor(new BigDecimal("168.27"));
	movimentacao5.setTipoMovimentacao(TipoMovimentacao.SAIDA);
	movimentacao5.setAccount(conta2);

	manager.persist(movimentacao5);

	movimentacao6.setData(Calendar.getInstance());
	movimentacao6.setDescricao("Aniversario - MAIO/2011");
	movimentacao6.setValor(new BigDecimal("200"));
	movimentacao6.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
	movimentacao6.setAccount(conta2);

	manager.persist(movimentacao6);

	manager.getTransaction().commit();

	manager.close();

}

}
