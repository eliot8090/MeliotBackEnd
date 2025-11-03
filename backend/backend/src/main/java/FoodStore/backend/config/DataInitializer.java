package FoodStore.backend.config;

import FoodStore.backend.entity.Categoria;
import FoodStore.backend.entity.Producto;
import FoodStore.backend.entity.Rol;
import FoodStore.backend.entity.Usuario;
import FoodStore.backend.repository.CategoriaRepository;
import FoodStore.backend.repository.ProductoRepository;
import FoodStore.backend.repository.UsuarioRepository;
import FoodStore.backend.util.Sha256Util;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataInitializer {

    // Introducimos los repositorios

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private ProductoRepository productoRepository;

    @PostConstruct
    public void init() {

        // Creamos el usuario Administrador

        if (!usuarioRepository.existsByEmail("admin@food.com")) {
            System.out.println("🟢 Creando usuario Administrador inicial...");

            Usuario admin = Usuario.builder()
                    .nombre("Administrador")
                    .email("admin@food.com")
                    .role(Rol.admin)
                    .contrasena(Sha256Util.hash("admin123"))
                    .build();

            usuarioRepository.save(admin);
        }

        // Creamos categorías

        if (categoriaRepository.count() == 0) {
            System.out.println("🟢 Cargando categorías y productos iniciales...");

            Categoria hamburguesas = Categoria.builder()
                    .nombre("Hamburguesas")
                    .descripcion("Las mejores burgers")
                    .imagenUrl("https://www.carniceriademadrid.es/wp-content/uploads/2022/09/smash-burger-que-es.jpg")
                    .build();

            Categoria pizzas = Categoria.builder()
                    .nombre("Pizzas")
                    .descripcion("Variedad en pizzas")
                    .imagenUrl("https://www.cocinadelirante.com/sites/default/files/images/2023/08/receta-de-pizza-sin-horno.jpg")
                    .build();

            categoriaRepository.saveAll(List.of(hamburguesas, pizzas));

            // Creamos productos

            Producto prod1 = Producto.builder()
                    .nombre("Clásica")
                    .descripcion("Doble carne, queso cheddar")
                    .precio(1200.0)
                    .stock(50)
                    .imagenUrl("https://www.carniceriademadrid.es/wp-content/uploads/2022/09/smash-burger-que-es.jpg")
                    .disponible(true)
                    .categoria(hamburguesas)
                    .build();

            Producto prod2 = Producto.builder()
                    .nombre("Vegana")
                    .descripcion("Hamburguesa de lentejas")
                    .precio(1500.0)
                    .stock(10)
                    .imagenUrl("https://storage.googleapis.com/avena-recipes-v2/agtzfmF2ZW5hLWJvdHIZCxIMSW50ZXJjb21Vc2VyGICAgMW2rJ8LDA/24-04-2020/1587767770012.jpeg")
                    .disponible(true)
                    .categoria(hamburguesas)
                    .build();

            Producto prod3 = Producto.builder()
                    .nombre("Muzzarella")
                    .descripcion("Pizza de muzzarella clásica")
                    .precio(800.0)
                    .stock(30)
                    .imagenUrl("https://www.laespanolaaceites.com/wp-content/uploads/2019/06/pizza-con-tomate-albahaca-y-mozzarella-1080x671.jpg")
                    .disponible(true)
                    .categoria(pizzas)
                    .build();

            Producto prod4 = Producto.builder()
                    .nombre("Inactivo")
                    .descripcion("Producto no disponible")
                    .precio(500.0)
                    .stock(0)
                    .imagenUrl("https://img-global.cpcdn.com/recipes/5734a20cb61636e6/1200x630cq80/photo.jpg")
                    .disponible(false)
                    .categoria(pizzas)
                    .build();

            productoRepository.saveAll(List.of(prod1, prod2, prod3, prod4));
        }
    }
}
