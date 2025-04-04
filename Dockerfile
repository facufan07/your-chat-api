FROM maven:3.9.6-eclipse-temurin-21

RUN mvn clean package -DskipTests

COPY target/your-chat-api-0.0.1-SNAPSHOT.jar your_chat.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/your_chat.jar"]