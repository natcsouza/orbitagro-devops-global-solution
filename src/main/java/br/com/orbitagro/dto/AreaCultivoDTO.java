package br.com.orbitagro.dto;

import br.com.orbitagro.entity.Alerta;
import jakarta.validation.constraints.*;

public class AreaCultivoDTO {

    public record Request(
        @NotBlank(message = "Nome da área é obrigatório")
        String nomeArea,

        @NotBlank(message = "Cultura é obrigatória")
        String cultura,

        Double latitude,
        Double longitude,
        Double hectares,

        @NotNull(message = "Produtor é obrigatório")
        Long produtorId
    ) {}

    public record Response(
        Long id,
        String nomeArea,
        String cultura,
        Double latitude,
        Double longitude,
        Double hectares,
        Long produtorId,
        String nomeProdutoR
    ) {}
}
