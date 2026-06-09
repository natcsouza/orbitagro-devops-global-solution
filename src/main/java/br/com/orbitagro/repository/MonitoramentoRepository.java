package br.com.orbitagro.repository;

import br.com.orbitagro.entity.Monitoramento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MonitoramentoRepository extends JpaRepository<Monitoramento, Long> {
    List<Monitoramento> findByAreaCultivoId(Long areaCultivoId);
}
