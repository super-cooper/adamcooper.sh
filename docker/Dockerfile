FROM gradle:jdk8 as build

WORKDIR /build
COPY gradle.properties ./
COPY gradle/ gradle/
COPY *.gradle.kts ./
COPY src/ src/

RUN gradle --info jar

FROM openjdk:8-slim as release

WORKDIR /www

COPY --from=build /build/build/libs/*.jar .

ENTRYPOINT java -jar *.jar
