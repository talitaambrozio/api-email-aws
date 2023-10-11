#
# Build stage
#

FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#

FROM openjdk:17


WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar
SHELL ["/bin/sh", "-c"]
EXPOSE 8087

ENTRYPOINT ["java", "-jar","/app/app.jar"]
