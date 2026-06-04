package br.com.orbitagro.orbitagro_api.repository;

import br.com.orbitagro.orbitagro_api.entity.Monitoramento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoramentoRepository extends JpaRepository<Monitoramento, Long> {
}