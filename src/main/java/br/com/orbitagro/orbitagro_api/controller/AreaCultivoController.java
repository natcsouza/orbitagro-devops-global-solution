package br.com.orbitagro.orbitagro_api.controller;

import br.com.orbitagro.orbitagro_api.dto.request.AreaCultivoRequestDTO;
import br.com.orbitagro.orbitagro_api.dto.response.AreaCultivoResponseDTO;
import br.com.orbitagro.orbitagro_api.entity.AreaCultivo;
import br.com.orbitagro.orbitagro_api.mapper.AreaCultivoMapper;
import br.com.orbitagro.orbitagro_api.service.AreaCultivoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areas")
public class AreaCultivoController {

    private final AreaCultivoService service;
    private final AreaCultivoMapper mapper;

    public AreaCultivoController(AreaCultivoService service, AreaCultivoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<AreaCultivoResponseDTO> listarTodos() {
        return service.listarTodos().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<AreaCultivoResponseDTO> cadastrar(@RequestBody @Valid AreaCultivoRequestDTO dto) {
        AreaCultivo areaCultivo = service.salvar(dto);
        return ResponseEntity.status(201).body(mapper.toResponse(areaCultivo));
    }
}
