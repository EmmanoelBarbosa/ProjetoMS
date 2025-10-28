package com.exercicio.pos.msproduto.repository;

import com.exercicio.pos.msproduto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}