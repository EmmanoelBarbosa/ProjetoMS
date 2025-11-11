package com.example.biblioteca.repository;

import com.example.biblioteca.model.UsuarioBiblioteca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioBiblioteca, Long> {
}
