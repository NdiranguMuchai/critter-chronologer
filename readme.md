# Critter Chronologer
 
Critter Chronologer a `Software as a Service` application that provides a scheduling interface for a small business that takes care of animals. This `Spring Boot` project will allow users to create pets, owners, and employees, and then schedule events for employees to provide services for pets.
### Prerequisites
The following  should be installed in your system:
* [Java 8 or newer](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Git command line tool](https://help.github.com/articles/set-up-git)
* [MySQL Server 8](https://dev.mysql.com/downloads/mysql/) (or another standalone SQL instance)
* Alternately, you may wish to run MySQL in a `Docker` container, using [these instructions](https://hub.docker.com/_/mysql/).
* Your preferred IDE
    * Eclipse with the m2e plugin. Note: when m2e is available, there is an m2 icon in `Help -> About` dialog. If m2e is
      not there, just follow the install process here: https://www.eclipse.org/m2e/
    * [Spring Tools Suite](https://spring.io/tools) (STS)
    * IntelliJ IDEA
    * [VS Code](https://code.visualstudio.com)

## Database configuration
* A commented SQL command is included in the `application.properties` file to create the critter database
* Spring & Hibernate will create everything else for you

## Running Critter locally
Critter Chronologer is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line:


```
git clone https://github.com/NdiranguMuchai/critter-chronologer.git
cd critter-chronologer
./mvnw package
java -jar target/*.jar
```
You can then access critter chronologer here: http://localhost:8082/

Or you can run it from Maven directly using the Spring Boot Maven plugin. If you do this it will pick up changes that you make in the project immediately (changes to Java source files require a compile as well - most people use an IDE for this):

```
./mvnw spring-boot:run
```

### Postman
A Postman collection has been provided to assist with endpoint requests.

* Open Postman.
* Select the `Import` button.
* Import the file found in this repository under `src/main/resource/CritterChronologer.postman_collection.json`
* Expand the Critter Chronologer folder in postman.

Each entry in this collection contains information in its `Body` tab if necessary.

### API Documentation
Once you run the application you can get documentation for the endpoints here: http://localhost:8082/swagger-ui.html#/