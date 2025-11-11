package FoodStore.backend.service.impl;

import FoodStore.backend.entity.*;
import FoodStore.backend.entity.dto.OrderRequestDTO;
import FoodStore.backend.entity.dto.OrderResponseDTO;
import FoodStore.backend.exception.AuthException;
import FoodStore.backend.repository.PedidoRepository;
import FoodStore.backend.repository.ProductoRepository;
import FoodStore.backend.service.PedidoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public OrderResponseDTO crearPedido(OrderRequestDTO request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new AuthException("PEDIDO_SIN_ITEMS");
        }
        Map<Long, Producto> productos = new HashMap<>();
        for (OrderRequestDTO.OrderItemDTO it : request.getItems()) {
            Producto p = productoRepository.findById(it.getProductoId())
                    .orElseThrow(() -> new AuthException("PRODUCTO_NO_ENCONTRADO: " + it.getProductoId()));
            productos.put(p.getId(), p);
        }

        for (OrderRequestDTO.OrderItemDTO it : request.getItems()) {
            Producto p = productos.get(it.getProductoId());
            if (!Boolean.TRUE.equals(p.getDisponible()) || p.getStock() < it.getCantidad()) {
                throw new AuthException("STOCK_INSUFICIENTE: " + p.getNombre());
            }
        }

        double total = 0.0;
        List<DetallePedido> items = new ArrayList<>();

        Pedido pedido = Pedido.builder()
                .usuarioId(request.getUsuarioId())
                .telefono(request.getTelefono())           // del formulario de checkout
                .direccion(request.getDireccion())         // del formulario de checkout
                .metodoPago(request.getMetodoPago())       // del formulario de checkout
                .notasAdicionales(request.getNotasAdicionales()) // opcional
                .estado(Estado.pendiente)            // estado inicial
                .total(0.0)
                .build();

        for (OrderRequestDTO.OrderItemDTO it : request.getItems()) {
            Producto p = productos.get(it.getProductoId());

            p.setStock(p.getStock() - it.getCantidad());
            if (p.getStock() == 0) p.setDisponible(false); // opcional
            productoRepository.save(p);

            double precio = p.getPrecio();
            double subtotal = precio * it.getCantidad();

            DetallePedido pi = DetallePedido.builder()
                    .pedido(pedido)
                    .producto(p)
                    .cantidad(it.getCantidad())
                    .precioUnitario(precio)
                    .subtotal(subtotal)
                    .build();
            items.add(pi);

            total += subtotal;
        }

        pedido.setItems(items);
        pedido.setTotal(total);

        Pedido saved = pedidoRepository.save(pedido);

        return OrderResponseDTO.builder()
                .id(saved.getId())
                .fecha(saved.getFecha())
                .total(saved.getTotal())
                .usuarioId(saved.getUsuarioId())
                .telefono(saved.getTelefono())
                .direccion(saved.getDireccion())
                .metodoPago(saved.getMetodoPago())
                .notasAdicionales(saved.getNotasAdicionales())
                .estado(saved.getEstado().name())
                .detallesPedido(saved.getItems().stream().map(pi ->
                        OrderResponseDTO.OrderLineDTO.builder()
                                .productoId(pi.getProducto().getId())
                                .productoNombre(pi.getProducto().getNombre())
                                .cantidad(pi.getCantidad())
                                .precioUnitario(pi.getPrecioUnitario())
                                .subtotal(pi.getSubtotal())
                                .build()
                ).toList())
                .build();
    }

    @Override
    public List<OrderResponseDTO> listarPedidos(Long usuarioId) {
        var list = (usuarioId == null)
                ? pedidoRepository.findAll()
                : pedidoRepository.findByUsuarioId(usuarioId);

        return list.stream().map(p ->
                OrderResponseDTO.builder()
                        .id(p.getId())
                        .fecha(p.getFecha())
                        .total(p.getTotal())
                        .usuarioId(p.getUsuarioId())
                        .telefono(p.getTelefono())
                        .direccion(p.getDireccion())
                        .metodoPago(p.getMetodoPago())
                        .notasAdicionales(p.getNotasAdicionales())
                        .estado(p.getEstado().name())
                        .detallesPedido(p.getItems().stream().map(pi ->
                                OrderResponseDTO.OrderLineDTO.builder()
                                        .productoId(pi.getProducto().getId())
                                        .productoNombre(pi.getProducto().getNombre())
                                        .cantidad(pi.getCantidad())
                                        .precioUnitario(pi.getPrecioUnitario())
                                        .subtotal(pi.getSubtotal())
                                        .build()
                        ).toList())
                        .build()
        ).toList();

    }

    @Override
    public OrderResponseDTO obtenerPedido(Long id) {
        var p = pedidoRepository.findById(id)
                .orElseThrow(() -> new AuthException("PEDIDO_NO_ENCONTRADO"));
        return OrderResponseDTO.builder()
                .id(p.getId())
                .fecha(p.getFecha())
                .total(p.getTotal())
                .usuarioId(p.getUsuarioId())
                .telefono(p.getTelefono())
                .direccion(p.getDireccion())
                .metodoPago(p.getMetodoPago())
                .notasAdicionales(p.getNotasAdicionales())
                .estado(p.getEstado().name())
                .detallesPedido(p.getItems().stream().map(pi ->
                        OrderResponseDTO.OrderLineDTO.builder()
                                .productoId(pi.getProducto().getId())
                                .productoNombre(pi.getProducto().getNombre())
                                .cantidad(pi.getCantidad())
                                .precioUnitario(pi.getPrecioUnitario())
                                .subtotal(pi.getSubtotal())
                                .build()
                ).toList())
                .build();
    }
}
