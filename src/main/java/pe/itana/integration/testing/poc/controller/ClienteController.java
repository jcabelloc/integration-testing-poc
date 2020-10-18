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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
  public ResponseEntity<List<Cliente>> getClientes() {
    
    return new ResponseEntity<>(clienteService.findAll(), HttpStatus.OK);
    
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
      response.setCodigo(4001);
      response.setMensaje("Data enviada con problemas");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    } else {
      try {
        cliente = clienteService.create(cliente);
        response.setData(cliente);
        response.setCodigo(2001);
        return new ResponseEntity<>(response, HttpStatus.OK);
      } catch (MyException e) {
        logger.error(e.getMensaje(), e);
        response.setCodigo(4001);
        response.setMensaje(e.getMensaje());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
      } catch (Exception e) {
        logger.error(e.toString(), e);
        response.setCodigo(5001);
        response.setMensaje("Se presento algun problema");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        
      }
    }
  }
}
