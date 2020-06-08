package com.pruebatecnica.api.service;

import java.util.List;

import com.pruebatecnica.api.dto.EvaluacionDTO;

public interface EvaluacionService {

	String save(EvaluacionDTO evaluacionDTO);
	void update(EvaluacionDTO evaluacionDTO);
	List<EvaluacionDTO> getEvaluacionesByRangoFechas(String inicio, String fin);
	EvaluacionDTO getEvaluacionById(String ID);
}
