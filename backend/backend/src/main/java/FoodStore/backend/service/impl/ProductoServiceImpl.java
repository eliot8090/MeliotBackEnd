package FoodStore.backend.service.impl;

import FoodStore.backend.entity.Producto;
import FoodStore.backend.entity.dto.ProductoDTO;
import FoodStore.backend.entity.mapper.ProductoMapper;
import FoodStore.backend.exception.AuthException;
import FoodStore.backend.repository.CategoriaRepository;
import FoodStore.backend.repository.ProductoRepository;
import FoodStore.backend.service.ProductoService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    public ProductoServiceImpl(ProductoRepository productoRepository,
                               CategoriaRepository categoriaRepository,
                               ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoMapper = productoMapper;
    }

    // READ
    @Override
    public List<ProductoDTO> findAll(Boolean soloDisponibles) {
        List<Producto> productos = (soloDisponibles != null && soloDisponibles)
                ? productoRepository.findByDisponibleTrue()
                : productoRepository.findAll();

        return productos.stream()
                .map(productoMapper::toDto)
                .toList();
    }
    //READ
    // Obtenemos producto por ID
    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new AuthException("PRODUCTO_NO_ENCONTRADO"));
    }

    // CREATE / UPDATE

    @Override
    public Producto save(Producto producto, Long categoriaId) {
        // Validamos campos requeridos
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty() ||
                producto.getPrecio() == null || producto.getPrecio() <= 0 ||
                producto.getStock() == null || producto.getStock() < 0 ||
                producto.getImagenUrl() == null || producto.getImagenUrl().trim().isEmpty()) {
            throw new AuthException("CAMPOS_REQUERIDOS");
        }

        var categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new AuthException("CATEGORIA_NO_ENCONTRADA"));
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }

    //DELETE
    @Override
    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new AuthException("PRODUCTO_NO_ENCONTRADO");
        }
        productoRepository.deleteById(id);
    }
}
