package br.com.texo.repository;

import br.com.texo.domain.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findAllByNameIgnoreCase(String name);
}
