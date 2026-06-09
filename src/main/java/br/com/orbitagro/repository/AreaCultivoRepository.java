package br.com.orbitagro.repository;

import br.com.orbitagro.entity.AreaCultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AreaCultivoRepository extends JpaRepository<AreaCultivo, Long> {
    List<AreaCultivo> findByProdutorId(Long produtorId);
}
