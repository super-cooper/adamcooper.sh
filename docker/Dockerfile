FROM gradle:jdk16 as build

WORKDIR /build
COPY gradle.properties ./
COPY gradle/ gradle/
COPY *.gradle.kts ./
COPY apps/ apps/
COPY src/ src/

RUN gradle --info jar

FROM openjdk:16-slim as release

WORKDIR /www

COPY --from=build /build/build/libs/*.jar server.jar

ENTRYPOINT ["java", "-jar", "server.jar"]
