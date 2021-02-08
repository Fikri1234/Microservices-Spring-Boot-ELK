# Microservices-Spring-Boot-ELK
Spring boot microservices with Authentication and centralized log with ELK

## Prerequisites
1. Java 8
2. JPA Mysql
3. Mongo DB
4. Spring Boot Actuator 
5. Dev Tools 
6. Lombok 
7. Eureka
8. Zuul
9. Zipkin
10. Sleuth
11. JWT
12. Elasticsearch-Logstash-Kibana

## Setup MySQL
Import dump-app_db.sql to your MySQL

## Architecture
![Alt text](asset/architecture.jpg?raw=true "Architecture")

Login to get bearer token from the authentication endpoint /auth/login . Then send a request to access the protected microservices.
Zuul Service act like Gateway which does central authentication, redirect an incoming request to other microservices.

## Eureka Server
Holds the information about all client-service applications.

## Common Service
Configure jwt variable

## Authentication Service
Validate user credential with phoneNumber or/and email and if validate then generate token, otherwise throw exception

## User Service
Create microservices that responsible to handle CRUD operation of user.

## Product Service
Create microservices that responsible to handle CRUD operation of product.

## Zuul Service
Create Gateway-Service (Zuul proxy) Application ,register it in Eureka server, validate token, and centralize authentication incoming request.

## Elasticsearch-Logstash-Kibana
Collect and centralized logging in three open source project for analysis in various environments, search indexes, visualize data with charts and graphs 

## Zipkin
Measure where service has spent more time.

## Deployment Strategy with Docker

### Interact container with each other using Docker Networking
Create Docker Networking to allow multiple containers to communicate with each other.
```
docker network create interact-spring-boot-services
```

### Build image for each microservices
**1. Eureka Service**
```
mvn clean install package -DskipTests
docker image build -t eureka-service --rm=true .
```

**2. Common Service**
```
mvn clean install package -DskipTests
docker image build -t commons-service --rm=true .
```

**3. Authentication Service**
```
mvn clean install package -DskipTests
docker image build -t authentication-service --rm=true .
```

**4. User Service**
```
mvn clean install package -DskipTests
docker image build -t master-user-service --rm=true .
```

**5. Product Service**
```
mvn clean install package -DskipTests
docker image build -t master-product-service --rm=true .
```

**6. Zuul Service**
```
mvn clean install package -DskipTests
docker image build -t zuul-service --rm=true .
```

### Create and run container using Docker Compose
```docker-compose up --build```

## Sample Payload
1. [Login request](asset/login.PNG)
2. [Register User](asset/register_user.PNG)
3. [Update User](asset/update_user.PNG)
4. [Get User By Id](asset/login.PNG)
5. [Get Product By Id](asset/get_product_by_id.PNG)
6. [Collect log by ELK](asset/elk_log.PNG)
7. [Measure request with Zipkin](asset/zipkin_log.PNG)

## JMeter Performace Testing
1. [Add Thread Group](asset/jmeter_create.PNG)
2. [Thread Group Number of Users Login](asset/jmeter_number_user.PNG)
3. [Http Request Body](asset/jmeter_req.PNG)
4. [View Results Tree](asset/jmeter_result.PNG)
5. [Duration Assertion 1000ms](asset/jmeter_duration.PNG)
6. [Http Header](asset/jmeter_header.PNG)
7. [Graph Results](asset/jmeter_graph.PNG)
