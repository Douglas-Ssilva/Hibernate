package br.com.model;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

//Instalação do lombok depois de colocar a dependencia, ir na pasta que baixou o jar dar duplo click e apontar o diretorio da IDE
	@Getter
	@Setter
	@EqualsAndHashCode(onlyExplicitlyIncluded = true) //gerar equals and hascode, inclua somente o que eu colocar essa annotation: @EqualsAndHashCode.Include
	@SqlResultSetMappings({
	@SqlResultSetMapping(name = "mapeamento.Usuario", entities = @EntityResult(entityClass = User.class, fields = { //Como a consulta é feita em outra table, tenho que fazer esse mapeamento tipo alias.
	                                @FieldResult(name = "id", column = "usu_codigo"),
	                                @FieldResult(name = "login", column = "usu_email"),
	                                @FieldResult(name = "senha", column = "usu_senha"),
	                                @FieldResult(name = "nome", column = "usu_nome")
	                        })),
   @SqlResultSetMapping(name = "mapeamento.UsuarioDTO", classes = {
	                		    	@ConstructorResult(targetClass = UsuarioDTO.class, columns = { //coloco nomes das colunas que constam no BD
	                				@ColumnResult(name = "usu_codigo", type = Integer.class),
	                				@ColumnResult(name = "usu_email", type = String.class),
	                				@ColumnResult(name = "usu_nome", type = String.class),
	                })	
	        })
	})
	@NamedNativeQueries({
	        @NamedNativeQuery(name = "namedQuery.Usuario",
	                query = "select * from sis_usuario", resultSetMapping = "mapeamento.Usuario" //referenciando esse mapeamento que contem os atributos do meu retorno
//	                query = "select * from usuario", resultClass = Usuario.class
	        )})
	
	@Table(name = "usuario")
	@Entity
	public class User {

	    @EqualsAndHashCode.Include
	    @Id
	    private Integer id;

	    private String login;

	    private String senha;

	    private String nome;

}
