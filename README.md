### Project Documentation
- [General](#general)
- [Project Structure](#project-structure)
- [Build and Run App](#build-and-run-app)
- [Spin up Locally Required Infrastructure](#spin-up-locally-required-infrastructure)
- [Run App](#run-app)
- [Run Karate Tests](#run-karate-tests)
- [Run Tests (Mutation + Code Coverage)](#run-tests-mutation--code-coverage)
- [Obtain JWT Locally](#obtain-jwt-locally)
- [References](#references)

### Prerequisites:
 - Java 17
 - Docker compose

### General
The project is organized in way of mix Hexagonal architecture + DDD + Clean architecture.

`code` - Java source code

`e2e` - Karate tests

#### Project structure:
- orders-api-domain - Business related module. Technologies agnostic. Interfaces or application services without dependency to any technologies.
- orders-api-rest - Web. Main ports. `api` folder - APIFirst approach. Implementation of autogenerated REST interfaces from openapi spec. 
- orders-api-infrastructure - DB, Queue, REST 3rd-parties. Secondary ports. `src/test/resources/compose` folder with docker compose to run locally required infrastructure such as DB, colors API, etc.
- orders-api-application - Implementation of business logic. Glue to wire primary ports and secondary.
- orders-api-rest-clients - Integration with 3rd-parties via REST. `api` folder in each submodule is used to generate client API. There is openapi spec file.
- orders-api-boot - SpringBoot specific module to wire everything together. Entry point to run app and specify configs for DB, REST clients, etc. Integration tests there.

#### Build and run app
```shell
cd code
```

```shell
mvn clean install
```

Options to enable/disable different test types:

- `-DskipTests` skips both unit and integration tests

- `-DskipUnitTests` skips unit tests but executes integration tests

- `-DskipIntegrationTests` skips integration tests but executes unit tests

<b>To run integration tests docker-compose should be up.</b>


##### Spin up locally required infrastructure
```shell
docker-compose -f ./orders-boot/src/test/resources/compose/docker-compose.yml up
```

##### Run app
```shell
java -jar -DLOCAL_PASSWORD=<LOCAL_PASSWORD> -Dspring.profiles.active=local ./code/orders-boot/target/orders-boot-*.jar
```
where `LOCAL_PASSWORD` is password applied on local env for all users by default which are specified in `auth.users` section in `application-local.yml`. 

By default, each time when app starts the keypair for JWT is generated. It means after restart app JWT should be generated. Later we add possibility to use static keys.

Swagger [http://localhost:8080/retail/swagger-ui/index.html](http://localhost:8080/retail/swagger-ui/index.html).

#### Run karate tests

```shell
cd e2e/karate
```

```shell
mvn clean verify -Dkarate.env=local 
```
It is possible to specify username/password via env vars which will be used by karate, defaults see in `config-local-secret.yml`

```shell
mvn clean verify -Dkarate.env=local -DUSERNAME=<TEST_USERNAME> -DPASSWORD=<TEST_PASSWORD>
```
where `TEST_USERNAME` and `TEST_PASSWORD` is username and password with which app started

#### Run tests (Mutation + Code coverage) 
```shell
mvn -B clean verify -DskipITs pitest:mutationCoverage pitest:report-aggregate-module
```
Mutation report is located `<ROOT_MODULE>/target/pit-reports/index.html` 

UT coverage is located `<ROOT_MODULE>/jacoco-report-aggregate/target/site/jacoco-aggregate/index.html`

To apply format for java and pom.xml run mvn with the `format-apply` profile
Example: `mvn clean install -DskipTests -P format-apply`

#### Obtain JWT locally
```shell
curl --location 'http://localhost:8080/retail/auth/token' \
--header 'Content-Type: application/json' \
--data '{
    "username": "<USERNAME>",
    "password":  "<PASSWORD>"
}'
```

### References

- [Use-case diagram](https://app.diagrams.net/#G17iCThtH58keC83T8SUX5Lpo9Oiipvetp#%7B%22pageId%22%3A%22c0BYFkauXTc5PcYzelpT%22%7D)
- [Domain model diagram](https://app.diagrams.net/#G1elOkc_kON3mlytmNawvccNE0cexzDkxE#%7B%22pageId%22%3A%22C5RBs43oDa-KdzZeNtuy%22%7D)
- [Frontend deployment](http://idxacademy.xyz/)
- [Backend deployment](http://api.idxacademy.xyz/retail)
- [Swagger link](http://api.idxacademy.xyz/retail/swagger-ui/index.html)
