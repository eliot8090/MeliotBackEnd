package FoodStore.backend.service;


import FoodStore.backend.entity.Rol;
import FoodStore.backend.entity.Usuario;
import FoodStore.backend.exception.AuthException;
import FoodStore.backend.repository.UsuarioRepository;
import FoodStore.backend.util.Sha256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registro de usuario
    public Usuario register(Usuario usuario) {

        // Validaciones de los campos obligatorios

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new AuthException("NOMBRE_REQUERIDO");
            }
            if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
                throw new AuthException("APELLIDO_REQUERIDO");
            }
            if (usuario.getCelular() == null || usuario.getCelular().trim().isEmpty()) {
                throw new AuthException("CELULAR_REQUERIDO");
            }

            // Verificamos si email ya existe
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                throw new AuthException("EMAIL_YA_REGISTRADO");
            }

            // Asignamos el rol cliente
            usuario.setRole(Rol.client);

            // Hasheamos la contraseña antes de guardar
            usuario.setContrasena(Sha256Util.hash(usuario.getContrasena()));

            return usuarioRepository.save(usuario);
        }

    // Autenticación del login

    public Usuario login(String email, String contrasena) {
        // Buscamos el usuario por email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("EMAIL_NO_ENCONTRADO"));

        // Verificamos la contraseña hasheada
        boolean match = Sha256Util.verify(contrasena, usuario.getContrasena());

        // Fallo de contraseña
        if (!match) {
            throw new AuthException("CONTRASENA_INCORRECTA");
        }
        // Login exitoso
        return usuario;
    }
}

