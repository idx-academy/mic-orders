FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . ./
RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install
CMD ["sh", "-c", "java -XX:MaxRAMPercentage=80 -Dspring.profiles.active=secrets -jar orders-boot/target/orders-boot-*.jar"]
