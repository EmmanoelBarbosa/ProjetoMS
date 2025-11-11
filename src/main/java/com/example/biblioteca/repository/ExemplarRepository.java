package com.example.biblioteca.repository;

import com.example.biblioteca.model.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {
    List<Exemplar> findByLivroIdAndDisponivelTrue(Long livroId);
}
