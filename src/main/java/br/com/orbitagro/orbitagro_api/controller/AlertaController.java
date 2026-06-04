package br.com.orbitagro.orbitagro_api.controller;

import br.com.orbitagro.orbitagro_api.entity.Alerta;
import br.com.orbitagro.orbitagro_api.service.AlertaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService service;

    public AlertaController(AlertaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Alerta> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    public Alerta cadastrar(@RequestBody @Valid Alerta alerta) {
        return service.salvar(alerta);
    }
}