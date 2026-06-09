package br.com.orbitagro.orbitagro_api.controller;

import br.com.orbitagro.orbitagro_api.dto.request.MonitoramentoRequestDTO;
import br.com.orbitagro.orbitagro_api.dto.response.MonitoramentoResponseDTO;
import br.com.orbitagro.orbitagro_api.entity.Monitoramento;
import br.com.orbitagro.orbitagro_api.mapper.MonitoramentoMapper;
import br.com.orbitagro.orbitagro_api.service.MonitoramentoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitoramentos")
public class MonitoramentoController {

    private final MonitoramentoService service;
    private final MonitoramentoMapper mapper;

    public MonitoramentoController(MonitoramentoService service, MonitoramentoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MonitoramentoResponseDTO> listarTodos() {
        return service.listarTodos().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<MonitoramentoResponseDTO> cadastrar(@RequestBody @Valid MonitoramentoRequestDTO dto) {
        Monitoramento monitoramento = service.salvar(dto);
        return ResponseEntity.status(201).body(mapper.toResponse(monitoramento));
    }
}
