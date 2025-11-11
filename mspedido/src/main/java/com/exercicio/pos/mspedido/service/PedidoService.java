package com.exercicio.pos.mspedido.service;

import com.exercicio.pos.mspedido.client.ProdutoClient;
import com.exercicio.pos.mspedido.dto.PedidoRequest;
import com.exercicio.pos.mspedido.dto.PedidoResponse;
import com.exercicio.pos.mspedido.dto.ProdutoPedidoDTO;
import com.exercicio.pos.mspedido.model.Pedido;
import com.exercicio.pos.mspedido.model.StatusPedido;
import com.exercicio.pos.mspedido.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoClient produtoClient;


    @Transactional
    public PedidoResponse criarPedido(PedidoRequest request) {
       List<ProdutoPedidoDTO> produtosDetalhes = request.getIdProdutos().stream()
                .map(idProduto -> {
                    try {
                        return produtoClient.buscarProdutoPorId(idProduto);
                    } catch (Exception e) {
                        throw new RuntimeException("Produto ID " + idProduto + " não encontrado ou serviço indisponível.", e);
                    }
                })
                .collect(Collectors.toList());

        Pedido pedido = Pedido.builder()
                .dataPedido(LocalDateTime.now())
                .statusPedido(StatusPedido.CRIADO)
                .idProdutos(request.getIdProdutos())
                .build();

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        PedidoResponse response = new PedidoResponse();
        response.setId(pedidoSalvo.getId());
        response.setDataPedido(pedidoSalvo.getDataPedido());
        response.setStatusPedido(pedidoSalvo.getStatusPedido());
        response.setProdutos(produtosDetalhes);

        return response;
    }

    public Optional<PedidoResponse> buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id).map(pedido -> {

            List<ProdutoPedidoDTO> produtosDetalhes = pedido.getIdProdutos().stream()
                    .map(idProduto -> {
                        try {
                            return produtoClient.buscarProdutoPorId(idProduto);
                        } catch (Exception e) {
                            ProdutoPedidoDTO erro = new ProdutoPedidoDTO();
                            erro.setId(idProduto);
                            erro.setNome("Produto Indisponível/Excluído");
                            return erro;
                        }
                    })
                    .collect(Collectors.toList());

            PedidoResponse response = new PedidoResponse();
            response.setId(pedido.getId());
            response.setDataPedido(pedido.getDataPedido());
            response.setStatusPedido(pedido.getStatusPedido());
            response.setProdutos(produtosDetalhes);
            return response;
        });
    }
    @Transactional
    public PedidoResponse atualizarStatus(Long id, String novoStatus) {

        StatusPedido status = StatusPedido.valueOf(novoStatus.toUpperCase());

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com id: " + id));
        if (pedido.getStatusPedido() == StatusPedido.CANCELADO) {
            throw new RuntimeException("Não é possível alterar o status de um pedido CANCELADO.");
        }
        pedido.setStatusPedido(status);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return buscarPedidoPorId(pedidoAtualizado.getId())
                .orElseThrow(() -> new RuntimeException("Erro interno: Pedido recém-criado não foi encontrado."));
    }
}