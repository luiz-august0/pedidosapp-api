# Stage 1: Build
FROM maven:3.9.5-eclipse-temurin-21-alpine as builder
WORKDIR /workdir/server
COPY pom.xml /workdir/server/pom.xml
RUN mvn dependency:go-offline

COPY src /workdir/server/src
RUN mvn install

# Stage 2: Prepare for production
FROM builder as prepare-production
RUN mkdir -p target/dependency
WORKDIR /workdir/server/target/dependency
RUN jar -xf ../*.jar

# Stage 3: Runtime
FROM openjdk:22-ea-21-jdk

EXPOSE 8080
VOLUME /tmp
ARG DEPENDENCY=/workdir/server/target/dependency
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=prepare-production ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/classes /app

# Defines profile
ENV SPRING_PROFILES_ACTIVE=$PEDIDOSAPP_PROFILE
ENV PEDIDOSAPP_PG_URL=$PEDIDOSAPP_PG_URL
ENV PEDIDOSAPP_PG_DATABASE=$PEDIDOSAPP_PG_DATABASE
ENV PEDIDOSAPP_PG_USERNAME=$PEDIDOSAPP_PG_USERNAME
ENV PEDIDOSAPP_PG_PASSWORD=$PEDIDOSAPP_PG_PASSWORD

ENTRYPOINT ["java","-cp","app:app/lib/*","com.pedidosapp.api"]