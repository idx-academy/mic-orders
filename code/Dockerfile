FROM maven:3.8.5-openjdk-17 AS BUILDER

WORKDIR /opt/app/build
COPY . ./

RUN mvn -B -e clean install -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /opt/app
COPY --from=BUILDER /opt/app/build/orders-boot/target/orders-boot-*.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", \
#            "-XX:InitialRAMPercentage=80", \
#            "-XX:MinRAMPercentage=80", \
            "-XX:MaxRAMPercentage=80", \
            "-Dspring.profiles.active=secrets", \
            "-jar", \
            "app.jar"]
