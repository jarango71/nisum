package com.nisum.evaluation.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nisum.evaluation.dto.MessageResponseDTO;

@ControllerAdvice
public class ExceptionManager {

	@ExceptionHandler(RuntimeException.class)
	@Order(Ordered.LOWEST_PRECEDENCE)
    protected ResponseEntity<MessageResponseDTO> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponseDTO(ex.getMessage()));
    }
	
	@ExceptionHandler(BadRequestException.class)
	@Order(Ordered.HIGHEST_PRECEDENCE)
    protected ResponseEntity<MessageResponseDTO> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDTO(ex.getMessage()));
    }
	
}
