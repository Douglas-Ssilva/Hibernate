package br.com.mapeamentos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "pagamento")
@Entity
public class Pagamento {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal valor;

    @OneToOne
    @JoinColumn(name = "matricula_id") //NESSE TIPO DE MAPEAMENTO, POSSO COLOCAR A FK EM QUALQUER LUGAR
    private Matricula matricula;

}
