package FoodStore.backend.service;

import FoodStore.backend.entity.Producto;
import FoodStore.backend.entity.dto.ProductoDTO;
import java.util.List;

// Definimos las operaciones que pueden realizarse sobre los productos

public interface ProductoService {
    List<ProductoDTO> findAll(Boolean soloDisponibles); // Lista productos. Si soloDisponibles = true, filtra solo los activos.
    Producto findById(Long id);  // Busca un producto por su ID.
    Producto save(Producto producto, Long categoriaId); // Guarda o actualiza un producto / asocia el producto a una categoría existente
    void delete(Long id); // Elimina un producto por su ID (previa validación de existencia).
}
