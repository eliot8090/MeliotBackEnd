package FoodStore.backend.entity.dto;

import FoodStore.backend.entity.Estado;
import lombok.Builder;
import lombok.Value;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class OrderResponseDTO {
    Long id;
    LocalDateTime fecha;
    Double total;
    UsuarioDTO usuario;
    String telefono;
    String direccion;
    String metodoPago;
    String notasAdicionales;
    String estado;
    List<OrderLineDTO> detallesPedido;

    @Value
    @Builder
    public static class UsuarioDTO {
        Long id;
        String nombre;
        String email;
    }

    @Value
    @Builder
    public static class OrderLineDTO {
        Long productoId;
        ProductoDTO producto;
        Integer cantidad;
        Double precioUnitario;
        Double subtotal;
    }

    @Value
    @Builder
    public static class ProductoDTO {
        Long id;
        String nombre;
    }
}
