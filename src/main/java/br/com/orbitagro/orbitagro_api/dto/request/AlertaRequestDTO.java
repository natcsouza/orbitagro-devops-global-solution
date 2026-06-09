package br.com.orbitagro.orbitagro_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertaRequestDTO {

    @NotBlank(message = "Tipo de alerta é obrigatório")
    private String tipoAlerta;

    @NotBlank(message = "Mensagem é obrigatória")
    private String mensagem;

    private String status;

    @NotNull(message = "ID da área de cultivo é obrigatório")
    private Long areaCultivoId;
}
