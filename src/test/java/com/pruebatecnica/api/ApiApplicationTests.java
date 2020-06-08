
package com.pruebatecnica.api;

import static com.pruebatecnica.api.util.Constants.URL_BASE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.pruebatecnica.api.dto.EvaluacionDTO;
import com.pruebatecnica.api.exception.MessageResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApiApplicationTests {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;

	
	@Test
	void getEvaluacionesByFechas() throws Exception {
		
		MessageResponse messageResp  = restTemplate.exchange(URL_BASE.concat("?fechaInicio={fechaInicio}&fechaFin={fechaFin}"), HttpMethod.GET, null, MessageResponse.class, "01/06/2020", "30/06/2020")
		 .getBody();

		assertEquals(HttpStatus.OK.value(), messageResp.getStatus());
		
	}
	
	@Test
	void save() throws Exception {
		
		EvaluacionDTO evaluacionDTO = EvaluacionDTO.builder().nombres("Andre Lopez")
		.correo("andre@test.com")
		.calificacion("7")
		.comentario("Bueno!")
		.build();

		ResponseEntity<MessageResponse> response = restTemplate.postForEntity(URL_BASE, evaluacionDTO, MessageResponse.class);

        assertEquals(HttpStatus.CREATED.value(), response.getBody().getStatus());
	}

	
	@Test
	void update() throws Exception {
		
		EvaluacionDTO evaluacionDTO = EvaluacionDTO.builder().nombres("Andre Lopez")
		.correo("andre@test.com")
		.calificacion("5")
		.comentario("Regular")
		.build();
		
		 HttpHeaders headers = new HttpHeaders();
	     headers.setContentType(MediaType.APPLICATION_JSON);
	     HttpEntity<EvaluacionDTO> entity = new HttpEntity<>(evaluacionDTO, headers);

	     ResponseEntity<MessageResponse> response = restTemplate.exchange(URL_BASE.concat("/5edc9affc1572d391178f47b"), HttpMethod.PUT, entity, MessageResponse.class);

	     assertEquals(HttpStatus.OK.value(), response.getBody().getStatus());
		
	}
}
