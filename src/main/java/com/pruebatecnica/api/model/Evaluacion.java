package com.pruebatecnica.api.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluacion {

	@Id
	private String idEval;
	
	private String nombresCliente;
	
	private String correoCliente;
	
	private byte calificacionEval;
	
	private String comentarioEval;

	private LocalDate registroEval;

}
