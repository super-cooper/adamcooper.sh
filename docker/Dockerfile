FROM gradle:jdk23-graal AS build

WORKDIR /build
COPY gradle.properties ./
COPY gradle/ gradle/
COPY *.gradle.kts ./
COPY apps/ apps/
COPY src/ src/

RUN gradle --info javaJar

FROM container-registry.oracle.com/graalvm/native-image:23 AS release

WORKDIR /www

COPY --from=build /build/build/libs/*.jar server.jar

ENTRYPOINT ["java", "-jar", "server.jar"]
