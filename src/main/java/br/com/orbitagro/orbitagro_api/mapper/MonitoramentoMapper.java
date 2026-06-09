package br.com.orbitagro.orbitagro_api.mapper;

import br.com.orbitagro.orbitagro_api.dto.request.MonitoramentoRequestDTO;
import br.com.orbitagro.orbitagro_api.dto.response.MonitoramentoResponseDTO;
import br.com.orbitagro.orbitagro_api.entity.AreaCultivo;
import br.com.orbitagro.orbitagro_api.entity.Monitoramento;
import org.springframework.stereotype.Component;

@Component
public class MonitoramentoMapper {

    public Monitoramento toEntity(MonitoramentoRequestDTO dto, AreaCultivo areaCultivo) {
        Monitoramento monitoramento = new Monitoramento();
        monitoramento.setIndiceNdvi(dto.getIndiceNdvi());
        monitoramento.setUmidadeSolo(dto.getUmidadeSolo());
        monitoramento.setTemperaturaSolo(dto.getTemperaturaSolo());
        monitoramento.setObservacao(dto.getObservacao());
        monitoramento.setAreaCultivo(areaCultivo);
        return monitoramento;
    }

    public MonitoramentoResponseDTO toResponse(Monitoramento monitoramento) {
        return MonitoramentoResponseDTO.builder()
                .id(monitoramento.getId())
                .indiceNdvi(monitoramento.getIndiceNdvi())
                .umidadeSolo(monitoramento.getUmidadeSolo())
                .temperaturaSolo(monitoramento.getTemperaturaSolo())
                .observacao(monitoramento.getObservacao())
                .areaCultivoId(monitoramento.getAreaCultivo() != null ? monitoramento.getAreaCultivo().getId() : null)
                .areaCultivoNome(monitoramento.getAreaCultivo() != null ? monitoramento.getAreaCultivo().getNomeArea() : null)
                .build();
    }
}
