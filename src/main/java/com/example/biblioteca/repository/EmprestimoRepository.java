package com.example.biblioteca.repository;

import com.example.biblioteca.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByDataHoraEmprestimoBetween(LocalDateTime inicio, LocalDateTime fim);
}
