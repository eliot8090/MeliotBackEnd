package FoodStore.backend.controller;

import FoodStore.backend.entity.dto.OrderRequestDTO;
import FoodStore.backend.entity.dto.OrderResponseDTO;
import FoodStore.backend.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> crear(@RequestBody OrderRequestDTO request) {
        var created = pedidoService.crearPedido(request);
        return ResponseEntity.created(URI.create("/api/pedidos/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> listar(@RequestParam(required = false) Long usuarioId) {
        return ResponseEntity.ok(pedidoService.listarPedidos(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPedido(id));
    }
}
