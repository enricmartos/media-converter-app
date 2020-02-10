# media-converter-app

## Description

This REST API allows you to perform several conversions on images. It has been designed according to the Layered Architecture principles for better maintainability and understandability. The acceptance tests have been fully automated following a BDD approach with Cucumber. 

The application has been deployed with Heroku and you can try it out on https://media-converter-app.herokuapp.com/media-converter/documentation 

## Personal goals

- To implement a REST API using Spring Boot
- To build a service layer integrated with an external image conversion software [ImageMagick](https://imagemagick.org/)
- To understand the configuration and deployment of Spring Boot applications
- To add code analysis checks such as checkstyle and pmd
- To automate the functional tests with Cucumber
- To document and provide an easy way to test the API with Swagger
- To design the application according to the Layered Architecture principles
- To build a CI pipeline that tests and deploys the application to Docker Hub and Heroku

## Architectural approach

The project [media-converter-api](https://github.com/enricmartos/media-converter-api) defines the API endpoints along with the model, and it is added as an external dependency to the *web-layer*. 

Regarding the Layered Architecture approach, this Spring Boot has been splitted up into multiple sub-modules with Gradle. Each one of the sub-modules described below is a codebase that can be maintained and built separately from the other ones.

- *Parent module*: Contains all the sub-modules. The top-level *build.gradle* file configures build behavior that is shared accross all sub-modules.
- *configuration-layer*: Contains the actual Spring Boot application and any Spring Java Configuration that puts together the Spring application context. To create the application context, it needs access to the other modules, which each provides certain parts of the application. So, it depends on *service-layer* and *web-layer*. This sub-module is called infrastructure in other contexts.
- *service-layer*: Also known as *application-layer*, it implements use cases that orchestrate the domain model. 
- *web-layer*: It is the entry point of the application and is coupled to the communication protocol (HTTP). It implements the API contract endpoints declared in [media-converter-api](https://github.com/enricmartos/media-converter-api). It may also call the use cases implemented in the *service-layer*, so it depends on it.
- *functional-tests*: Contains the acceptance tests implemented with Cucumber and Gherkin.


## Core technologies

*Back-end*
- Spring Boot

*Unit Testing*
- JUnit
- Mockito

*Acceptance Testing*
- Cucumber 
- Guerkin

*Dependency management tool*
- Gradle

*Containerization*
- Docker-compose

*Continuous Integration*
- GitHub Actions

*Cloud platform*
- Heroku

*Documentation*
- Swagger

## Build setup

### With Docker

- Clone this repo to your local machine. Docker-compose version must above 1.18 [Upgrade docker-compose to the latest version](https://stackoverflow.com/questions/49839028/how-to-upgrade-docker-compose-to-latest-version). Don't forget to restart your shell after performing all the steps.
```
# Start docker-compose

$ docker-compose -f docker-compose-debug.yaml up
```

- Open your browser and test the application on *localhost:8086/media-converter/documentation*

## Usage

### With an API client

- Postman or any other API client must be already installed in your machine. Otherwise, you will have to install them. You can perform the requests below in order to test the application. They belong to *Media converter* collection, which can be easily imported to Postman with [this](https://www.getpostman.com/collections/e0b47002f4d7633b7069) shareable link. 

| Action | HTTP request method | Endpoint | Header param | Body (form-data) |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| Resize an image to a given width and height | POST  | **media-converter/api/v1/image/resize** | apiKey | selectedFile, width, height |
| Auto-rotate an image | POST  | **media-converter/api/v1/image/autorotate** | apiKey | selectedFile |

### Run accceptance tests

- First of all, start the application in a separate console. In order to run the acceptance tests from the IDE console, execute the commands detailed below
```
# Go to functional-tests sub-module

$ cd functional-tests

# Comment out the snipptet in build.gradle, which avoids the tests execution

test {
    exclude '**/*'
}

# Build sub-module with gradle wrapper

$ ./gradlew build
```


## References

I have accomplished the aforementioned goals thanks to the following resources:
- [Building a Multi-Module Spring Boot Application with Gradle](https://reflectoring.io/spring-boot-gradle-multi-module/)
- [How to add Swagger to Spring Boot](https://www.youtube.com/watch?v=gduKpLW_vdY)
- [How to configure Swagger in Spring Boot](https://www.youtube.com/watch?v=8s9I1G4tXhA)
