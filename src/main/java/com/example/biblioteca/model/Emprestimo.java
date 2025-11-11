package com.example.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "emprestimo")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Emprestimo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHoraEmprestimo;

    @ManyToOne(optional = false)
    private UsuarioBiblioteca usuario;

    @ManyToOne(optional = false)
    private Exemplar exemplar;
}
