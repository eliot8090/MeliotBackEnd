package FoodStore.backend.controller;

import FoodStore.backend.entity.Categoria;
import FoodStore.backend.entity.dto.CategoriaDTO;
import FoodStore.backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriaController {

    // Introducimos categoría service

    @Autowired private CategoriaService categoriaService;

    // READ

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        // Traemos las categorías desde la BD
        List<CategoriaDTO> categorias = categoriaService.findAll().stream()
                .map(cat -> new CategoriaDTO(cat.getId(), cat.getNombre(), cat.getDescripcion(), cat.getImagenUrl()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(categorias);
    }

    // CREATE

    @PostMapping
    public ResponseEntity<CategoriaDTO> createCategoria(@RequestBody Categoria categoria) {
        Categoria savedCategoria = categoriaService.save(categoria);
        CategoriaDTO dto = new CategoriaDTO(savedCategoria.getId(), savedCategoria.getNombre(), savedCategoria.getDescripcion(), savedCategoria.getImagenUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // UPDATE

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        categoria.setId(id);
        Categoria updatedCategoria = categoriaService.save(categoria);
        CategoriaDTO dto = new CategoriaDTO(updatedCategoria.getId(), updatedCategoria.getNombre(), updatedCategoria.getDescripcion(), updatedCategoria.getImagenUrl());
        return ResponseEntity.ok(dto);
    }

    // DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}