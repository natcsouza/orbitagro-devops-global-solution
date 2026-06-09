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
public class AlertaResponseDTO {

    private Long id;
    private String tipoAlerta;
    private String mensagem;
    private String status;
    private Long areaCultivoId;
    private String areaCultivoNome;
}
