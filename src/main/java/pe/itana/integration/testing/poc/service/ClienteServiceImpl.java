package pe.itana.integration.testing.poc.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import pe.itana.integration.testing.poc.dto.ClienteDto;
import pe.itana.integration.testing.poc.entity.Cliente;
import pe.itana.integration.testing.poc.repository.ClienteRepository;
import pe.itana.integration.testing.poc.utils.MyException;



@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {
  
  
  private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

  @Autowired
  ClienteRepository clienteRepository;
  
  @Override
  public Cliente findById(Integer id) {
    logger.info("Obteniendo cliente con id {}", id);
    Optional<Cliente> opt = clienteRepository.findById(id);
    Cliente cliente = null;
    if (opt.isPresent()) {
      cliente = opt.get();
    }
    return cliente;
  }
  
  @Override
  public Cliente create(Cliente cliente) {
    logger.info("Creando al cliente {}", cliente);
    if (cliente.getCodCliente() != null) {
      throw new MyException("Codigo del cliente debe ser nulo");
    }
    
    Cliente cli = new Cliente(cliente.getNroDocumento());
    if (clienteRepository.exists(Example.of(cli))) {
      throw new MyException("Nro. de Documento Duplicado");
    }

    cliente = clienteRepository.save(cliente);

    return cliente;
  }
  
  @Override
  public Cliente update(Cliente cliente) {
    logger.info("Actualizando al cliente {}", cliente);

    if (!clienteRepository.existsById(cliente.getCodCliente())) {
      throw new MyException("No existe cliente con dicho codigo");
    }
    
    Cliente cli = new Cliente(cliente.getNroDocumento());
    Example<Cliente> exampleCli = Example.of(cli);
    clienteRepository.findAll(exampleCli)
        .forEach(e -> {
          if (!e.getCodCliente().equals(cliente.getCodCliente())) {
            throw new MyException("El nroDocumento ya esta asignado a otro cliente");
          }
        });
    return clienteRepository.save(cliente);
  }
  
  @Override
  public List<Cliente> findAll() {
    logger.info("Obteniendo todos los clientes");

    return clienteRepository.findAll();
  }

  @Override
  public List<ClienteDto> findByNombreStartingWith(String primerNombre) {
    logger.info("Obteniendo clientes cuyo nombre inicie con {}", primerNombre);

    return clienteRepository.findByNombreStartingWith(primerNombre);
  }
  
  
  
}

