package FoodStore.backend.service;

import FoodStore.backend.entity.Categoria;
import java.util.List;

// Definimos que operaciones puede hacer un servicio de categoría

public interface CategoriaService {
    List<Categoria> findAll(); // Metodo para listar todas las categorías.
    Categoria findById(Long id); // Busca una categoría por su ID.
    Categoria save(Categoria categoria); // Crea o actualiza una categoría (depende de si tiene ID o no).
    void delete(Long id); // Elimina una categoría por su ID.
}
