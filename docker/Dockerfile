# Alpine Linux with OpenJDK JRE
FROM openjdk:21-jdk-slim

WORKDIR /opt/app

ARG JAR_FILE=business-users-management-v1-0.0.1-SNAPSHOT.jar

EXPOSE 9082

# Copy JAR file
COPY ${JAR_FILE} business-users-management-v1-0.0.1.jar

# Run the app
ENTRYPOINT ["java", "-jar", "business-users-management-v1-0.0.1.jar", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers", "-browser"]
