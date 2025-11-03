package FoodStore.backend.entity.dto;

import FoodStore.backend.entity.Rol;
import lombok.Value;

@Value
public class UsuarioDTO {
    Long id;
    String nombre;
    String email;
    Rol role;
    String apellido;
    String celular;
}
