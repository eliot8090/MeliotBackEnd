package FoodStore.backend.exception;

// Convertimos el fallo de lógica en un tipo de error manejable para la API

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}