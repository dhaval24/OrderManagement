FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
COPY target/OrderCreationService-0.0.1-SNAPSHOT.jar app.jar
COPY ./AI-Agent.xml AI-Agent.xml
COPY ./applicationinsights-agent-2.4.0-BETA-SNAPSHOT.jar applicationinsights-agent-2.4.0-BETA-SNAPSHOT.jar
COPY ./wait-for-it.sh wait-for-it.sh
COPY ./start.sh start.sh
RUN ["chmod", "+x", "/start.sh"]
ENTRYPOINT ["/start.sh"]