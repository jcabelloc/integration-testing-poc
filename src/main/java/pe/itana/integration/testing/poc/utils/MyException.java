package pe.itana.integration.testing.poc.utils;


public class MyException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;

  
  private final String mensaje;
  

  public MyException(String mensaje) {
    this.mensaje = mensaje;
  }


  public String getMensaje() {
    return mensaje;
  }
  
}
