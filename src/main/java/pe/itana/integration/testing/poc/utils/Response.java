package pe.itana.integration.testing.poc.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Response<T> {
  
  private Integer codigo;
  
  private String mensaje;
  
  private Message message;
  
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

  public String getMensaje() {
    return mensaje;
  }

  public List<String> getErrores() {
    return errores;
  }

  public void setErrores(List<String> errores) {
    this.errores = errores;
  }
  
  @JsonIgnore
  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.codigo = message.getCodigo();
    this.mensaje = message.getTexto();
    this.message = message;
  }
  
  
  
}
