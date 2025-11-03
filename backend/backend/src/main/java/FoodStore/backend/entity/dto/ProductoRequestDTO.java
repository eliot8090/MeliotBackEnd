package FoodStore.backend.entity.dto;

import lombok.Data;

@Data

// RECIBIMOS DATOS DEL FRONTEND

public class ProductoRequestDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String imagenUrl;
    private Boolean disponible;
    private Long categoriaId;
}
