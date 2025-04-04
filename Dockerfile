FROM openjdk:21-jdk-slim

COPY target/your-chat-api-0.0.1-SNAPSHOT.jar your_chat.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "your_chat.jar"]