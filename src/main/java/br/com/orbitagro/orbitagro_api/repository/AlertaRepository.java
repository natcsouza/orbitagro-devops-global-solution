package br.com.orbitagro.orbitagro_api.repository;

import br.com.orbitagro.orbitagro_api.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
}