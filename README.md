# inter-systems-poc

Simple spring-boot application which tests connection with InterSystems IRIS DB

### Prerequisites
* JDK 21
* Docker

### Technological stack
* Java 21
* Gradle
* Spring-Boot 3.4.1
* Liquibase
* Spock

# Getting Started

1. Start local docker image with IRIS DB
```bash
docker run --name iris -d --publish 1972:1972 --publish 52773:52773 containers.intersystems.com/intersystems/iris-community:2024.3
```

2. Go into `http://localhost:52773/csp/sys/UtilHome.csp` and login with `SuperUser\SYS` account in order to change default password
3. Change password in `application.properties` file
4. Build and run app
5. Go into `http://localhost:8080/swagger-ui/index.html` in order to test and use endpoints 
