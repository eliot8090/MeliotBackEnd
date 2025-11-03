package FoodStore.backend.controller;

import FoodStore.backend.entity.Producto;
import FoodStore.backend.entity.dto.ProductoDTO;
import FoodStore.backend.entity.dto.ProductoRequestDTO;
import FoodStore.backend.exception.AuthException;
import FoodStore.backend.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // READ
    // Obtenemos todos los productos

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAll(
            @RequestParam(value = "disponible", required = false) Boolean disponible) {
        return ResponseEntity.ok(productoService.findAll(disponible));
    }

    //READ
    // Obtenemos productos por ID

    @GetMapping("/{id}")
    public Producto getById(@PathVariable Long id) {
        return productoService.findById(id);
    }

    // POST

    @PostMapping
    public ResponseEntity<ProductoDTO> create(@RequestBody ProductoRequestDTO dto) {
        if (dto.getCategoriaId() == null)
            throw new AuthException("CATEGORIA_NO_ENCONTRADA");

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setImagenUrl(dto.getImagenUrl());
        producto.setDisponible(dto.getDisponible() != null ? dto.getDisponible() : true);

        Producto saved = productoService.save(producto, dto.getCategoriaId());
        ProductoDTO dtoResponse = productoService.findAll(null)
                .stream()
                .filter(p -> p.getId().equals(saved.getId()))
                .findFirst()
                .orElseThrow();

        return ResponseEntity.created(URI.create("/api/productos/" + saved.getId())).body(dtoResponse);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> update(@PathVariable Long id, @RequestBody ProductoRequestDTO dto) {
        if (dto.getCategoriaId() == null)
            throw new AuthException("CATEGORIA_NO_ENCONTRADA");

        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setImagenUrl(dto.getImagenUrl());
        producto.setDisponible(dto.getDisponible() != null ? dto.getDisponible() : true);

        Producto updated = productoService.save(producto, dto.getCategoriaId());
        ProductoDTO dtoResponse = productoService.findAll(null)
                .stream()
                .filter(p -> p.getId().equals(updated.getId()))
                .findFirst()
                .orElseThrow();

        return ResponseEntity.ok(dtoResponse);
    }

    // DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
