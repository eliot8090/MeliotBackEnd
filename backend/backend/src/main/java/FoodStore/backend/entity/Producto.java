package FoodStore.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id

    // Generamos el ID automáticamente

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campos obligatorios

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private String imagenUrl;

    @Column(nullable = false)
    private Boolean disponible = true;

    // Vinculamos con Categoría
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "categoria_id")
    @JsonBackReference // Evita recursión infinita con Categoría
    private Categoria categoria;
}
