package br.com.orbitagro.orbitagro_api.controller;

import br.com.orbitagro.orbitagro_api.entity.Monitoramento;
import br.com.orbitagro.orbitagro_api.service.MonitoramentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitoramentos")
public class MonitoramentoController {

    private final MonitoramentoService service;

    public MonitoramentoController(MonitoramentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Monitoramento> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    public Monitoramento cadastrar(@RequestBody @Valid Monitoramento monitoramento) {
        return service.salvar(monitoramento);
    }
}