FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/oauth2-server.jar  app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
