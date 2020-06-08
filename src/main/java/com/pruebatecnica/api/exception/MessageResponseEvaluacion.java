package com.pruebatecnica.api.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.pruebatecnica.api.dto.EvaluacionDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@JsonInclude(value = Include.NON_NULL)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseEvaluacion {

	 @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	 private LocalDateTime timestamp;
	 private int status;
	 private String message;
	 private String path;
	 private EvaluacionDTO response;
	 
	 @ApiModelProperty(hidden = true)
	 private List<Field> fields;
	
	 @Builder
	 @Data
	 @NoArgsConstructor
	 @AllArgsConstructor
	 public static class Field {
		
		private String name;
		private String message;
	}
}
