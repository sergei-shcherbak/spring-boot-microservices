# spring-boot-microservices
It's a demo of spring boot and microservices.    
The second goal is to implement bulk operations in a restful API.    
There is some difference between bulk operations and batch operations.    
[As a basis, this concept has been used.  ](https://codeburst.io/bulk-operations-in-a-restful-api-67a0db237e1b)

### what has been done

- requests validation
- error handling
- rest API documentation
- docker and multi-container
- basic testing

### what can be improved

- security
- logging
- service discovery
- configuration management
- monitoring and alerting

# How to run
This project uses [maven wrapper](https://github.com/takari/maven-wrapper).    
Also, docker-compose is used for running cluster.    
Requirements for running these applications are:

- **Running docker daemon**
- **Docker compose**



Run the **Maven** command:

Windows



    mvnw.cmd package jib:dockerBuild && docker-compose up  


Unix



    ./mvnw package jib:dockerBuild && docker-compose up  


# How to use
This project uses [Swagger API documentation](https://github.com/swagger-api).
After the deploying stage on your local machine,    
the swagger endpoint should be available for running rest commands.    
The default address is http://localhost:1111/swagger-ui/