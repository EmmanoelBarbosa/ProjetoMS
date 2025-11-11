package com.exercicio.pos.mspedido.dto;

import lombok.Data;

@Data
public class ProdutoPedidoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private float preco;
}