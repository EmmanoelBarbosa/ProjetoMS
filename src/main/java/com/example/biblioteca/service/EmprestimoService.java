package com.example.biblioteca.service;

import com.example.biblioteca.model.Emprestimo;
import com.example.biblioteca.model.Exemplar;
import com.example.biblioteca.model.UsuarioBiblioteca;
import com.example.biblioteca.repository.EmprestimoRepository;
import com.example.biblioteca.repository.ExemplarRepository;
import com.example.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;
    private final ExemplarRepository exemplarRepository;
    private final UsuarioRepository usuarioRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             ExemplarRepository exemplarRepository,
                             UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.exemplarRepository = exemplarRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Emprestimo realizarEmprestimo(Long usuarioId, Long exemplarId, LocalDateTime dataHora) {
        UsuarioBiblioteca usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        Exemplar exemplar = exemplarRepository.findById(exemplarId)
                .orElseThrow(() -> new IllegalArgumentException("Exemplar não encontrado"));
        if (!exemplar.isDisponivel()) {
            throw new IllegalStateException("Exemplar não disponível para empréstimo");
        }
        exemplar.setDisponivel(false);
        exemplarRepository.save(exemplar);

        Emprestimo e = Emprestimo.builder()
                .usuario(usuario)
                .exemplar(exemplar)
                .dataHoraEmprestimo(dataHora == null ? LocalDateTime.now() : dataHora)
                .build();
        return emprestimoRepository.save(e);
    }

    public List<Emprestimo> buscarPorIntervalo(LocalDateTime inicio, LocalDateTime fim) {
        return emprestimoRepository.findByDataHoraEmprestimoBetween(inicio, fim);
    }
}
