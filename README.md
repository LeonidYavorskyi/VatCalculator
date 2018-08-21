
#### Invoice service
Contains general customer invoices logic: saving invoices and calculating customer balance.

Method | Path | Description
------------- | ------------------------- | ------------- |
GET	| /invoices/customers/{customerId}/balance| Calculate total VAT and total amount for specific customer 
PUT | /invoices | Save new invoice

#### API Gateway
It is a single entry point into the system, used to handle requests by routing them to the appropriate backend service or by invoking multiple backend services.

With Spring Cloud we can enable zuul-edge-service with one `@EnableZuulProxy` annotation. Zuul is used to route requests to appropriate 
microservices. Here's a simple prefix-based routing configuration for Invoice service:

```yml
zuul:
  routes:
    invoice-service:
        path: /vat/**
        serviceId: invoice-service
        stripPrefix: false
```

That means all requests starting with `/vat` will be routed to Invoice service. There is no hardcoded address. Zuul uses [Service 
discovery](https://github.com/sqshq/PiggyMetrics/blob/master/README.md#service-discovery) mechanism to locate Invoice service instances.


#### Service discovery
Client-side service discovery allows services to find and communicate with each other without hard coding hostname and port. The only ‘fixed point’ in such an architecture consists of a service registry with which each service has to register.

With Netflix Eureka each client can simultaneously act as a server, to replicate its status to a connected peer. In other words, a client retrieves a list of all connected peers of a service registry and makes all further requests to any other services through a load-balancing algorithm.

With Spring Boot, you can easily build Eureka Registry with `spring-cloud-starter-eureka-server` dependency, `@EnableEurekaServer` annotation and simple configuration properties.

Client support enabled with `@EnableDiscoveryClient` annotation an `bootstrap.yml` with application name:
``` yml
spring:
  application:
    name: invoice-service
```

Now, on application startup, it will register with Eureka Server and provide meta-data, such as host and port, health indicator URL, home page etc. Eureka receives heartbeat messages from each instance belonging to a service. If the heartbeat fails over a configurable timetable, the instance will be removed from the registry.

Also, Eureka provides a simple interface, where you can track running services and a number of available instances: `http://localhost:1111`

#### Running application
```bash
cd vat-calculator
mvn clean package 
java -jar registry/target/registry-0.0.1-SNAPSHOT.jar & 
java -jar gateway/target/gateway-0.0.1-SNAPSHOT.jar & 
java -jar invoice-service/target/invoice-service-0.0.1-SNAPSHOT.jar &
```

#### Functionality
To create a new invoice, send the following request:
```yml
    PUT http://127.0.0.1:4000/vat/invoices
```
with request body:
```json
{ 
 "customer_id" : 1234,
 "invoice_id": 1111, //must be unique for each request
 "amount": 124.55,
 "vat": 12.45
}
```
Response should be like this:
```json
{
    "id": 4,
    "amount": 124,
    "vat": 12,
    "invoice_id": 1111,
    "customer_id": 1234
}
```

All created invoices are stored in MySQL db (test_db) that will be automatically created during application startup and dropped after shutdown.  

To calculate total customer balance, send following request:
```yml
    GET http://127.0.0.1:4000/vat/invoices/customers/1234/balance
```
Response should be like this:
```json
{
    "customer_id": 1234,
    "total_amount": 372,
    "total_vat": 36
}
```