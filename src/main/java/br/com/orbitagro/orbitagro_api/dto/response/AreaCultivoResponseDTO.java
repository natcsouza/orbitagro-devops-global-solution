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
public class AreaCultivoResponseDTO {

    private Long id;
    private String nomeArea;
    private String cultura;
    private Double latitude;
    private Double longitude;
    private Long produtorId;
    private String produtorNome;
}
