#Conference Track Management

### Tech stack
* Java 1.8+
* Gradle as dependency management tool
* Log4j for logging dependencies
* JUnit as tool for Unit tests.

### How to Build

```bash
cd /path/to/project
gradle build
```

This build task compiles the code, builds a jar file in `build/libs` directory, and executes the
tests.

If you want to change the environment, you can pass -Penv=[ENVIRONMENT PREFIX] to load the appropriate application properties file. By default, will be loaded application-dev.properties.

### How to Run the Program

After `gradle build`:

```bash
java -jar /path/to/conference-mgmt-api-1.0.0.jar /path/to/input_file
```

### Example of use

* The `Conference` class provides a constructor as an API to create a
  `Conference` object representing a scheduled conference with tracks for the provided input
* To schedule the conference, this class currently uses the first-fit algorithm when adding the
  events.
* Example of use:
```java
BufferedReader reader = new BufferedReader(new FileReader("/path/to/input_file"));
Conference conference = new Conference(reader);
System.out.println(conference);
```