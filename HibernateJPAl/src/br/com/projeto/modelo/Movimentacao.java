package br.com.projeto.modelo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(query="select avg(m.valor) from Movimentacao m where m.tipoMovimentacao= :pTipo and m.account= :pConta group by day(m.data), month(m.data), year(m.data)",name="MediasDiaTipo")
public class Movimentacao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private BigDecimal valor;
	private String descricao;//Salvando data e hora

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar data;
	@Enumerated(EnumType.STRING) //Guarda a String no banco
	private TipoMovimentacao tipoMovimentacao;
	@ManyToOne //Várias movimentações para uma conta
	private Account account;
	@ManyToMany//Se falar que é oneToMany, aquelas categorias serão somente de uma movimentação
	private List<Categoria> categoria;
	
	public List<Categoria> getCategoria() {
		return categoria;
	}
	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}
	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return "Movimentacao [descricao=" + descricao + "]\n";
	}
	
	
}
