package FoodStore.backend.repository;

import FoodStore.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Herencia JPA
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Usado para login
    Optional<Usuario> findByEmail(String email);

    // Usado para registro
    boolean existsByEmail(String email);
}
