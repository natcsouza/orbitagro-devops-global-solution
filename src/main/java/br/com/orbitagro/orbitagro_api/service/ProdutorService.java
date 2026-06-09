package br.com.orbitagro.orbitagro_api.service;

import br.com.orbitagro.orbitagro_api.dto.request.ProdutorRequestDTO;
import br.com.orbitagro.orbitagro_api.entity.Produtor;
import br.com.orbitagro.orbitagro_api.exception.ResourceNotFoundException;
import br.com.orbitagro.orbitagro_api.mapper.ProdutorMapper;
import br.com.orbitagro.orbitagro_api.repository.ProdutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutorService {

    private final ProdutorRepository repository;
    private final ProdutorMapper mapper;

    public ProdutorService(ProdutorRepository repository, ProdutorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Produtor> listarTodos() {
        return repository.findAll();
    }

    public Produtor buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produtor não encontrado com id: " + id));
    }

    public Produtor salvar(ProdutorRequestDTO dto) {
        return repository.save(mapper.toEntity(dto));
    }

    public Produtor atualizar(Long id, ProdutorRequestDTO dto) {
        Produtor produtor = buscarPorId(id);
        mapper.updateEntity(produtor, dto);
        return repository.save(produtor);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Produtor não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}
