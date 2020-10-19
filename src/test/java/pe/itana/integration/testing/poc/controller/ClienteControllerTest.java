package pe.itana.integration.testing.poc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import pe.itana.integration.testing.poc.entity.Cliente;
import pe.itana.integration.testing.poc.utils.Response;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-integration-testing.properties")
class ClienteControllerTest {
  
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;
  
  @Autowired
  private ClienteController clienteController;
  
  String url;
  
  @BeforeAll
  static void setupBeforeAll() {
  }
  
  @BeforeEach
  void runBeforeEach() {
    url = "http://localhost:" + port + "/clientes";
  }
  
  @Test
  void contextLoads() {
    assertThat(clienteController).isNotNull();
  }
    
  
  // ------------ findAll---------------------------------------
  @Test
  void findAll_Should_ObtenerClientes() {
    // test case
    Response<List<Cliente>> response = restTemplate.getForObject(url, Response.class);
    
    // Validation
    assertThat(response.getData()).hasSize(3);
  }
  
  @Test
  void findAll_Should_RetornarOk() {
    // test case
    ResponseEntity<?> response = restTemplate.getForEntity(url, Response.class);
    
    // validation
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
  
  

}