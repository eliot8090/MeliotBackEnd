package FoodStore.backend.service.impl;

import FoodStore.backend.entity.Categoria;
import FoodStore.backend.exception.AuthException;
import FoodStore.backend.repository.CategoriaRepository;
import FoodStore.backend.service.CategoriaService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    //  READ
    // Obtenemos todas las categorías

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    //READ
    // Buscamos categoría por ID

    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new AuthException("CATEGORIA_NO_ENCONTRADA"));
    }

    // CREATE / UPDATE
    // Guardamos o actualizamos una categoría

    @Override
    public Categoria save(Categoria categoria) {

        // Validamos campos requeridos
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty() ||
                categoria.getImagenUrl() == null || categoria.getImagenUrl().trim().isEmpty() ||
                categoria.getDescripcion() == null || categoria.getDescripcion().trim().isEmpty()) {
            throw new AuthException("CAMPOS_REQUERIDOS");
        }

        // Verificación de nombre duplicado
        boolean existe = categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre());
        if (existe && (categoria.getId() == null ||
                !categoriaRepository.findByNombreIgnoreCase(categoria.getNombre())
                        .get().getId().equals(categoria.getId()))) {
            throw new AuthException("NOMBRE_DUPLICADO");
        }

        return categoriaRepository.save(categoria);
    }

    //DELETE

    @Override
    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new AuthException("CATEGORIA_NO_ENCONTRADA"));

        // Verificamos si tiene productos asociados
        if (categoria.getProductos() != null && !categoria.getProductos().isEmpty()) {
            throw new AuthException("CATEGORIA_CON_PRODUCTOS");
        }

        categoriaRepository.deleteById(id);
    }

}

