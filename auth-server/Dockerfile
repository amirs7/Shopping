FROM openjdk:11-jre-slim
ENV JAR_FILE=./target/auth-server-1.0-SNAPSHOT.jar
COPY $JAR_FILE app.jar
ENV SPRING_PROFILES_ACTIVE=docker
EXPOSE 80
ENTRYPOINT ["java","-jar","/app.jar"]