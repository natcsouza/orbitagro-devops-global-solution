package br.com.orbitagro.service;

import br.com.orbitagro.dto.AlertaDTO;
import br.com.orbitagro.entity.*;
import br.com.orbitagro.exception.ResourceNotFoundException;
import br.com.orbitagro.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository repository;
    private final AreaCultivoRepository areaCultivoRepository;

    public List<AlertaDTO.Response> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public AlertaDTO.Response findById(Long id) {
        return toResponse(findOrThrow(id));
    }

    public List<AlertaDTO.Response> findByArea(Long areaId) {
        return repository.findByAreaCultivoId(areaId).stream().map(this::toResponse).toList();
    }

    public AlertaDTO.Response create(AlertaDTO.Request dto) {
        AreaCultivo area = areaCultivoRepository.findById(dto.areaCultivoId())
                .orElseThrow(() -> new ResourceNotFoundException("Área não encontrada: " + dto.areaCultivoId()));

        Alerta alerta = Alerta.builder()
                .tipoAlerta(dto.tipoAlerta())
                .observacao(dto.observacao())
                .areaCultivo(area)
                .build();
        return toResponse(repository.save(alerta));
    }

    public AlertaDTO.Response update(Long id, AlertaDTO.Request dto) {
        Alerta alerta = findOrThrow(id);
        alerta.setTipoAlerta(dto.tipoAlerta());
        alerta.setObservacao(dto.observacao());
        return toResponse(repository.save(alerta));
    }

    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private Alerta findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado: " + id));
    }

    private AlertaDTO.Response toResponse(Alerta a) {
        return new AlertaDTO.Response(
                a.getId(), a.getTipoAlerta(), a.getObservacao(),
                a.getDataAlerta(), a.getStatusAlerta(),
                a.getAreaCultivo().getId(), a.getAreaCultivo().getNomeArea()
        );
    }
}
