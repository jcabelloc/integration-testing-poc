package pe.itana.integration.testing.poc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Random;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import pe.itana.integration.testing.poc.AbstractContainerBaseTest;
import pe.itana.integration.testing.poc.entity.Cliente;
import pe.itana.integration.testing.poc.entity.Cliente.TipoDocumento;
import pe.itana.integration.testing.poc.utils.Message;
import pe.itana.integration.testing.poc.utils.Response;



@TestMethodOrder(OrderAnnotation.class)
class ClienteControllerTest extends AbstractContainerBaseTest {
  
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;
  
  @Autowired
  private ClienteController clienteController;
  
  HttpHeaders headers = new HttpHeaders();
  
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
    
  
  
  @Test
  @Sql("/testdata/insert_clientes.sql")
  @Order(0)
  void iniciarDataPrueba() {
    
  }
  //------------ findById---------------------------------------
  
  @ParameterizedTest
  @Order(1) 
  @CsvFileSource(resources = "/clientes_id_json_respuesta.csv", numLinesToSkip = 1, delimiter = ';')
  void findById_Should_RetornarJsonResponse(Integer id, String expected) throws JSONException {
    // test case
    String response = restTemplate.getForObject(url + "/" + id, String.class);
    
    // validation
    JSONAssert.assertEquals(expected, response, JSONCompareMode.STRICT);
    
  }
  
  // ------------ findAll---------------------------------------
  
  @Test
  @Order(1)
  void findAll_Should_ObtenerClientes() {
    // test case
    int nroRegistrosDataPrueba = 5;
    Response<List<Cliente>> response = restTemplate.getForObject(url, Response.class);
    
    // Validation
    assertThat(response.getData()).hasSize(nroRegistrosDataPrueba);
  }
  
  @Test
  void findAll_Should_RetornarOk() {
    // test case
    ResponseEntity<?> response = restTemplate.getForEntity(url, Response.class);
    
    // validation
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
  
  //------------ create---------------------------------------
  @Test
  void create_Should_RetornarBadRequest_When_DataEsInvalida() {
    // test case
    Cliente cliente = new Cliente();
    HttpEntity<Cliente> request = new HttpEntity<>(cliente, headers);
    ResponseEntity<?> response = restTemplate.postForEntity(url, request, Response.class);
    
    // validation
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
  
  @Test
  void create_Should_RetornarBadRequest_When_NroDocEsInvalido() {
    // test case
    String nroDocInvalido = "1234567";
    Cliente cliente = new Cliente("Juan Perez", TipoDocumento.DNI, nroDocInvalido);
    HttpEntity<Cliente> request = new HttpEntity<>(cliente, headers);
    ResponseEntity<?> response = restTemplate.postForEntity(url, request, Response.class);
    
    // validation
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
  
  @Test
  void create_Should_RetornarBadRequestAndMensajeDataInvalida_When_NroDocEsDuplicado() {
    // test case
    String nroDocDuplicado = "20257758214";
    Cliente cliente = new Cliente("Juan Perez", TipoDocumento.RUC, nroDocDuplicado);
    HttpEntity<Cliente> request = new HttpEntity<>(cliente, headers);
    ResponseEntity<Response> response = restTemplate.postForEntity(url, request, Response.class);
    
    // validation
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(Message.DATA_INVALIDA.getCodigo(), response.getBody().getCodigo());
    assertEquals(Message.DATA_INVALIDA.getTexto(), response.getBody().getMensaje());
  }
  
  @ParameterizedTest
  @CsvFileSource(resources = "/clientes_data_invalida.csv", numLinesToSkip = 1)
  void create_Should_RetornarBadRequest_When_ClienteConDataInvalida(String nombre, 
      String tipoDocumento, String nroDocumento) {
    // test case
    Cliente cliente = new Cliente();
    cliente.setNombre(nombre);
    cliente.setNroDocumento(nroDocumento);
    try {
      cliente.setTipoDocumento(TipoDocumento.valueOf(tipoDocumento));
    } catch (IllegalArgumentException e) {
      cliente.setTipoDocumento(null);
    }
    
    HttpEntity<Cliente> request = new HttpEntity<>(cliente, headers);
    ResponseEntity<?> response = restTemplate.postForEntity(url, request, Response.class);
    
    // validation
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
  
  @ParameterizedTest
  @CsvFileSource(resources = "/clientes_data_invalida.csv", numLinesToSkip = 1)
  void create_Should_RetornarMensajeDataInvalida_When_ClienteConDataInvalida(String nombre, 
      String tipoDocumento, String nroDocumento) {
    // test case
    Cliente cliente = new Cliente();
    cliente.setNombre(nombre);
    cliente.setNroDocumento(nroDocumento);
    try {
      cliente.setTipoDocumento(TipoDocumento.valueOf(tipoDocumento));
    } catch (IllegalArgumentException e) {
      cliente.setTipoDocumento(null);
    }
    
    HttpEntity<Cliente> request = new HttpEntity<>(cliente, headers);
    Response<List<Cliente>> response = restTemplate.postForObject(url, request, Response.class);
    
    // validation
    assertEquals(Message.DATA_INVALIDA.getCodigo(), response.getCodigo());
    assertEquals(Message.DATA_INVALIDA.getTexto(), response.getMensaje());
  }
  

  //------------ update---------------------------------------
  
  @ParameterizedTest
  @CsvFileSource(resources = "/clientes_data_invalida.csv", numLinesToSkip = 1)
  void update_Should_RetornarBadRequestAndMensajeDataInvalida_When_DataInvalida(String nombre, 
      String tipoDocumento, String nroDocumento) {
    // test case
    int codCliente = new Random().nextInt(1004 - 1001) + 1001;
    Cliente cliente = restTemplate.exchange(url + "/" + codCliente, HttpMethod.GET, null, 
        new ParameterizedTypeReference<Response<Cliente>>() {}).getBody().getData();
    assertNotNull(cliente);
    cliente.setNombre(nombre);
    cliente.setNroDocumento(nroDocumento);
    try {
      cliente.setTipoDocumento(TipoDocumento.valueOf(tipoDocumento));
    } catch (IllegalArgumentException e) {
      cliente.setTipoDocumento(null);
    }    
    HttpEntity<Cliente> request = new HttpEntity<>(cliente, headers);
    ResponseEntity<Response<Cliente>> response = restTemplate.exchange(url + "/" + codCliente, 
        HttpMethod.PUT, request, new ParameterizedTypeReference<Response<Cliente>>() {});
    
    // validation
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(Message.DATA_INVALIDA.getCodigo(), response.getBody().getCodigo());
    assertEquals(Message.DATA_INVALIDA.getTexto(), response.getBody().getMensaje());
    
  }
  
  @ParameterizedTest
  @CsvFileSource(resources = "/clientes_update_input_output.csv", numLinesToSkip = 1, 
      delimiter = ';')  
  void update_Should_RetornarResponseDeCliente_When_DataEsValida(Integer codCliente, 
      String clienteJson, String expectedOutput) throws JSONException {
    // test case
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> request = new HttpEntity<>(clienteJson, headers);
    ResponseEntity<String> response = restTemplate.exchange(url + "/" + codCliente, 
        HttpMethod.PUT, request, String.class);
    
    JSONAssert.assertEquals(expectedOutput, response.getBody(), JSONCompareMode.STRICT);
    
    
  }


}