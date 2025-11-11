package com.exercicio.pos.mspedido.controller;

import com.exercicio.pos.mspedido.dto.PedidoRequest;
import com.exercicio.pos.mspedido.dto.PedidoResponse;
import com.exercicio.pos.mspedido.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse criarPedido(@RequestBody PedidoRequest request) {

        return pedidoService.criarPedido(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPedidoPorId(@PathVariable Long id) {
        return pedidoService.buscarPedidoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /*
     * MÉTODOS DE ATUALIZAÇÃO DE STATUS (Lógica de Negócio)
     */

    // PUT /api/pedidos/{id}/confirmar
    // CONFIRMA o pedido (simulando a etapa de pagamento/processamento)
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<PedidoResponse> confirmarPedido(@PathVariable Long id) {
        try {
            PedidoResponse pedidoAtualizado = pedidoService.atualizarStatus(id, "CONFIRMADO");
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponse> cancelarPedido(@PathVariable Long id) {
        try {
            PedidoResponse pedidoAtualizado = pedidoService.atualizarStatus(id, "CANCELADO");
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}