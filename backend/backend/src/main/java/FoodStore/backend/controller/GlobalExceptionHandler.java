package FoodStore.backend.controller;

import FoodStore.backend.exception.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de excepciones personalizadas

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, String>> handleAuthException(AuthException ex) {
        HttpStatus status;

        if (ex.getMessage().contains("REGISTRADO")) {
            status = HttpStatus.CONFLICT;

        } else if (ex.getMessage().contains("NO_ENCONTRADA")) {
            status = HttpStatus.NOT_FOUND;

        } else if (ex.getMessage().contains("CATEGORIA_CON_PRODUCTOS") ||
                ex.getMessage().contains("CAMPOS_REQUERIDOS") ||
                ex.getMessage().contains("NOMBRE_DUPLICADO")) {
            status = HttpStatus.BAD_REQUEST;

        } else {
            status = HttpStatus.UNAUTHORIZED;
        }

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }

    // Manejo de excepciones genéricas

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleGenericRuntimeException(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Error interno del servidor: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
