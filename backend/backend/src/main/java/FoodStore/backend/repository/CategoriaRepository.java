package FoodStore.backend.repository;
import FoodStore.backend.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Evitamos duplicados en la creación o actualización
    boolean existsByNombreIgnoreCase(String nombre);

    // Búsquedas sin distinguir mayúsculas / minúsculas
    Optional<Categoria> findByNombreIgnoreCase(String nombre);
}