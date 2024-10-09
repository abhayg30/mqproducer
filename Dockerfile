FROM openjdk:21
COPY target/*.jar mqproducer.jar
ENTRYPOINT ["java", "-jar", "/mqproducer.jar"]