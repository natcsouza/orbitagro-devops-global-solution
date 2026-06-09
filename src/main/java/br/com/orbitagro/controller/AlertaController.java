package br.com.orbitagro.controller;

import br.com.orbitagro.dto.AlertaDTO;
import br.com.orbitagro.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
@Tag(name = "Alertas", description = "Alertas gerados pelo sistema")
@SecurityRequirement(name = "Bearer")
public class AlertaController {

    private final AlertaService service;

    @GetMapping
    @Operation(summary = "Listar todos os alertas")
    public ResponseEntity<List<AlertaDTO.Response>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar alerta por ID")
    public ResponseEntity<AlertaDTO.Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/area/{areaId}")
    @Operation(summary = "Listar alertas por área")
    public ResponseEntity<List<AlertaDTO.Response>> findByArea(@PathVariable Long areaId) {
        return ResponseEntity.ok(service.findByArea(areaId));
    }

    @PostMapping
    @Operation(summary = "Criar alerta")
    public ResponseEntity<AlertaDTO.Response> create(@Valid @RequestBody AlertaDTO.Request dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alerta")
    public ResponseEntity<AlertaDTO.Response> update(@PathVariable Long id, @Valid @RequestBody AlertaDTO.Request dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar alerta")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
