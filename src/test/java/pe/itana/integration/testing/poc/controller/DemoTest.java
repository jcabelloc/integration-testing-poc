package pe.itana.integration.testing.poc.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import pe.itana.integration.testing.poc.AbstractContainerBaseTest;

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestPropertySource("/application-integration-testing.properties")
class DemoTest extends AbstractContainerBaseTest {
  
  @LocalServerPort
  private int port;

  @Test
  void test02() {
    System.out.println("POOOOOOOOOOOOOOOOOOOOOOOOOORT: " + port);
    assertEquals("dos", "dos");
  }
  
  
}
