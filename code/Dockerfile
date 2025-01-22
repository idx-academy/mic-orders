FROM eclipse-temurin:17-jre-alpine
#FROM openjdk:17-jdk-slim

#WORKDIR /app
#COPY . ./
#CMD ["sh", "-c", "java -jar target/dummy-data-service-*.jar"]

RUN addgroup -S executor && adduser -S executor -G executor

RUN mkdir -p /opt/app
RUN chown executor:executor /opt/app/
WORKDIR /opt/app
COPY . ./
RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install
COPY orders-boot/target/orders-boot-*.jar /opt/app/app.jar

EXPOSE 8080
USER executor
ENTRYPOINT ["java", \
#            "-XX:InitialRAMPercentage=80", \
#            "-XX:MinRAMPercentage=80", \
            "-XX:MaxRAMPercentage=80", \
            "-Dspring.profiles.active=secrets", \
            "-jar", \
            "app.jar"]