package br.com.orbitagro.service;

import br.com.orbitagro.dto.MonitoramentoDTO;
import br.com.orbitagro.entity.*;
import br.com.orbitagro.exception.ResourceNotFoundException;
import br.com.orbitagro.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoramentoService {

    private final MonitoramentoRepository repository;
    private final AreaCultivoRepository areaCultivoRepository;

    public List<MonitoramentoDTO.Response> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public MonitoramentoDTO.Response findById(Long id) {
        return toResponse(findOrThrow(id));
    }

    public List<MonitoramentoDTO.Response> findByArea(Long areaId) {
        return repository.findByAreaCultivoId(areaId).stream().map(this::toResponse).toList();
    }

    public MonitoramentoDTO.Response create(MonitoramentoDTO.Request dto) {
        AreaCultivo area = areaCultivoRepository.findById(dto.areaCultivoId())
                .orElseThrow(() -> new ResourceNotFoundException("Área não encontrada: " + dto.areaCultivoId()));

        Monitoramento m = Monitoramento.builder()
                .indiceNdvi(dto.indiceNdvi())
                .ndviAnterior(dto.ndviAnterior())
                .umidadeSolo(dto.umidadeSolo())
                .temperaturaSolo(dto.temperaturaSolo())
                .areaCultivo(area)
                .build();
        return toResponse(repository.save(m));
    }

    public MonitoramentoDTO.Response update(Long id, MonitoramentoDTO.Request dto) {
        Monitoramento m = findOrThrow(id);
        m.setIndiceNdvi(dto.indiceNdvi());
        m.setNdviAnterior(dto.ndviAnterior());
        m.setUmidadeSolo(dto.umidadeSolo());
        m.setTemperaturaSolo(dto.temperaturaSolo());
        return toResponse(repository.save(m));
    }

    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private Monitoramento findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monitoramento não encontrado: " + id));
    }

    private MonitoramentoDTO.Response toResponse(Monitoramento m) {
        return new MonitoramentoDTO.Response(
                m.getId(), m.getIndiceNdvi(), m.getNdviAnterior(),
                m.getUmidadeSolo(), m.getTemperaturaSolo(), m.getDataLeitura(),
                m.getAreaCultivo().getId(), m.getAreaCultivo().getNomeArea()
        );
    }
}
