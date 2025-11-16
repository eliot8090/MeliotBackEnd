package FoodStore.backend.service.impl;

import FoodStore.backend.entity.*;
import FoodStore.backend.entity.dto.OrderRequestDTO;
import FoodStore.backend.entity.dto.OrderResponseDTO;
import FoodStore.backend.exception.AuthException;
import FoodStore.backend.repository.PedidoRepository;
import FoodStore.backend.repository.ProductoRepository;
import FoodStore.backend.repository.UsuarioRepository;
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
    private final UsuarioRepository usuarioRepository;

    // Crear pedido

    @Override
    @Transactional
    public OrderResponseDTO crearPedido(OrderRequestDTO request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new AuthException("PEDIDO_SIN_ITEMS");
        }

        // Buscar usuario real
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new AuthException("USUARIO_NO_ENCONTRADO"));

        // Cargar productos
        Map<Long, Producto> productos = new HashMap<>();
        for (OrderRequestDTO.OrderItemDTO it : request.getItems()) {
            Producto p = productoRepository.findById(it.getProductoId())
                    .orElseThrow(() -> new AuthException("PRODUCTO_NO_ENCONTRADO: " + it.getProductoId()));
            productos.put(p.getId(), p);
        }

        // Validar stock
        for (OrderRequestDTO.OrderItemDTO it : request.getItems()) {
            Producto p = productos.get(it.getProductoId());
            if (!Boolean.TRUE.equals(p.getDisponible()) || p.getStock() < it.getCantidad()) {
                throw new AuthException("STOCK_INSUFICIENTE: " + p.getNombre());
            }
        }

        // Crear Pedido
        double total = 0.0;
        List<DetallePedido> items = new ArrayList<>();

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .metodoPago(request.getMetodoPago())
                .notasAdicionales(request.getNotasAdicionales())
                .estado(Estado.pendiente)
                .total(0.0)
                .build();

        // Crear items y descontar stock
        for (OrderRequestDTO.OrderItemDTO it : request.getItems()) {
            Producto p = productos.get(it.getProductoId());
            // Actualizar stock
            p.setStock(p.getStock() - it.getCantidad());
            if (p.getStock() == 0) p.setDisponible(false);
            productoRepository.save(p);

            double precio = p.getPrecio();
            double subtotal = precio * it.getCantidad();

            DetallePedido detalle = DetallePedido.builder()
                    .pedido(pedido)
                    .producto(p)
                    .cantidad(it.getCantidad())
                    .precioUnitario(precio)
                    .subtotal(subtotal)
                    .build();

            items.add(detalle);
            total += subtotal;
        }

        pedido.setItems(items);
        pedido.setTotal(total);

        Pedido saved = pedidoRepository.save(pedido);
        return mapToResponse(saved);
    }

    // Listar pedidos

    @Override
    public List<OrderResponseDTO> listarPedidos(Long usuarioId) {
        var pedidos = (usuarioId == null)
                ? pedidoRepository.findAll()
                : pedidoRepository.findByUsuarioId(usuarioId);

        return pedidos.stream().map(this::mapToResponse).toList();
    }

    // Obtener pedido por ID

    @Override
    public OrderResponseDTO obtenerPedido(Long id) {
        var pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new AuthException("PEDIDO_NO_ENCONTRADO"));
        return mapToResponse(pedido);
    }

    // Actualizar estado del pedido

    @Override
    @Transactional
    public OrderResponseDTO actualizarEstado(Long id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new AuthException("PEDIDO_NO_ENCONTRADO"));

        // Convertir a enum de forma segura
        Estado estadoEnum;
        try {
            estadoEnum = Estado.valueOf(nuevoEstado.trim().toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new AuthException("ESTADO_INVALIDO: " + nuevoEstado);
        }

        // Restaurar stock si el pedido fue cancelado
        if (estadoEnum == Estado.cancelado) {
            pedido.getItems().forEach(item -> {
                Producto p = item.getProducto();
                p.setStock(p.getStock() + item.getCantidad());
                p.setDisponible(true);
                productoRepository.save(p);
            });
        }

        // Actualizar estado
        pedido.setEstado(estadoEnum);
        Pedido updated = pedidoRepository.save(pedido);

        return mapToResponse(updated);
    }

    // MAPPER → Pedido → OrderResponseDTO

    private OrderResponseDTO mapToResponse(Pedido p) {
        return OrderResponseDTO.builder()
                .id(p.getId())
                .fecha(p.getFecha())
                .total(p.getTotal())
                .usuario(
                        OrderResponseDTO.UsuarioDTO.builder()
                                .id(p.getUsuario().getId())
                                .nombre(p.getUsuario().getNombre())
                                .email(p.getUsuario().getEmail())
                                .build()
                )
                .telefono(p.getTelefono())
                .direccion(p.getDireccion())
                .metodoPago(p.getMetodoPago())
                .notasAdicionales(p.getNotasAdicionales())
                .estado(p.getEstado().name())
                .detallesPedido(
                        p.getItems().stream().map(it ->
                                OrderResponseDTO.OrderLineDTO.builder()
                                        .productoId(it.getProducto().getId())
                                        .producto(
                                                OrderResponseDTO.ProductoDTO.builder()
                                                        .id(it.getProducto().getId())
                                                        .nombre(it.getProducto().getNombre())
                                                        .build()
                                        )
                                        .cantidad(it.getCantidad())
                                        .precioUnitario(it.getPrecioUnitario())
                                        .subtotal(it.getSubtotal())
                                        .build()
                        ).toList()
                )
                .build();
    }
}
