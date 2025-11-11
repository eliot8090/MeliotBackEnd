package FoodStore.backend.entity.dto;


import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class OrderRequestDTO {
    Long usuarioId;
    List<OrderItemDTO> items;
    String telefono;
    String direccion;
    String metodoPago;
    String notasAdicionales;

    @Value
    @Builder
    public static class OrderItemDTO {
        Long productoId;
        Integer cantidad;
    }
}
