FROM arm64v8/eclipse-temurin:17-jre-ubi9-minimal

RUN addgroup -S executor && adduser -S executor -G executor

RUN mkdir -p /opt/app
RUN chown executor:executor /opt/app/
COPY orders-boot/target/orders-boot-*.jar /opt/app/app.jar

EXPOSE 8080
USER executor
WORKDIR /opt/app
ENTRYPOINT ["java", \
            "-XX:InitialRAMPercentage=80", \
            "-XX:MinRAMPercentage=80", \
            "-XX:MaxRAMPercentage=80", \
            "-jar", \
            "app.jar"]
