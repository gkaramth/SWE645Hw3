FROM openjdk:17
COPY target/hw3-0.0.1-SNAPSHOT.jar hw3-0.0.1-SNAPSHOT.jar.original
EXPOSE 8080
CMD ["java", "-jar", "hw3-0.0.1-SNAPSHOT.jar"]