package br.com.elementcollection;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "cliente")
@Entity
public class Cliente {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

//    @ElementCollection		Cria uma table que armazena o id do cliente e a string do endereço
//    @CollectionTable(name = "cliente_endereco", joinColumns = @JoinColumn(name = "cliente_id"))
//    @Column(name = "endereco")
//    private List<String> enderecos;

//    @ElementCollection		|| e todos atributos de Endereco
//    @CollectionTable(name = "cliente_endereco", joinColumns = @JoinColumn(name = "cliente_id"))
//    private List<Endereco> enderecos;

//    @ElementCollection
//    @CollectionTable(name = "cliente_endereco", joinColumns = @JoinColumn(name = "cliente_id"))
//    @Column(name = "endereco")
//    @MapKeyColumn(name = "tipo")//key do map, cria-se uma column no BD
//    private Map<String, String> enderecos;

//    @ElementCollection
//    @CollectionTable(name = "cliente_endereco", joinColumns = @JoinColumn(name = "cliente_id"))
//    @MapKeyColumn(name = "tipo")
//    private Map<String, Endereco> enderecos;

    @ElementCollection
    @CollectionTable(name = "cliente_endereco", joinColumns = @JoinColumn(name = "cliente_id"))
    @MapKeyColumn(name = "tipo")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<TipoEndereco, Endereco> enderecos;

}
