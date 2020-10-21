package pe.itana.integration.testing.poc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.OracleContainer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-integration-testing.properties")
public abstract class AbstractContainerBaseTest {

  static final OracleContainer ORACLE_CONTAINER;

  static {
    ORACLE_CONTAINER = new OracleContainer()
          .withPassword("secreto")
          .withUsername("system");
    ORACLE_CONTAINER.start();
  }
  
  @DynamicPropertySource
  static void oracleProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", ORACLE_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.password", ORACLE_CONTAINER::getPassword);
    registry.add("spring.datasource.username", ORACLE_CONTAINER::getUsername);
  }
}