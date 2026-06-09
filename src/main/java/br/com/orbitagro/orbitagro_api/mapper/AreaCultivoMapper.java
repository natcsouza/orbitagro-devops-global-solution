package br.com.orbitagro.orbitagro_api.mapper;

import br.com.orbitagro.orbitagro_api.dto.request.AreaCultivoRequestDTO;
import br.com.orbitagro.orbitagro_api.dto.response.AreaCultivoResponseDTO;
import br.com.orbitagro.orbitagro_api.entity.AreaCultivo;
import br.com.orbitagro.orbitagro_api.entity.Produtor;
import org.springframework.stereotype.Component;

@Component
public class AreaCultivoMapper {

    public AreaCultivo toEntity(AreaCultivoRequestDTO dto, Produtor produtor) {
        AreaCultivo area = new AreaCultivo();
        area.setNomeArea(dto.getNomeArea());
        area.setCultura(dto.getCultura());
        area.setLatitude(dto.getLatitude());
        area.setLongitude(dto.getLongitude());
        area.setProdutor(produtor);
        return area;
    }

    public AreaCultivoResponseDTO toResponse(AreaCultivo area) {
        return AreaCultivoResponseDTO.builder()
                .id(area.getId())
                .nomeArea(area.getNomeArea())
                .cultura(area.getCultura())
                .latitude(area.getLatitude())
                .longitude(area.getLongitude())
                .produtorId(area.getProdutor() != null ? area.getProdutor().getId() : null)
                .produtorNome(area.getProdutor() != null ? area.getProdutor().getNome() : null)
                .build();
    }
}
