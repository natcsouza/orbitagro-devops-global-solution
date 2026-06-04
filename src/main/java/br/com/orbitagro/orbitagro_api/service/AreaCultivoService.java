package br.com.orbitagro.orbitagro_api.service;

import br.com.orbitagro.orbitagro_api.entity.AreaCultivo;
import br.com.orbitagro.orbitagro_api.repository.AreaCultivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaCultivoService {

    private final AreaCultivoRepository repository;

    public AreaCultivoService(AreaCultivoRepository repository) {
        this.repository = repository;
    }

    public List<AreaCultivo> listarTodos() {
        return repository.findAll();
    }

    public AreaCultivo salvar(AreaCultivo areaCultivo) {
        return repository.save(areaCultivo);
    }
}