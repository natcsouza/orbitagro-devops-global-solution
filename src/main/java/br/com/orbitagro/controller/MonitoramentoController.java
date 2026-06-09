package br.com.orbitagro.controller;

import br.com.orbitagro.dto.MonitoramentoDTO;
import br.com.orbitagro.service.MonitoramentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/monitoramentos")
@RequiredArgsConstructor
@Tag(name = "Monitoramentos", description = "Leituras de sensores e satélite")
@SecurityRequirement(name = "Bearer")
public class MonitoramentoController {

    private final MonitoramentoService service;

    @GetMapping
    @Operation(summary = "Listar todos os monitoramentos")
    public ResponseEntity<List<MonitoramentoDTO.Response>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar monitoramento por ID")
    public ResponseEntity<MonitoramentoDTO.Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/area/{areaId}")
    @Operation(summary = "Listar monitoramentos por área")
    public ResponseEntity<List<MonitoramentoDTO.Response>> findByArea(@PathVariable Long areaId) {
        return ResponseEntity.ok(service.findByArea(areaId));
    }

    @PostMapping
    @Operation(summary = "Criar monitoramento")
    public ResponseEntity<MonitoramentoDTO.Response> create(@Valid @RequestBody MonitoramentoDTO.Request dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar monitoramento")
    public ResponseEntity<MonitoramentoDTO.Response> update(@PathVariable Long id, @Valid @RequestBody MonitoramentoDTO.Request dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar monitoramento")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
