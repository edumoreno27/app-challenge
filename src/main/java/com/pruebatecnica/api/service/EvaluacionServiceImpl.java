package com.pruebatecnica.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebatecnica.api.dto.EvaluacionDTO;
import com.pruebatecnica.api.exception.ResourceNotFoundException;
import com.pruebatecnica.api.model.Evaluacion;
import com.pruebatecnica.api.repository.EvaluacionRepository;
import com.pruebatecnica.api.util.EvaluacionUtil;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class EvaluacionServiceImpl implements EvaluacionService {

	@Autowired
	private EvaluacionRepository evaluacionRepository;
	
	@Autowired
	private EvaluacionUtil evaluacionUtil;
	
	
	public String save(EvaluacionDTO evaluacionDTO) {
		
		Evaluacion evaluacion = new Evaluacion(null,
											evaluacionDTO.getNombres(),
											evaluacionDTO.getCorreo(),
											Byte.valueOf(evaluacionDTO.getCalificacion()),
											evaluacionDTO.getComentario(),
											LocalDate.now());
		
		String id = evaluacionRepository.save(evaluacion).getIdEval();

		log.info(evaluacionUtil.getMessage("app.challenge.evaluacion.create", evaluacion.getIdEval()));

		return id;
	}

	@Override
	public List<EvaluacionDTO> getEvaluacionesByRangoFechas(String inicio, String fin) {

		
		List<EvaluacionDTO> listEvals = evaluacionRepository.findByRegistroEvalBetween(EvaluacionUtil.convertStringToLocalDate(inicio), 
																					   EvaluacionUtil.convertStringToLocalDate(fin))
						.stream().map(e -> EvaluacionDTO.builder().id(e.getIdEval())
									   		.nombres(e.getNombresCliente())
									   		.correo(e.getCorreoCliente())
									   		.comentario(e.getComentarioEval())
									   		.calificacion(String.valueOf(e.getCalificacionEval()))
									   		.registroEval(e.getRegistroEval()).build()
	   	).collect(Collectors.toList());
		
		if(listEvals.isEmpty()) {
			throw new ResourceNotFoundException(evaluacionUtil.getMessage("app.challenge.evaluacion.exceptions.notExistsEvaluaciones",inicio ,fin));
		}
	
	    return listEvals;
	}



	@Override
	public void update(EvaluacionDTO evaluacionDTO) {
		
			Evaluacion evaluacion = getEvaluacion(evaluacionDTO.getId());
			evaluacion.setNombresCliente(evaluacionDTO.getNombres());
			evaluacion.setCorreoCliente(evaluacionDTO.getCorreo());
			evaluacion.setComentarioEval(evaluacionDTO.getComentario());
			evaluacion.setCalificacionEval(Byte.valueOf(evaluacionDTO.getCalificacion()));
			evaluacion.setRegistroEval(LocalDate.now());
				
			evaluacionRepository.save(evaluacion);
				
			log.info(evaluacionUtil.getMessage("app.challenge.evaluacion.update", evaluacion.getIdEval()));
	}

	private  Evaluacion getEvaluacion(String id) {
		return 	evaluacionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(evaluacionUtil.getMessage("app.challenge.evaluacion.exceptions.notFound")));
	}

	@Override
	public EvaluacionDTO getEvaluacionById(String ID) {
		List<EvaluacionDTO> listEvals = evaluacionRepository.findByID(ID)
					.stream().map(e -> EvaluacionDTO.builder().id(e.getIdEval())
					.nombres(e.getNombresCliente())
					.correo(e.getCorreoCliente())
					.comentario(e.getComentarioEval())
					.calificacion(String.valueOf(e.getCalificacionEval()))
					.registroEval(e.getRegistroEval()).build()
							).collect(Collectors.toList());
		
		if(listEvals.isEmpty()) {
			throw new ResourceNotFoundException(evaluacionUtil.getMessage("app.challenge.evaluacion.exceptions.notFound"));
		}

		return listEvals.get(0);
	}
	
}
