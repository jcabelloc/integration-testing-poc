package pe.itana.integration.testing.poc;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

//@SpringBootTest(classes = IntegrationTestingPocApplication.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-integration-testing.properties")
class IntegrationTestingPocApplicationTests {

  @Test
  void contextLoads() {
    
  }

}
