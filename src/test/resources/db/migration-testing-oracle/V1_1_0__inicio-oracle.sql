/* Single line comment */
CREATE TABLE cliente (
  cod_cliente INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1006 INCREMENT BY 1) NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  tipo_documento VARCHAR(4) NOT NULL,
  nro_documento VARCHAR(20) NOT NULL,
  PRIMARY KEY(cod_cliente)
);


