package pe.itana.integration.testing.poc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.itana.integration.testing.poc.dto.ClienteDto;
import pe.itana.integration.testing.poc.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

  
  @Query("SELECT c.codCliente AS codCliente, c.nombre AS nombre FROM "
      + " Cliente c WHERE c.nombre LIKE :primerNombre% ")
  List<ClienteDto> findByNombreStartingWith(@Param("primerNombre") String primerNombre);
}
