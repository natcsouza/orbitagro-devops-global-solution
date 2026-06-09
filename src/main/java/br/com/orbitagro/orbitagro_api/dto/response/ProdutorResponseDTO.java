package br.com.orbitagro.orbitagro_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutorResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
}
