package br.com.texo.repository;

import br.com.texo.domain.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, Long> {
    Optional<Studio> findAllByNameIgnoreCase(String name);
}
