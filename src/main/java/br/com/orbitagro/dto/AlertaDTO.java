package br.com.orbitagro.dto;

import br.com.orbitagro.entity.Alerta;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class AlertaDTO {

    public record Request(
        @NotNull(message = "Tipo de alerta é obrigatório")
        Alerta.TipoAlerta tipoAlerta,

        String observacao,

        @NotNull(message = "Área de cultivo é obrigatória")
        Long areaCultivoId
    ) {}

    public record Response(
        Long id,
        Alerta.TipoAlerta tipoAlerta,
        String observacao,
        LocalDateTime dataAlerta,
        Alerta.StatusAlerta statusAlerta,
        Long areaCultivoId,
        String nomeArea
    ) {}
}
