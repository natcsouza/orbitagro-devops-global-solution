package br.com.orbitagro.repository;

import br.com.orbitagro.entity.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProdutorRepository extends JpaRepository<Produtor, Long> {
    Optional<Produtor> findByEmail(String email);
    Optional<Produtor> findByCpf(String cpf);
    boolean existsByEmail(String email);
}
