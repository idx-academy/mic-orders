FROM openjdk:17-jdk-slim

RUN mkdir -p /opt/app/build
WORKDIR /opt/app/build

COPY . ./
RUN ./mvnw -B -DskipTests -DskipITs clean package

EXPOSE 8080
ENTRYPOINT ["java", \
            "-XX:MaxRAMPercentage=80", \
            "-Dspring.profiles.active=secrets", \
            "-jar", \
            "/opt/app/build/orders-boot/target/orders-boot-*.jar"]