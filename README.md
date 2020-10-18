# Notas de la Prueba de Concepto de Integration Testing

### Creacion del proyecto con Sprint Boot Starter


### DB
```
docker run -it -p 3306:3306 -e MYSQL_ROOT_PASSWORD=secreto -e MYSQL_DATABASE=app -e MYSQL_USER=app -e MYSQL_PASSWORD=secreto --name app mysql:5.7.27

```

### Oracle DB
Referencias:
* https://github.com/oracle/docker-images/tree/master/OracleDatabase/SingleInstance
* https://blogs.oracle.com/oraclemagazine/deliver-oracle-database-18c-express-edition-in-containers

* Clonar el repo de docker-images de Oracle
```
git clone https://github.com/oracle/docker-images.git
```

* Generar la imagen de la BD Express Edition 18.4.0

```
cd docker-images/OracleDatabase/SingleInstance/dockerfiles
./buildDockerImage.sh -v 18.4.0 -x
```
* Esperar ....
```
...
Successfully built a60d6628e3e3
Successfully tagged oracle/database:18.4.0-xe


  Oracle Database Docker Image for 'xe' version 18.4.0 is ready to be extended:

    --> oracle/database:18.4.0-xe

  Build completed in 1120 seconds.
```

* Verificar que la imagen ha sido generada
```
docker images
```

* Ejecutar el container
```
docker run --name oracledb \
    -d \
    -p 1521:1521 \
    -p 5500:5500 \
    -e ORACLE_PWD=secreto \
    -e ORACLE_CHARACTERSET=AL32UTF8 \
    oracle/database:18.4.0-xe

```

* Conectarse al container de la BD oracle
```
docker exec -it --user=oracle oracledb bash

[oracle@e269bce124bf /]$ . oraenv
ORACLE_SID = [XE] ? XE
```
* Conectarse a la BD con sys
```
sqlplus sys/secreto@XE as sysdba
```
* Conectase a la BD como system
```
sqlplus system/secreto@XE
```

Enter password:
SQL> sho con_name
CON_NAME
------------------------------
CDB$ROOT
```


* Queries en la BD
```
select "description", "version", "script", "installed_on", "success" from "flyway_schema_history";
drop table "flyway_schema_history";
```


