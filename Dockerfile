FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} hrm-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/hrm-backend-0.0.1-SNAPSHOT.jar"]