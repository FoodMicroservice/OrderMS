FROM eclipse-temurin:17-jre-alpine

WORKDIR /opt

ENV SERVER_PORT=9097

COPY target/*.jar /opt/app.jar

EXPOSE 9097

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]