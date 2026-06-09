package br.com.orbitagro.service;

import br.com.orbitagro.dto.ProdutorDTO;
import br.com.orbitagro.entity.*;
import br.com.orbitagro.exception.ResourceNotFoundException;
import br.com.orbitagro.repository.ProdutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutorService {

    private final ProdutorRepository repository;

    public List<ProdutorDTO.Response> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ProdutorDTO.Response findById(Long id) {
        return toResponse(findOrThrow(id));
    }

    public ProdutorDTO.Response create(ProdutorDTO.Request dto) {
        Produtor produtor = Produtor.builder()
                .nome(dto.nome())
                .email(dto.email())
                .cpf(dto.cpf())
                .telefone(dto.telefone())
                .endereco(Endereco.builder()
                        .rua(dto.rua())
                        .cidade(dto.cidade())
                        .estado(dto.estado())
                        .cep(dto.cep())
                        .build())
                .build();
        return toResponse(repository.save(produtor));
    }

    public ProdutorDTO.Response update(Long id, ProdutorDTO.Request dto) {
        Produtor produtor = findOrThrow(id);
        produtor.setNome(dto.nome());
        produtor.setEmail(dto.email());
        produtor.setTelefone(dto.telefone());
        return toResponse(repository.save(produtor));
    }

    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private Produtor findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produtor não encontrado: " + id));
    }

    private ProdutorDTO.Response toResponse(Produtor p) {
        return new ProdutorDTO.Response(
                p.getId(), p.getNome(), p.getEmail(), p.getCpf(), p.getTelefone(),
                p.getEndereco() != null ? p.getEndereco().getCidade() : null,
                p.getEndereco() != null ? p.getEndereco().getEstado() : null
        );
    }
}
