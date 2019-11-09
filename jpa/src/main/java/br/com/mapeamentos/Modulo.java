package br.com.mapeamentos;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false) //foreing key fica nessa entidade. A entidade que possui joinColumn é o main da relação. Quem possui o mappedby nao é dono da relação
    private Curso curso;

    @OneToMany(mappedBy = "modulo") //Caminho de volta, decisão arbitraria. Nome do atributo na entidade Aula, assim sei o caminho de volta pra saber as aulas p esse modulo
    private List<Aula> aulas;
}
