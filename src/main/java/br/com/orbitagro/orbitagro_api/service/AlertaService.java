package br.com.orbitagro.orbitagro_api.service;

import br.com.orbitagro.orbitagro_api.dto.request.AlertaRequestDTO;
import br.com.orbitagro.orbitagro_api.entity.Alerta;
import br.com.orbitagro.orbitagro_api.entity.AreaCultivo;
import br.com.orbitagro.orbitagro_api.exception.ResourceNotFoundException;
import br.com.orbitagro.orbitagro_api.mapper.AlertaMapper;
import br.com.orbitagro.orbitagro_api.repository.AlertaRepository;
import br.com.orbitagro.orbitagro_api.repository.AreaCultivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaService {

    private final AlertaRepository repository;
    private final AreaCultivoRepository areaCultivoRepository;
    private final AlertaMapper mapper;

    public AlertaService(
            AlertaRepository repository,
            AreaCultivoRepository areaCultivoRepository,
            AlertaMapper mapper) {
        this.repository = repository;
        this.areaCultivoRepository = areaCultivoRepository;
        this.mapper = mapper;
    }

    public List<Alerta> listarTodos() {
        return repository.findAll();
    }

    public Alerta salvar(AlertaRequestDTO dto) {
        AreaCultivo areaCultivo = areaCultivoRepository.findById(dto.getAreaCultivoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Área de cultivo não encontrada com id: " + dto.getAreaCultivoId()));

        Alerta alerta = mapper.toEntity(dto, areaCultivo);
        return repository.save(alerta);
    }
}
