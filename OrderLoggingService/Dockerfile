FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 5672
COPY target/OrderLoggingService-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]