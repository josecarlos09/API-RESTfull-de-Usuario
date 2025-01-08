package plataforma_ead.usuario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class) // Estou indicando qual excepion va ser tratada
    public ResponseEntity<ErroRecordResponse> handleNotFoundException(NotFoundException exception){
        // usa o DTO ErroRecordResponse que recebe um erro de código(NOT_FOUND.value()), uma mensagem(exception.getMessage()) e uma descrição mais especifica (null)
        var erroRecordResponse = new ErroRecordResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), null);
        // Retorne o erroRecordResponse
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroRecordResponse);
    }

    // Metodo que trata das validações feitas no DTO e retorna o status do protocolo, a mensagem e o tipo do erro
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRecordResponse> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String nomeCampo = ((FieldError) error).getField();
            String mensagemErro = error.getDefaultMessage();
            erros.put(nomeCampo, mensagemErro);
           }
        );
        var erroRecordResponse = new ErroRecordResponse(HttpStatus.BAD_REQUEST.value(), "ERRO DE VALIDAÇÃO", erros);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroRecordResponse);
    }
}