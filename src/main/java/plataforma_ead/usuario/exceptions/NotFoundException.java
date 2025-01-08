package plataforma_ead.usuario.exceptions;

public class NotFoundException extends RuntimeException{
    // Construtor da class NotFoundException que retorna uma message
    public NotFoundException(String message) {
        super(message);
    }
}
