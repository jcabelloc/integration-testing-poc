package pe.itana.integration.testing.poc.utils;

public enum Message {
  
  OK(2000, "Ok"),
  DATA_INVALIDA(4000, "Data es invalida"),
  ERROR_NO_CONTROLADO(5000, "Se presento algun problema")
  ;
  
  private int codigo;
  
  private String texto;
  
  Message(int codigo, String texto) {
    this.codigo = codigo;
    this.texto = texto;
  }
  
  public int getCodigo() {
    return codigo;
  }
  
  public String getTexto() {
    return texto;
  }

}
