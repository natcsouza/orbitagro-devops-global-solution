package br.com.orbitagro.orbitagro_api.service;

import br.com.orbitagro.orbitagro_api.dto.request.AreaCultivoRequestDTO;
import br.com.orbitagro.orbitagro_api.entity.AreaCultivo;
import br.com.orbitagro.orbitagro_api.entity.Produtor;
import br.com.orbitagro.orbitagro_api.exception.ResourceNotFoundException;
import br.com.orbitagro.orbitagro_api.mapper.AreaCultivoMapper;
import br.com.orbitagro.orbitagro_api.repository.AreaCultivoRepository;
import br.com.orbitagro.orbitagro_api.repository.ProdutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaCultivoService {

    private final AreaCultivoRepository repository;
    private final ProdutorRepository produtorRepository;
    private final AreaCultivoMapper mapper;

    public AreaCultivoService(
            AreaCultivoRepository repository,
            ProdutorRepository produtorRepository,
            AreaCultivoMapper mapper) {
        this.repository = repository;
        this.produtorRepository = produtorRepository;
        this.mapper = mapper;
    }

    public List<AreaCultivo> listarTodos() {
        return repository.findAll();
    }

    public AreaCultivo salvar(AreaCultivoRequestDTO dto) {
        Produtor produtor = produtorRepository.findById(dto.getProdutorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Produtor não encontrado com id: " + dto.getProdutorId()));

        AreaCultivo areaCultivo = mapper.toEntity(dto, produtor);
        return repository.save(areaCultivo);
    }
}
