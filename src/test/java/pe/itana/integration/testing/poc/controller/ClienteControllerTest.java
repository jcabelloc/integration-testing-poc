package pe.itana.integration.testing.poc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import pe.itana.integration.testing.poc.entity.Cliente;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-integration-testing.properties")
public class ClienteControllerTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	
	@Autowired
	private ClienteController clienteController;
		
	
	@Test
	void contextLoads() {
		assertThat(clienteController).isNotNull();
	}
	
	
	@Test
	public void temp() {
		assertThat(restTemplate.getForObject("http://localhost:" + port + "/clientes",
				String.class)).isNotEmpty();
	}
	
	@Test
	public void temp2() throws Exception {
		assertTrue(
                restTemplate
                    .getForObject("http://localhost:" + port + "/clientes", Cliente[].class).length == 3);
	}
	
	@Test
	public void temp3() throws Exception {
		assertThat(
                restTemplate
                    .getForObject("http://localhost:" + port + "/clientes", Cliente[].class).length).isEqualTo(3);
	}
	
    @Test
    public void temp4() {
    	ResponseEntity<Cliente[]> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/clientes", Cliente[].class);
    	assertEquals(200, responseEntity.getStatusCodeValue());
    }

}