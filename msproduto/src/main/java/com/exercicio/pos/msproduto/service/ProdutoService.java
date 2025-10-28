package com.exercicio.pos.msproduto.service;

import com.exercicio.pos.msproduto.dto.ProdutoDTO;
import com.exercicio.pos.msproduto.model.Produto;
import com.exercicio.pos.msproduto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    private ProdutoDTO toDTO(Produto produto) {
        return ProdutoDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .quantidade(produto.getQuantidade())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .build();
    }
    private Produto toEntity(ProdutoDTO dto) {
        return Produto.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .quantidade(dto.getQuantidade())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .build();
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        Produto produto = toEntity(produtoDTO);
        Produto savedProduto = produtoRepository.save(produto);
        return toDTO(savedProduto);
    }

    public List<ProdutoDTO> findAll() {
        return produtoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProdutoDTO> findById(Long id) {
        return produtoRepository.findById(id).map(this::toDTO);
    }

    public ProdutoDTO update(Long id, ProdutoDTO produtoDetailsDTO) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoDetailsDTO.getNome());
                    produto.setQuantidade(produtoDetailsDTO.getQuantidade());
                    produto.setDescricao(produtoDetailsDTO.getDescricao());
                    produto.setPreco(produtoDetailsDTO.getPreco());

                    Produto updatedProduto = produtoRepository.save(produto);

                    return toDTO(updatedProduto);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id " + id));
    }

    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }
}