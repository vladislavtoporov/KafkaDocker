FROM openjdk:8-jdk-alpine

EXPOSE 8080 5051

ARG JAR_FILE=target/grpc-server-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} grpc-server-1.0-SNAPSHOT.jar
 
CMD ["java","-jar","/grpc-server-1.0-SNAPSHOT.jar"]
