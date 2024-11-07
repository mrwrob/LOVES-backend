FROM maven:3.8.6-openjdk-18-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11-oracle
COPY --from=build /usr/src/app/target/loves-0.0.1-SNAPSHOT.jar /usr/app/loves.jar
ENV TZ Europe/Berlin
RUN mkdir -p ./uploads
ENTRYPOINT ["java", "-jar", "/usr/app/loves.jar"]
