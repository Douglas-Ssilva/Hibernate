package br.com.mapeamentos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "curso")
@Entity
public class Curso {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @OneToMany(mappedBy = "curso") //Caminho de volta, decisão arbitraria. forma de o JPA saber onde está a lista de modulos BIDIRECIONAL OU UNIDIRECIONAL
    private List<Modulo> modulos;

    @ManyToMany//Colocar joinColumn aqui, falo que um curso so poderá ter um aluno
//    @JoinTable(name = "curso_aluno", joinColumns = @JoinColumn(name = "curso_id"), inverseJoinColumns = @JoinColumn(name = "aluno_id"))
    @JoinTable(name = "matricula", joinColumns = @JoinColumn(name = "curso_id"), inverseJoinColumns = @JoinColumn(name = "aluno_id"))//na entidade matricula tenho o curso e o aluno
    private List<Aluno> alunos;

    @OneToMany(mappedBy = "curso")
    private List<Matricula> matriculas;

}
