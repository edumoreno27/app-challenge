# app-challenge
Customer Satisfaction App Challenge v .1.1 

### Pre-requisitos para ejecutar el App

_Tener instalado Docker_

### Para realizar solo tests
```
$  mvn test -Dmaven.test.skip=false
```
### Paso para instalar y ejecutar la imagen **mongo**
_1) Instalar y ejecutar la imagen _

```
$  docker run -d -p 27017:27017 --name mongodb mongo
```

### Pasos para construir el app mediante el archivo Dockerfile
_1) Para crear la imagen_

```
$  mvn clean package dockerfile:build
```
_2) Instalar y ejecutar la imagen **api**_

```
$  docker run -d -p 8080:8080 --env MONGO_HOST=mongodb --link mongodb --name api edumoreno/api:1.1
```
_Verificar si todo está OK_

```
$  docker logs api
```
### Documentación en Swagger

_En la documentación se puede hacer pruebas del API._


[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)





