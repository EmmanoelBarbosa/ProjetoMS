package com.example.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "livro")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Livro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String titulo;

    private String autor;
}
