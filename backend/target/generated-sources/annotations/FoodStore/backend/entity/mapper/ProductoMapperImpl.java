package FoodStore.backend.entity.mapper;

import FoodStore.backend.entity.Categoria;
import FoodStore.backend.entity.Producto;
import FoodStore.backend.entity.dto.ProductoDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-09T10:18:13-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class ProductoMapperImpl implements ProductoMapper {

    @Override
    public ProductoDTO toDto(Producto entity) {
        if ( entity == null ) {
            return null;
        }

        Long categoriaId = null;
        String categoriaNombre = null;
        Long id = null;
        String nombre = null;
        String descripcion = null;
        double precio = 0.0d;
        int stock = 0;
        String imagenUrl = null;
        boolean disponible = false;

        categoriaId = entityCategoriaId( entity );
        categoriaNombre = entityCategoriaNombre( entity );
        id = entity.getId();
        nombre = entity.getNombre();
        descripcion = entity.getDescripcion();
        if ( entity.getPrecio() != null ) {
            precio = entity.getPrecio();
        }
        if ( entity.getStock() != null ) {
            stock = entity.getStock();
        }
        imagenUrl = entity.getImagenUrl();
        if ( entity.getDisponible() != null ) {
            disponible = entity.getDisponible();
        }

        ProductoDTO productoDTO = new ProductoDTO( id, nombre, descripcion, precio, stock, imagenUrl, disponible, categoriaId, categoriaNombre );

        return productoDTO;
    }

    private Long entityCategoriaId(Producto producto) {
        if ( producto == null ) {
            return null;
        }
        Categoria categoria = producto.getCategoria();
        if ( categoria == null ) {
            return null;
        }
        Long id = categoria.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityCategoriaNombre(Producto producto) {
        if ( producto == null ) {
            return null;
        }
        Categoria categoria = producto.getCategoria();
        if ( categoria == null ) {
            return null;
        }
        String nombre = categoria.getNombre();
        if ( nombre == null ) {
            return null;
        }
        return nombre;
    }
}
