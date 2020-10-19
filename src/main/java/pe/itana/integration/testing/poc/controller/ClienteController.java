package pe.itana.integration.testing.poc.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.itana.integration.testing.poc.dto.ClienteDto;
import pe.itana.integration.testing.poc.entity.Cliente;
import pe.itana.integration.testing.poc.service.ClienteService;
import pe.itana.integration.testing.poc.utils.MyException;
import pe.itana.integration.testing.poc.utils.Response;


@RestController
@RequestMapping("clientes")
public class ClienteController {
  
  private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

  @Autowired
  ClienteService clienteService;
  
  @GetMapping
  public ResponseEntity<Response<List<Cliente>>> getClientes() {
    logger.info("Obteniendo todos los clientes");
    Response<List<Cliente>> response = new Response<>();
    
    try {
      List<Cliente> clientes = clienteService.findAll();
      response.setData(clientes);
      response.setCodigo(2000);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      logger.error(e.toString(), e);
      response.setCodigo(5000);
      response.setMensaje("Se presento algun problema");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
  }
  
  @GetMapping("{codCliente}")
  public ResponseEntity<Response<Cliente>> findById(@PathVariable("codCliente") Integer codCliente) {
    logger.info("Buscando al cliente con id {}", codCliente);
    Response<Cliente> response = new Response<>();
    try {
      Cliente cliente = clienteService.findById(codCliente);
      response.setData(cliente);
      response.setCodigo(2000);
      return new ResponseEntity<>(response, HttpStatus.OK);

    } catch (Exception e) {
      logger.error(e.toString(), e);
      response.setCodigo(5000);
      response.setMensaje("Se presento algun problema");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @PutMapping("{codCliente}")
  public ResponseEntity<Response<Cliente>> update(@PathVariable Integer codCliente, 
      @Valid @RequestBody Cliente cliente, BindingResult result) {
    logger.info("Actualizando al cliente {}", cliente);
    Response<Cliente> response = new Response<>();    
    if (result.hasErrors()) {
      List<String> errors = result
          .getFieldErrors()
          .stream()
          .map(FieldError::getDefaultMessage)
          .collect(Collectors.toList());
      logger.info("Errores que se presenta {}", errors);
      response.setErrores(errors);
      response.setCodigo(4000);
      response.setMensaje("Data enviada con problemas");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    } else {
      try {
        cliente.setCodCliente(codCliente);
        cliente = clienteService.update(cliente);
        response.setData(cliente);
        response.setCodigo(2000);
        return new ResponseEntity<>(response, HttpStatus.OK);
      } catch (MyException e) {
        logger.error(e.getMensaje(), e);
        response.setCodigo(4000);
        response.setMensaje(e.getMensaje());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        response.setCodigo(5000);
        response.setMensaje("Se presento algun problema");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      
    }
 }
  
  @PostMapping
  public ResponseEntity<Response<Cliente>> create(@Valid @RequestBody Cliente cliente, 
      BindingResult result) {
    logger.info("Creando al cliente {}", cliente);
    Response<Cliente> response = new Response<>();

    if (result.hasErrors()) {
      List<String> errors = result
          .getFieldErrors()
          .stream()
          .map(FieldError::getDefaultMessage)
          .collect(Collectors.toList());
      logger.info("Errores que se presenta {}", errors);
      response.setErrores(errors);
      response.setCodigo(4000);
      response.setMensaje("Data enviada con problemas");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    } else {
      try {
        cliente = clienteService.create(cliente);
        response.setData(cliente);
        response.setCodigo(2000);
        return new ResponseEntity<>(response, HttpStatus.OK);
      } catch (MyException e) {
        logger.error(e.getMensaje(), e);
        response.setCodigo(4000);
        response.setMensaje(e.getMensaje());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        response.setCodigo(5000);
        response.setMensaje("Se presento algun problema");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
  }
  
  @GetMapping(path = "search", params = "nombreStartingWith")
  public ResponseEntity<Response<List<ClienteDto>>> findByNombreStartingWith(
      @RequestParam String nombreStartingWith) {
    logger.info("Buscando clientes que inicien nombre con {}", nombreStartingWith);
    Response<List<ClienteDto>> response = new Response<>();  
    try {
      List<ClienteDto> clientes = clienteService.findByNombreStartingWith(nombreStartingWith);
      response.setData(clientes);
      response.setCodigo(2000);
      return new ResponseEntity<>(response, HttpStatus.OK);

    } catch (Exception e) {
      logger.error(e.toString(), e);
      response.setCodigo(5000);
      response.setMensaje("Se presento algun problema");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
