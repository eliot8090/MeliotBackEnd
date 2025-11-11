package FoodStore.backend.service;

import FoodStore.backend.entity.dto.OrderRequestDTO;
import FoodStore.backend.entity.dto.OrderResponseDTO;

import java.util.List;

public interface PedidoService {
    OrderResponseDTO crearPedido(OrderRequestDTO request);
    List<OrderResponseDTO> listarPedidos(Long usuarioId);
    OrderResponseDTO obtenerPedido(Long id);
}
