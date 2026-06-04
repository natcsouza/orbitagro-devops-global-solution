package br.com.orbitagro.orbitagro_api.controller;

import br.com.orbitagro.orbitagro_api.entity.AreaCultivo;
import br.com.orbitagro.orbitagro_api.service.AreaCultivoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areas")
public class AreaCultivoController {

    private final AreaCultivoService service;

    public AreaCultivoController(AreaCultivoService service) {
        this.service = service;
    }

    @GetMapping
    public List<AreaCultivo> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    public AreaCultivo cadastrar(@RequestBody @Valid AreaCultivo areaCultivo) {
        return service.salvar(areaCultivo);
    }
}