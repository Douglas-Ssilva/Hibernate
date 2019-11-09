package br.com.elementcollection;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable //será incorporada à entidade cliente
public class Endereco {

    private String logradouro;

    private String numero;

    private String bairro;

    private String cidade;

    private String estado;

    private String cep;

}
