package com.pruebatecnica.api.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

import com.pruebatecnica.api.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@ApiModel(value = "Evaluacion")
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
public class EvaluacionDTO {

	@ApiModelProperty(value = "Id Evaluación" , readOnly = true)
	private String id;
	
	@ApiModelProperty(value = "Nombre completo del usuario" , required = true , example = "test test")
	@NotEmpty(message = "{app.challenge.evaluacion.validations.field.notempty}")
	private String nombres;
	
	@ApiModelProperty(value = "Correo del usuario", example = "test@evaluacion.com" , required = true)
	@NotEmpty(message = "{app.challenge.evaluacion.validations.field.notempty}")
	@Email(message = "{app.challenge.evaluacion.validations.correo.email}")
	private String correo;
	
	@ApiModelProperty(value = "Calificación de la evaluación", allowableValues = "range(1, 10)", required = true, example = "8")
	@NotEmpty(message = "{app.challenge.evaluacion.validations.field.notempty}")
	@Range(min=1, max=10, message = "{app.challenge.evaluacion.validations.calificacion.range}")
	private String calificacion;

	@ApiModelProperty(value = "Comentario de la evaluación" , required = true , example = "Bueno!")
	@NotEmpty(message = "{app.challenge.evaluacion.validations.field.notempty}")
	private String comentario;
	
	@ApiModelProperty(value = "Fecha de registro de la evaluación", readOnly = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_PATTERN_FORMAT)
	private LocalDate registroEval;
	
}
