FROM maven:3-eclipse-temurin-17 AS builder

WORKDIR /usr/src/scnlp
COPY src src
COPY pom.xml pom.xml
RUN mvn -DskipTests=true clean package

FROM eclipse-temurin:21-jre

ENV TZ=Europe/Berlin
ENV USERNAME="admin"
ENV PASSWORD="changeme"

WORKDIR /usr/src/scnlp
COPY --from=builder /usr/src/scnlp/target/stanfordnlp-*.jar stanfordnlp.jar

EXPOSE 8080

ENTRYPOINT java -jar /usr/src/scnlp/stanfordnlp.jar
