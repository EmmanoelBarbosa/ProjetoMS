package com.exercicio.pos.mspedido.client;

import com.exercicio.pos.mspedido.dto.ProdutoPedidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msproduto")
public interface ProdutoClient {

    @GetMapping("/api/produtos/{id}")
    ProdutoPedidoDTO buscarProdutoPorId(@PathVariable("id") Long id);
}
