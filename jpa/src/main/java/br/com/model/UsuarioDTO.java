package br.com.model;

public class UsuarioDTO {

	private Integer id;

	private String login;

	private String senha;

	public UsuarioDTO(Integer id, String login, String senha) {
		this.id = id;
		this.login = login;
		this.senha = senha;
	}

	public Integer getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getNome() {
		return senha;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UsuarioDTO that = (UsuarioDTO) o;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", login=" + login + ", senha=" + senha + "]";
	}


}
