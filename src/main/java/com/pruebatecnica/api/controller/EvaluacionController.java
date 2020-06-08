package com.pruebatecnica.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pruebatecnica.api.constraints.ValidateDate;
import com.pruebatecnica.api.dto.EvaluacionDTO;
import com.pruebatecnica.api.exception.MessageResponse;
import com.pruebatecnica.api.exception.MessageResponseEvaluacion;
import com.pruebatecnica.api.service.EvaluacionService;
import com.pruebatecnica.api.util.Constants;
import com.pruebatecnica.api.util.EvaluacionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/evaluaciones")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@Api(description = "Servicio para la gestión de evaluaciones")
public class EvaluacionController {
	
	@Autowired
	private EvaluacionService evaluacionService;
	
	@Autowired
	private EvaluacionUtil evaluacionUtil;
	
	@ApiOperation(value = "Actualiza una evaluación")
    @ApiResponses(
    		value = {
    				@ApiResponse(code = 200, message = "Evaluación {ID} se actualizo correctamente", response = MessageResponse.class)
    	    }
    )
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable(value = "id", required = true) String idEval,
										 @ApiParam(value = "Evaluacion",name = "Evaluacion")
										 @Valid @RequestBody EvaluacionDTO evaluacionDTO) {

		log.info("call update()");

		evaluacionDTO.setId(idEval);
		
		evaluacionService.update(evaluacionDTO);
		
		return ResponseEntity.ok(MessageResponse.builder()
				.status(HttpStatus.OK.value())
				.message(evaluacionUtil.getMessage("app.challenge.evaluacion.update", idEval))
				.timestamp(LocalDateTime.now()).build());
	}
	
	
	@ApiOperation(value = "Devuelve una lista de evaluaciones según rango de fechas")
    @ApiResponses(
    		value = {
    				@ApiResponse(code = 200, message = "Se consulto correctamente", response = MessageResponse.class)
    	    }
    )
	@GetMapping
	public ResponseEntity<Object> getEvaluacionesByFechasRange(	@ApiParam(value = "Fecha Inicio" , example = "01/01/2020", format = "dd/MM/yyyy")
																@RequestParam(value = "fechaInicio", required = false)
																@NotEmpty(message = "{app.challenge.evaluacion.validations.field.notempty}")			
																@ValidateDate(message = "{app.challenge.evaluacion.validations.fecha.validated}", pattern = Constants.DATE_PATTERN_FORMAT)
															    String fechaInicio,
															    @ApiParam(value = "Fecha Fin" , example = "01/01/2020" , format = "dd/MM/yyyy")
															    @RequestParam(value = "fechaFin", required = false)
																@NotEmpty(message = "{app.challenge.evaluacion.validations.field.notempty}")
																@ValidateDate(message = "{app.challenge.evaluacion.validations.fecha.validated}", pattern = Constants.DATE_PATTERN_FORMAT)
																String fechaFin) {

		
		log.info("call getEvaluacionesByFechasRange()");
		
		List<EvaluacionDTO> listEvals = evaluacionService.getEvaluacionesByRangoFechas(fechaInicio , fechaFin);
		
		return ResponseEntity.ok(MessageResponse.builder()
				.status(HttpStatus.OK.value())
				.response(listEvals)
				.message(evaluacionUtil.getMessage("app.challenge.evaluacion.getEvaluacionesByFechas"))
				.timestamp(LocalDateTime.now()).build());
	}
	
	@ApiOperation(value = "Devuelve una evaluación por ID")
    @ApiResponses(
    		value = {
    				@ApiResponse(code = 200, message = "Se consulto correctamente", response = MessageResponseEvaluacion.class)
    	    }
    )
	@GetMapping("/{id}")
	public ResponseEntity<Object> getEvaluacionByID(@PathVariable(value = "id", required = true) String ID){
		log.info("call getEvaluacionByID()");
		
		EvaluacionDTO evaluacion = evaluacionService.getEvaluacionById(ID);
		
		return ResponseEntity.ok(MessageResponseEvaluacion.builder()
				.status(HttpStatus.OK.value())
				.response(evaluacion)
				.message(evaluacionUtil.getMessage("app.challenge.evaluacion.getEvaluacionByID"))
				.timestamp(LocalDateTime.now()).build());
	}
	
	
	@ApiOperation(value = "Crea una nueva evaluación")
    @ApiResponses(
    		value = {
    				@ApiResponse(code = 201, message = "Evaluación {ID} se registro correctamente", response = MessageResponse.class)
    	    }
    )
	@PostMapping
	public ResponseEntity<Object> save(@ApiParam(value = "Evaluacion",name = "Evaluacion") @Valid @RequestBody EvaluacionDTO evaluacionDTO) {

		log.info("call save()");

		String id = evaluacionService.save(evaluacionDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.builder()
				.status(HttpStatus.CREATED.value())
				.message(evaluacionUtil.getMessage("app.challenge.evaluacion.create", id))
				.timestamp(LocalDateTime.now()).build());
	}
}
