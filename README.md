# Notas de la Prueba de Concepto de Integration Testing

### Creacion del proyecto con Sprint Boot Starter


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
* OJO: Que levante la BD toma alrededor de 3 minutos

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

## Uso de Testcontainers
* Referencia: https://www.testcontainers.org/

### Para correr testcontainers desde el IDE, agregar la variable de entorno al ide
```
PATH = $PATH:/usr/local/bin
```

### Nota sobre Testcontainers
```
Adding this Testcontainers library JAR will not automatically add a database driver JAR to your project. 
You should ensure that your project also has a suitable database driver as a dependency.
```

### Example of creating an image with pre-built DB

* https://github.com/oracle/docker-images/blob/master/OracleDatabase/SingleInstance/samples/prebuiltdb/README.md
```

docker run --name oracledb -p 1521:1521 -p 5500:5500 oracle/database:18.4.0-xe

docker exec oracledb ./setPassword.sh secreto

docker stop -t 120 oracledb

docker commit -m "Image with prebuilt database" oracledb jcabelloc/oracledb-prebuilt:18.4.0-xe

docker rm oracledb



```

* Probar la imagen creada (opcional)
```
docker run --name oracledb -p 1521:1521 -p 5500:5500  jcabelloc/oracledb-prebuilt:18.4.0-xe

```

