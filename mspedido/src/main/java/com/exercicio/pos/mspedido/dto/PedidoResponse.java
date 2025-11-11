package com.exercicio.pos.mspedido.dto;

import com.exercicio.pos.mspedido.model.StatusPedido;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoResponse {

    private Long id;
    private LocalDateTime dataPedido;
    private StatusPedido statusPedido;

    private List<ProdutoPedidoDTO> produtos;
}
