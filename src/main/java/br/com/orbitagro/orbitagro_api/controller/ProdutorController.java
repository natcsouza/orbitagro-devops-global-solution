package br.com.orbitagro.orbitagro_api.controller;

import br.com.orbitagro.orbitagro_api.dto.request.ProdutorRequestDTO;
import br.com.orbitagro.orbitagro_api.dto.response.ProdutorResponseDTO;
import br.com.orbitagro.orbitagro_api.entity.Produtor;
import br.com.orbitagro.orbitagro_api.mapper.ProdutorMapper;
import br.com.orbitagro.orbitagro_api.service.ProdutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtores")
public class ProdutorController {

    private final ProdutorService service;
    private final ProdutorMapper mapper;

    public ProdutorController(ProdutorService service, ProdutorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ProdutorResponseDTO> listarTodos() {
        return service.listarTodos().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ProdutorResponseDTO buscarPorId(@PathVariable Long id) {
        Produtor produtor = service.buscarPorId(id);
        return mapper.toResponse(produtor);
    }

    @PostMapping
    public ResponseEntity<ProdutorResponseDTO> cadastrar(@RequestBody @Valid ProdutorRequestDTO dto) {
        Produtor novoProdutor = service.salvar(dto);
        return ResponseEntity.status(201).body(mapper.toResponse(novoProdutor));
    }

    @PutMapping("/{id}")
    public ProdutorResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid ProdutorRequestDTO dto) {
        Produtor produtorAtualizado = service.atualizar(id, dto);
        return mapper.toResponse(produtorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
