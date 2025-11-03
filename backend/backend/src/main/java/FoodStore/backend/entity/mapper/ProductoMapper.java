package FoodStore.backend.entity.mapper;

import FoodStore.backend.entity.Producto;
import FoodStore.backend.entity.dto.ProductoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    @Mapping(source = "entity.categoria.id", target = "categoriaId")
    @Mapping(source = "entity.categoria.nombre", target = "categoriaNombre")
    ProductoDTO toDto(Producto entity);
}