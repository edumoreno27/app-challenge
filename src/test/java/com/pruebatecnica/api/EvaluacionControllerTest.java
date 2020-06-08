package com.pruebatecnica.api;

import static com.pruebatecnica.api.util.Constants.URL_BASE;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.pruebatecnica.api.model.Evaluacion;
import com.pruebatecnica.api.repository.EvaluacionRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class EvaluacionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	EvaluacionRepository evaluacionRepository;
	
	@Test
	void getEvaluacionesByFechas() throws Exception {
		
		when(evaluacionRepository.findByRegistroEvalBetween(Mockito.any(),Mockito.any())).thenReturn(getModelEvaluaciones());

		mockMvc.perform(MockMvcRequestBuilders
			      .get(URL_BASE)
			      .param("fechaInicio", "01/06/2020")
			      .param("fechaFin", "30/06/2020")
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.value()))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].id").isNotEmpty());
	}
	
	@Test
	void update() throws Exception {
		
		when(evaluacionRepository.findById(Mockito.any())).thenReturn(getModelEvaluacion());
		
		verify(evaluacionRepository, times(0)).save(Mockito.any());

		mockMvc.perform(MockMvcRequestBuilders
			      .put(URL_BASE.concat("/{id}"), "5edd36eb090efb6c04a98b4e")
			      .content("{\"nombres\":\"test\",\"correo\":\"test@aplicacion.com\",\"calificacion\":\"5\",\"comentario\":\"Aceptable\"}")
			      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().is(HttpStatus.OK.value()))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.value()))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());
	}
	
	@Test
	void save() throws Exception {
	
		when(evaluacionRepository.save(Mockito.any())).thenReturn(getModelEvaluacion().get());
		
		mockMvc.perform(MockMvcRequestBuilders
			      .post(URL_BASE)
			      .content("{\"nombres\":\"test\",\"correo\":\"test@aplicacion.com\",\"calificacion\":\"9\",\"comentario\":\"Excelente\"}")
			      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().is(HttpStatus.CREATED.value()))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.CREATED.value()))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty());
	}
	
	@Test
	void errorValidarCampos_BadRequest() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
			      .post(URL_BASE)
			      .content("{\"nombres\":\"test\",\"correo\":\"test@aplicacion.com\",\"calificacion\":\"8\"}")
			      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isBadRequest())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.path").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.fields").isArray())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.fields[*].name").value("comentario"))
				  .andExpect(MockMvcResultMatchers.jsonPath("$.fields[*].message").value("Es requerido"));
		
		mockMvc.perform(MockMvcRequestBuilders
			      .post(URL_BASE)
			      .content("{\"nombres\":\"test\",\"correo\":\"test@aplicacion.com\",\"calificacion\":\"20\",\"comentario\":\"Excelente\"}")
			      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isBadRequest())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.path").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.fields").isArray())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.fields[*].name").value("calificacion"))
				  .andExpect(MockMvcResultMatchers.jsonPath("$.fields[*].message").value("La calificación permitida es de 1 hasta 10."));
		
		mockMvc.perform(MockMvcRequestBuilders
				  .get(URL_BASE)
			      .param("fechaInicio", "32/06/2020")
			      .param("fechaFin", "30/06/2020")
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isBadRequest())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.path").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.fields").isArray())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.fields[*].name").value("fechaInicio"))
				  .andExpect(MockMvcResultMatchers.jsonPath("$.fields[*].message").value("Fecha o formato inválido, formato válido dd/MM/yyyy"));
		
	}
	
	@Test
	void errorValidarSiExisteEntidad_NotFound() throws Exception {

		when(evaluacionRepository.findByRegistroEvalBetween(Mockito.any(),Mockito.any())).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders
			      .get(URL_BASE)
			      .param("fechaInicio", "30/06/2020")
			      .param("fechaFin", "30/06/2020")
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isNotFound())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.path").isNotEmpty());
	}
	
	public Optional<Evaluacion> getModelEvaluacion(){
		return Optional.of(new Evaluacion("5edd36eb090efb6c04a98b4e", "test test", "test@aplicacion.com", (byte)8, "Excelente", LocalDate.now()));
	}
	
	public List<Evaluacion> getModelEvaluaciones(){
		
		List<Evaluacion> list = new ArrayList<>();
		list.add(getModelEvaluacion().get());
		
		return list;
	}

}
