package br.com.orbitagro.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class MonitoramentoDTO {

    public record Request(
        @NotNull(message = "Índice NDVI é obrigatório")
        @DecimalMin(value = "0.0") @DecimalMax(value = "1.0")
        Double indiceNdvi,

        Double ndviAnterior,

        @DecimalMin(value = "0.0") @DecimalMax(value = "100.0")
        Double umidadeSolo,

        Double temperaturaSolo,

        @NotNull(message = "Área de cultivo é obrigatória")
        Long areaCultivoId
    ) {}

    public record Response(
        Long id,
        Double indiceNdvi,
        Double ndviAnterior,
        Double umidadeSolo,
        Double temperaturaSolo,
        LocalDateTime dataLeitura,
        Long areaCultivoId,
        String nomeArea
    ) {}
}
