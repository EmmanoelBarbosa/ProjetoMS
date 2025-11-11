package com.example.biblioteca.controller;

import com.example.biblioteca.dto.EmprestimoRequest;
import com.example.biblioteca.model.Emprestimo;
import com.example.biblioteca.repository.EmprestimoRepository;
import com.example.biblioteca.service.EmprestimoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;
    private final EmprestimoRepository emprestimoRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmprestimoController.class);

    public EmprestimoController(EmprestimoService emprestimoService, EmprestimoRepository emprestimoRepository) {
        this.emprestimoService = emprestimoService;
        this.emprestimoRepository = emprestimoRepository;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody EmprestimoRequest request) {
        Emprestimo e = emprestimoService.realizarEmprestimo(request.getUsuarioId(), request.getExemplarId(), request.getDataHoraEmprestimo());
        return ResponseEntity.ok(e);
    }

    @GetMapping
    public List<Emprestimo> listar() {
        return emprestimoRepository.findAll();
    }

    @GetMapping("/search")
    public List<Emprestimo> buscarPorIntervalo(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return emprestimoService.buscarPorIntervalo(inicio, fim);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscar(@PathVariable Long id) {
        return emprestimoRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!emprestimoRepository.existsById(id)) return ResponseEntity.notFound().build();
        emprestimoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
