package com.example.demo.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Captura erros de validação (quando usamos @Valid no Controller)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleValidacao(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErroResposta resposta = new ErroResposta("Solicitação inválida", erros);
        return ResponseEntity.badRequest().body(resposta);
    }

    // Captura erros genéricos de argumentos inválidos
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResposta> handleIllegalArgument(IllegalArgumentException ex) {
        ErroResposta resposta = new ErroResposta("Solicitação inválida", List.of(ex.getMessage()));
        return ResponseEntity.badRequest().body(resposta);
    }
}