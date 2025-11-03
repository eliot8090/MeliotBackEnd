package FoodStore.backend.entity.dto;

import lombok.Value;

@Value

// ENVIAMOS DATOS AL FRONTEND

public class ProductoDTO {
    Long id;
    String nombre;
    String descripcion;
    double precio;
    int stock;
    String imagenUrl;
    boolean disponible;

    Long categoriaId;
    String categoriaNombre;
}