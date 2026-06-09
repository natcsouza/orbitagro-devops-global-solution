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
public class MonitoramentoResponseDTO {

    private Long id;
    private Double indiceNdvi;
    private Double umidadeSolo;
    private Double temperaturaSolo;
    private String observacao;
    private Long areaCultivoId;
    private String areaCultivoNome;
}
