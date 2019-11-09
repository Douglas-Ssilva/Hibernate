package br.com.mapeamentos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "matricula")
@Entity
public class Matricula {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_matricula")
    private LocalDateTime dataMatricula;

    @ManyToOne
    @JoinColumn(name = "curso_id") //Cada matricula referencia um curso
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @OneToOne(mappedBy = "matricula")//BUSQUE A FK NA ENTIDADE PAGAMENTO NO ATRIBUTO MATRICULA
    private Pagamento pagamento;


}
