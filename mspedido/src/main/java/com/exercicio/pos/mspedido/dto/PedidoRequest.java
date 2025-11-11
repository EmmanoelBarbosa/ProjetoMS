package com.exercicio.pos.mspedido.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoRequest {

    private List<Long> idProdutos;
}
