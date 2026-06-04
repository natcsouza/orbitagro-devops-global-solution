package br.com.orbitagro.orbitagro_api.service;

import br.com.orbitagro.orbitagro_api.entity.Alerta;
import br.com.orbitagro.orbitagro_api.repository.AlertaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaService {

    private final AlertaRepository repository;

    public AlertaService(AlertaRepository repository) {
        this.repository = repository;
    }

    public List<Alerta> listarTodos() {
        return repository.findAll();
    }

    public Alerta salvar(Alerta alerta) {
        return repository.save(alerta);
    }
}