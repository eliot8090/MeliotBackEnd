package FoodStore.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Categoria {

    @Id
    // Generamos el ID automáticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo obligatorio y único en la BD

    @Column(nullable = false, unique = true)
    private String nombre;

    // Campos obligatorios

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(nullable = false, length = 1000)
    private String imagenUrl;

    // Relación 1:N con producto
    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference   // Evita recursión infinita con Producto
    private List<Producto> productos;

}