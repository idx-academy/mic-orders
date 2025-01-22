FROM openjdk:17-jdk-slim

RUN mkdir -p /opt/app/build
WORKDIR /opt/app/build

COPY . ./
RUN ./mvnw -B -DskipTests -DskipITs clean package

WORKDIR /opt/app/build/orders-boot/target
EXPOSE 8080
ENTRYPOINT ["java", \
            "-XX:MaxRAMPercentage=80", \
            "-Dspring.profiles.active=secrets", \
            "-jar", \
            "orders-boot-*.jar"]