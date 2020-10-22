package pe.itana.integration.testing.poc.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
import pe.itana.integration.testing.poc.AbstractContainerBaseTest;

class DemoTest extends AbstractContainerBaseTest {
  
  @LocalServerPort
  private int port;

  @Test
  void test99() {
    assertEquals("99", "99");
  }
  
  
}
