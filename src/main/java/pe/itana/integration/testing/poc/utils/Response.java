package pe.itana.integration.testing.poc.utils;

import java.util.List;

public class Response<T> {
  
  private Integer codigo;
  
  private String mensaje;
  
  private List<String> errores;
  
  private T data;
  
  public Response() {
    
  }
  
  public Response(int codigo, String mensaje) {
    this.codigo = codigo;
    this.mensaje = mensaje;
  }
  
  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }


  public Integer getCodigo() {
    return codigo;
  }


  public void setCodigo(Integer codigo) {
    this.codigo = codigo;
  }


  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public List<String> getErrores() {
    return errores;
  }

  public void setErrores(List<String> errores) {
    this.errores = errores;
  }
  
}
