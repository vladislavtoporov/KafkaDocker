FROM openjdk:8-jdk-alpine

EXPOSE 8081

ARG JAR_FILE=target/grpc-client-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} grpc-client-1.0-SNAPSHOT.jar
 
CMD ["java","-jar","/grpc-client-1.0-SNAPSHOT.jar"]
