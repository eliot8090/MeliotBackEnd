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
    Long usuarioId;
    String telefono;
    String direccion;
    String metodoPago;
    String notasAdicionales;
    String estado;
    List<OrderLineDTO> detallesPedido;

    @Value
    @Builder
    public static class OrderLineDTO {
        Long productoId;
        String productoNombre;
        Integer cantidad;
        Double precioUnitario;
        Double subtotal;
    }
}
