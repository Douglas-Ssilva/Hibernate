package br.com.mapeamentos;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "modulo")
@Entity
public class Modulo {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
//    Muito cuidado ao usar cascade em manyToMany visto que ele remove todos os dados da entidade relacionada.
//    CascadeType.MERGE -> get modulo, update attribute curso o atualize
//    CascadeType.REMOVE 
//    CascadeType.ALL -> engloba persist, merge
//    Faz sentido usar quando a relação for muito forte. Ex: faz sentido ter aulas sem um modulo? 
    
    @ManyToOne(cascade = CascadeType.PERSIST)//Posso persistir primeiro o curso, ou fazer dessa forma
    @JoinColumn(name = "curso_id", nullable = false) //foreing key fica nessa entidade. A entidade que possui joinColumn é o main da relação. Quem possui o mappedby nao é dono da relação
    private Curso curso;

//    orphanRemoval = true remove as aulas do modulo que exclui, equivalente ao cascade remove, precisa do persist
    @OneToMany(mappedBy = "modulo", orphanRemoval = true, cascade = CascadeType.PERSIST) //Caminho de volta, decisão arbitraria. Nome do atributo na entidade Aula, assim sei o caminho de volta pra saber as aulas p esse modulo
    private List<Aula> aulas;
}
