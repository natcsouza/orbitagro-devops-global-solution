package br.com.orbitagro.orbitagro_api.service;

import br.com.orbitagro.orbitagro_api.entity.Monitoramento;
import br.com.orbitagro.orbitagro_api.repository.MonitoramentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoramentoService {

    private final MonitoramentoRepository repository;

    public MonitoramentoService(MonitoramentoRepository repository) {
        this.repository = repository;
    }

    public List<Monitoramento> listarTodos() {
        return repository.findAll();
    }

    public Monitoramento salvar(Monitoramento monitoramento) {
        return repository.save(monitoramento);
    }
}