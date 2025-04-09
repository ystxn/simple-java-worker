# Simple Java Worker

This example demonstrates how to use the Orkes Java SDK with Spring Boot without Maven or Gradle.

## Orkes Dependencies
- conductor-client-4.0.6.jar
- conductor-client-spring-4.0.6.jar
- java-sdk-4.0.6.jar
- orkes-conductor-client-4.0.6.jar
- orkes-conductor-client-spring-4.0.6.jar

## Configuration
Replace the configuration in `application.yaml`
```yaml
conductor:
  server.url: https://your-cluster.orkes-conductor.io
  security.client:
    key-id: ${ORKES_KEY_ID}
    secret: ${ORKES_SECRET}
```

## Write workers
Use `Worker.java` as an example
```java
@WorkerTask(value = "name-of-task", threadCount = 10)
public OutputClass generateReport(InputClass inputs) {
    // Business Logic
    return new OutputClass();
}
```

## Launch

### 1 - Compile
```shell
javac -cp "libs/*" -d . src/main/java/com/example/Main.java
```

### 2 - Execute
```shell
java -cp "libs/*:./src/main/resources:." com.example.Main
```
