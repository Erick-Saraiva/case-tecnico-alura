package br.com.alura.AluraFake.repository;

import br.com.alura.AluraFake.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> { }
