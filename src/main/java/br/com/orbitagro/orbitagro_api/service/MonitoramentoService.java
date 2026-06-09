package br.com.orbitagro.orbitagro_api.service;

import br.com.orbitagro.orbitagro_api.dto.request.MonitoramentoRequestDTO;
import br.com.orbitagro.orbitagro_api.entity.AreaCultivo;
import br.com.orbitagro.orbitagro_api.entity.Monitoramento;
import br.com.orbitagro.orbitagro_api.exception.ResourceNotFoundException;
import br.com.orbitagro.orbitagro_api.mapper.MonitoramentoMapper;
import br.com.orbitagro.orbitagro_api.repository.AreaCultivoRepository;
import br.com.orbitagro.orbitagro_api.repository.MonitoramentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoramentoService {

    private final MonitoramentoRepository repository;
    private final AreaCultivoRepository areaCultivoRepository;
    private final MonitoramentoMapper mapper;

    public MonitoramentoService(
            MonitoramentoRepository repository,
            AreaCultivoRepository areaCultivoRepository,
            MonitoramentoMapper mapper) {
        this.repository = repository;
        this.areaCultivoRepository = areaCultivoRepository;
        this.mapper = mapper;
    }

    public List<Monitoramento> listarTodos() {
        return repository.findAll();
    }

    public Monitoramento salvar(MonitoramentoRequestDTO dto) {
        AreaCultivo areaCultivo = areaCultivoRepository.findById(dto.getAreaCultivoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Área de cultivo não encontrada com id: " + dto.getAreaCultivoId()));

        Monitoramento monitoramento = mapper.toEntity(dto, areaCultivo);
        return repository.save(monitoramento);
    }
}
