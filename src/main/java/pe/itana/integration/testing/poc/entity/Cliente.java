package pe.itana.integration.testing.poc.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Cliente implements Serializable  {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer codCliente;
  
  @NotNull(message = "El nombre no puede ser Nulo")
  @Size(min = 2, message = "El nombre debe tener al menos dos caracteres")
  @Column(nullable = false)
  private String nombre;
  
  @NotNull(message = "El tipoDocumento no puede ser Nulo")
  @Column
  @Enumerated(EnumType.STRING)
  private TipoDocumento tipoDocumento;
  
  @NotNull(message = "El nroDocumento no puede ser Nulo")
  @Size(min = 8, max = 11, message = "El nroDocumento debe tener entre 8 y 11 digitos")
  @Column(unique = true, length = 20)
  private String nroDocumento;
    
  public Cliente(String nroDocumento) {
    this.nroDocumento = nroDocumento;
  }
  
  public enum TipoDocumento {
    DNI, RUC
  }
  
  public Cliente withCodCliente(Integer codCliente) {
    setCodCliente(codCliente);
    return this;
  }

}
