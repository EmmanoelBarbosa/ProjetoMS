package com.example.biblioteca.controller;

import com.example.biblioteca.model.UsuarioBiblioteca;
import com.example.biblioteca.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<UsuarioBiblioteca> criar(@RequestBody UsuarioBiblioteca usuario) {
        UsuarioBiblioteca saved = usuarioRepository.save(usuario);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<UsuarioBiblioteca> listar() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioBiblioteca> buscar(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioBiblioteca> alterar(@PathVariable Long id, @RequestBody UsuarioBiblioteca body) {
        return usuarioRepository.findById(id).map(u -> {
            u.setNome(body.getNome());
            u.setEmail(body.getEmail());
            usuarioRepository.save(u);
            return ResponseEntity.ok(u);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
