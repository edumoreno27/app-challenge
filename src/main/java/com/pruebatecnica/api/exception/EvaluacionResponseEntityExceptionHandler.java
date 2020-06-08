package com.pruebatecnica.api.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pruebatecnica.api.util.EvaluacionUtil;

@RestControllerAdvice
public class EvaluacionResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private EvaluacionUtil evaluacionUtil;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<MessageResponse.Field> fieldErrors =  ex.getBindingResult().getFieldErrors().stream()
											.map(x -> MessageResponse.Field.builder()
															.name(x.getField())
															.message(x.getDefaultMessage()).build())
											.collect(Collectors.toList());
		
		String requestURI = ((ServletWebRequest) request).getRequest().getRequestURI();
		
		MessageResponse messageError = MessageResponse.builder()
				  .timestamp(LocalDateTime.now())
				 .status(status.value())
				 .message(evaluacionUtil.getMessage("app.challenge.evaluacion.exceptions.handleMethodArgumentNotValid"))
				 .fields(fieldErrors)
				 .path(requestURI).build();
		  
	    return new ResponseEntity<>(messageError, headers, status);
	}
	

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<MessageResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
																		HttpServletRequest request) {
		
		  MessageResponse messageError = MessageResponse.builder()
				 .timestamp(LocalDateTime.now())
				 .status(HttpStatus.NOT_FOUND.value())
				 .message(ex.getMessage())
				 .path(request.getQueryString() != null ? String.join("?", request.getRequestURI(), request.getQueryString()) : request.getRequestURI()).build();
		  
	    return new ResponseEntity<>(messageError, new HttpHeaders(), HttpStatus.NOT_FOUND);
	 }
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<MessageResponse> handleConstraintViolationException(ConstraintViolationException ex,
																		HttpServletRequest request) {
		
			List<MessageResponse.Field> fieldErrors =  ex.getConstraintViolations().stream()
				.map(x -> MessageResponse.Field.builder()
								.name(x.getPropertyPath().toString().split("\\.")[1])
								.message(x.getMessage()).build()
				).collect(Collectors.toList());
			
			MessageResponse messageError = MessageResponse.builder()
			.timestamp(LocalDateTime.now())
			.status(HttpStatus.BAD_REQUEST.value())
			.message(evaluacionUtil.getMessage("app.challenge.evaluacion.exceptions.handleMethodArgumentNotValid"))
			.fields(fieldErrors)
			.path(request.getQueryString() != null ? String.join("?", request.getRequestURI(), request.getQueryString()) : request.getRequestURI()).build();
			
		 return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
	 }
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		 MessageResponse messageError = MessageResponse.builder()
				 .timestamp(LocalDateTime.now())
				 .status(HttpStatus.METHOD_NOT_ALLOWED.value())
				 .message(ex.getMessage())
				 .path(((ServletWebRequest) request).getRequest().getRequestURI()).build();
		  
	    return new ResponseEntity<>(messageError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);

	}
}