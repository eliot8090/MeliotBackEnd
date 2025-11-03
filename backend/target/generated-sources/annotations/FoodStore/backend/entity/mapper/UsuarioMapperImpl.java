package FoodStore.backend.entity.mapper;

import FoodStore.backend.entity.Rol;
import FoodStore.backend.entity.Usuario;
import FoodStore.backend.entity.dto.UsuarioDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-02T13:53:07-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioDTO toDto(Usuario entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String nombre = null;
        String email = null;
        Rol role = null;
        String apellido = null;
        String celular = null;

        id = entity.getId();
        nombre = entity.getNombre();
        email = entity.getEmail();
        role = entity.getRole();
        apellido = entity.getApellido();
        celular = entity.getCelular();

        UsuarioDTO usuarioDTO = new UsuarioDTO( id, nombre, email, role, apellido, celular );

        return usuarioDTO;
    }

    @Override
    public Usuario toEntity(UsuarioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Usuario.UsuarioBuilder usuario = Usuario.builder();

        usuario.id( dto.getId() );
        usuario.nombre( dto.getNombre() );
        usuario.email( dto.getEmail() );
        usuario.role( dto.getRole() );
        usuario.apellido( dto.getApellido() );
        usuario.celular( dto.getCelular() );

        return usuario.build();
    }
}
