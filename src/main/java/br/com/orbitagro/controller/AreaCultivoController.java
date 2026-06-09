package br.com.orbitagro.controller;

import br.com.orbitagro.dto.AreaCultivoDTO;
import br.com.orbitagro.service.AreaCultivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
@Tag(name = "Áreas de Cultivo", description = "Gerenciamento de áreas monitoradas")
@SecurityRequirement(name = "Bearer")
public class AreaCultivoController {

    private final AreaCultivoService service;

    @GetMapping
    @Operation(summary = "Listar todas as áreas")
    public ResponseEntity<List<AreaCultivoDTO.Response>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar área por ID")
    public ResponseEntity<AreaCultivoDTO.Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/produtor/{produtorId}")
    @Operation(summary = "Listar áreas por produtor")
    public ResponseEntity<List<AreaCultivoDTO.Response>> findByProdutor(@PathVariable Long produtorId) {
        return ResponseEntity.ok(service.findByProdutor(produtorId));
    }

    @PostMapping
    @Operation(summary = "Criar área de cultivo")
    public ResponseEntity<AreaCultivoDTO.Response> create(@Valid @RequestBody AreaCultivoDTO.Request dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar área de cultivo")
    public ResponseEntity<AreaCultivoDTO.Response> update(@PathVariable Long id, @Valid @RequestBody AreaCultivoDTO.Request dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar área de cultivo")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
