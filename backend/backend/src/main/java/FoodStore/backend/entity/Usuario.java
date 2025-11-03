package FoodStore.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    // Clave primaria de la tabla
    @Id

    // La base de datos especifíca el valor del ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Almacenamos id y nombre de cada usuario
    private Long id;
    private String nombre;

    // Creación de columnas, con restricción de unicidad, para que no pueda haber dos usuarios con el mismo valor
    @Column(unique = true)
    private String email;

    // Contraseña hasheada
    private String contrasena;

    // Guardamos el rol como cadena de texto
    @Enumerated(EnumType.STRING)
    private Rol role;

    // Almacenamos apellido y celular
    private String apellido;
    private String celular;
}