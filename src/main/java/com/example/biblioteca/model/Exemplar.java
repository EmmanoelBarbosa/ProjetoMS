package com.example.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exemplar")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Exemplar {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Livro livro;

    // true = disponível
    @Column(nullable = false)
    private boolean disponivel = true;
}
