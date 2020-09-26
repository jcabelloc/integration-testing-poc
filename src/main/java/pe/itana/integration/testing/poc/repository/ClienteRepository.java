package pe.itana.integration.testing.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.itana.integration.testing.poc.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
