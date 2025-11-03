package FoodStore.backend.entity.mapper;

import FoodStore.backend.entity.Usuario;
import FoodStore.backend.entity.dto.UsuarioDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDto(Usuario entity);

    Usuario toEntity(UsuarioDTO dto);
}