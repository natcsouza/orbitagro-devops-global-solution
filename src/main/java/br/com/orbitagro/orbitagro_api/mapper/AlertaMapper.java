package br.com.orbitagro.orbitagro_api.mapper;

import br.com.orbitagro.orbitagro_api.dto.request.AlertaRequestDTO;
import br.com.orbitagro.orbitagro_api.dto.response.AlertaResponseDTO;
import br.com.orbitagro.orbitagro_api.entity.Alerta;
import br.com.orbitagro.orbitagro_api.entity.AreaCultivo;
import org.springframework.stereotype.Component;

@Component
public class AlertaMapper {

    public Alerta toEntity(AlertaRequestDTO dto, AreaCultivo areaCultivo) {
        Alerta alerta = new Alerta();
        alerta.setTipoAlerta(dto.getTipoAlerta());
        alerta.setMensagem(dto.getMensagem());
        alerta.setStatus(dto.getStatus());
        alerta.setAreaCultivo(areaCultivo);
        return alerta;
    }

    public AlertaResponseDTO toResponse(Alerta alerta) {
        return AlertaResponseDTO.builder()
                .id(alerta.getId())
                .tipoAlerta(alerta.getTipoAlerta())
                .mensagem(alerta.getMensagem())
                .status(alerta.getStatus())
                .areaCultivoId(alerta.getAreaCultivo() != null ? alerta.getAreaCultivo().getId() : null)
                .areaCultivoNome(alerta.getAreaCultivo() != null ? alerta.getAreaCultivo().getNomeArea() : null)
                .build();
    }
}
