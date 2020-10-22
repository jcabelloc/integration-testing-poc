package pe.itana.integration.testing.poc;



import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;

class IntegrationTestingPocApplicationTests extends AbstractContainerBaseTest {
  
  @LocalServerPort
  private int port;
  
  @Test
  void test01() {
    assertThat(port).isPositive();
  }
  
  @Test
  void contextLoads() {
    
  }
  


}
