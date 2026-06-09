package br.com.orbitagro.orbitagro_api.controller;

import br.com.orbitagro.orbitagro_api.dto.request.AlertaRequestDTO;
import br.com.orbitagro.orbitagro_api.dto.response.AlertaResponseDTO;
import br.com.orbitagro.orbitagro_api.entity.Alerta;
import br.com.orbitagro.orbitagro_api.mapper.AlertaMapper;
import br.com.orbitagro.orbitagro_api.service.AlertaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService service;
    private final AlertaMapper mapper;

    public AlertaController(AlertaService service, AlertaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<AlertaResponseDTO> listarTodos() {
        return service.listarTodos().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<AlertaResponseDTO> cadastrar(@RequestBody @Valid AlertaRequestDTO dto) {
        Alerta alerta = service.salvar(dto);
        return ResponseEntity.status(201).body(mapper.toResponse(alerta));
    }
}
