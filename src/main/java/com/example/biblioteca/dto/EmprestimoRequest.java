package com.example.biblioteca.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmprestimoRequest {
    private Long usuarioId;
    private Long exemplarId;
    private LocalDateTime dataHoraEmprestimo;
}
