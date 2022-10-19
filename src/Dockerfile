FROM openjdk:11-jre-slim

ADD challenge-*.jar /app/challenge.jar


WORKDIR /app

ENV APP_NAME nsw-revenue-api

ENV JAVA_APP_JAR challenge.jar
ENV JAVA_OPTIONS "-Dhttp.port=8080 "
