
FROM eclipse-temurin:17-jre-alpine


VOLUME /tmp

ARG JAR_FILE=build/libs/fs-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

EXPOSE 8080