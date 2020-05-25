FROM openjdk:8-jdk-alpine

EXPOSE 8082

ARG JAR_FILE=target/kafka-listener-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} kafka-listener-1.0-SNAPSHOT.jar
 
CMD ["java","-jar","/kafka-listener-1.0-SNAPSHOT.jar"]
