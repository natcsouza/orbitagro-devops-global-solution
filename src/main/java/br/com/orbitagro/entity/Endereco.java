package br.com.orbitagro.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endereco {
    private String rua;
    private String cidade;
    private String estado;
    private String cep;
}
