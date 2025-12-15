package com.jeffersonsousa.smartstock.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jeffersonsousa.smartstock.exception.exceptions.ControllerNotFoundException;
import com.jeffersonsousa.smartstock.exception.exceptions.DatabaseException;
import com.jeffersonsousa.smartstock.exception.exceptions.InsufficientStockException;
import com.jeffersonsousa.smartstock.exception.exceptions.StockException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(StockException.class)
	public ResponseEntity<StandardError> stockError(StockException e, HttpServletRequest request){
		String error = "Regra de estoque violada !!!";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);		
	}
	
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<StandardError> insufficientStock(InsufficientStockException e, HttpServletRequest request){
		String error = "Saída Negada!!";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ControllerNotFoundException.class)
	public ResponseEntity<StandardError> controllerNotFound(ControllerNotFoundException e, HttpServletRequest request){
		String error = "Não Encontrado!!";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> databaseException(DatabaseException e, HttpServletRequest request){
		String error = "Erro no banco de dados";
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
