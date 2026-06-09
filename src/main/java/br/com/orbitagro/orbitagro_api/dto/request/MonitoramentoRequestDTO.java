package br.com.orbitagro.orbitagro_api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonitoramentoRequestDTO {

    @NotNull(message = "Índice NDVI é obrigatório")
    private Double indiceNdvi;

    @NotNull(message = "Umidade do solo é obrigatória")
    private Double umidadeSolo;

    @NotNull(message = "Temperatura do solo é obrigatória")
    private Double temperaturaSolo;

    private String observacao;

    @NotNull(message = "ID da área de cultivo é obrigatório")
    private Long areaCultivoId;
}
