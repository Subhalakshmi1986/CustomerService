![example workflow](https://github.com/Subhalakshmi1986/CustomerService/actions/workflows/gradle.yml/badge.svg)

# Customer Service 
    API to view and manage customer phonenumbers.
## Problem statement
[Belong problem statement]("Belong Code Test - Backend Engineer.pdf")
## API end points exposed

- "GET <baseurl>/customers/{customerId}/phonenumbers" - Endpoint to retrieve phonenumbers of customers
- "GET <baseurl>/phonenumbers" - Endpoint to get all phonenumbers
- "GET <baseurl>/phonenumbers/{phonenumberId}" - Endpoint to get phonenumber by Id.
- "PATCH <baseurl>/phonenumbers/{phonenumberId}" - Endpoint to activate or deactivate a phonenumber

## Project Build and Execution
 - To build projects
   - `./gradlew clean build`
 - To build, test, checkstyle, spotbugs, dependencyCheckAnalyze
   - `./gradlew clean check`
 - After running check task - Test/ Code / Checkstyle/ Spotbugs/ Dependencycheck reports can  be found in the below folders:
   - `./build/reports`
 - To run the springboot project
   - `./gradlew bootRun`

## Entity Relationship 
    One to Many Relationship between Customer and Phonenumber Entity -  Dao and API models maintained separately.

## Consumer driven contract first approach
    Openapi contract can found [here](config/openapi/customer-openapi.yaml)

## Tool to generate openapi models
    openapigenerator tool is used to generate model runtime from contracts

## API specifications  and implementations are driven openapi contracts and can be viewed by executing below steps:
-`./gradlew bootRun`
- http://localhost:8080/v3/api-docs.yaml - Access this to view openapi.yaml
- http://localhost:8080/swagger-ui/index.html - Access this endpoint to view swagger-ui

## Datasource
- In memory H2 DB used for the case of the challenge
- Data initialisation during application startup (src/main/resources/data.sql)
``
## Production Ready features
- Actuator endpoints for health check
  - http://localhost:8080/actuator/health/customhealth
- Spring cloud sleuth with local baggage fields like customerId and phonenumberId logged for easy tracing
- Alongside traceId, spanId  - customerId and phonenumberId for enhancing observability.

## Some sample endpoints to test
- `./gradlew bootRun`
- `GET http://localhost:8080/phonenumbers`
- `GET http://localhost:8080/phonenumbers/469324654`
- `GET http://localhost:8080/customers/123/phonenumbers`
- `PATCH http://localhost:8080/phonenumbers/469324645` with body `{ "status" : "INACTIVE" }`

