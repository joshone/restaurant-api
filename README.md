# restaurant-api for technical test

Este repo es para subir el test técnico para evaluación

## Pre-requisitos: 

1. java 8
2. [Apache Maven](https://maven.apache.org/)
2. [Apache ActiveMQ](http://activemq.apache.org/)

## Pasos de utilización de ActiveMQ para manejo de JMS

1. Descargar [Apache ActiveMQ](http://activemq.apache.org/)
2. Descomprimir archivo y ejecutarlo: 

	- si es windows activemq.exe start
	- si es linux/mac (revisar permisos de ejecución) ./activemq start
	
3. [Manager de ActiveMQ](http://localhost:8161/)  y usar admin/admin por defecto


## Pasos para ejecutar la aplicación
- Clonar el proyecto:
```
git clone https://github.com/joshone/restaurant-api.git
```
- Compilar el proyecto:
```
mvn clean package
```
- Ejecutar el proyecto:
```
- opción 1: 
mvn spring-boot:run
```
```
- opción 2:
java -jar target/restaurant-api.jar
```

### credenciales ejemplo
estas credenciales se encuentran en el archivo application.yml en la siguiente ruta
```
src/main/resources/application.yml
```

	1. jose:alejandro
	2. roberto:cortes
	3. fernando:cortes
	4. carmen:magallanes
	5. jorge:salinas