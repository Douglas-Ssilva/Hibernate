package br.com.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

	@Getter
	@Setter
	@EqualsAndHashCode(onlyExplicitlyIncluded = true)
	@AllArgsConstructor //Cria construtor conforme a ordem dos atributos
	@NoArgsConstructor //Cria o construtor default
	public class UserDTO {

	    @EqualsAndHashCode.Include
	    private Integer id;

	    private String login;

	    private String nome;
	}

