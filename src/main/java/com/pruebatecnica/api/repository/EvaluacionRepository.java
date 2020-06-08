package com.pruebatecnica.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pruebatecnica.api.model.Evaluacion;

@Repository
public interface EvaluacionRepository extends MongoRepository<Evaluacion, String>{

	@Query("{'registroEval':{$gte: ?0, $lte: ?1}}")
	List<Evaluacion> findByRegistroEvalBetween(LocalDate from, LocalDate to);
	
	 @Query("{_id: ?0})")
	List<Evaluacion> findByID(String ID);
}	
