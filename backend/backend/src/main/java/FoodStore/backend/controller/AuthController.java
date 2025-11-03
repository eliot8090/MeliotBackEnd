package FoodStore.backend.controller;


import FoodStore.backend.entity.Usuario;
import FoodStore.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import FoodStore.backend.entity.dto.UsuarioDTO;
import FoodStore.backend.entity.mapper.UsuarioMapper;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    // POST: http://localhost:8080/api/auth/register
    //Manejamos la creación de nuevos clientes
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody Usuario usuario) {
        Usuario newUser = authService.register(usuario);

        UsuarioDTO userDTO = usuarioMapper.toDto(newUser);

        return ResponseEntity.status(201).body(userDTO);
    }

    // POST: http://localhost:8080/api/auth/login
    //Manejamos la autenticación de clientes existentes
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody Usuario usuario) {
        Usuario loggedInUser = authService.login(usuario.getEmail(), usuario.getContrasena());

        UsuarioDTO userDTO = usuarioMapper.toDto(loggedInUser);

        return ResponseEntity.ok(userDTO); // Devolvemos 200 OK con el DTO
    }
}