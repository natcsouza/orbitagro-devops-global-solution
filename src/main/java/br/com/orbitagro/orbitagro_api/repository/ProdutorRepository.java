package br.com.orbitagro.orbitagro_api.repository;

import br.com.orbitagro.orbitagro_api.entity.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {
}