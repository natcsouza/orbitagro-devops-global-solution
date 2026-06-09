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
public class AreaCultivoRequestDTO {

    @NotBlank(message = "Nome da área é obrigatório")
    private String nomeArea;

    @NotBlank(message = "Cultura é obrigatória")
    private String cultura;

    @NotNull(message = "Latitude é obrigatória")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatória")
    private Double longitude;

    @NotNull(message = "ID do produtor é obrigatório")
    private Long produtorId;
}
