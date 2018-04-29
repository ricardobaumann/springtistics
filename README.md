# Springtistics

A service to collect and summarize statistics. 

## Running
- For gradle local run, exec `./gradlew bootRun` on root folder.
- To run on docker, exec `docker build -t <container_name> . && docker run -p 8080:8080 <container_name>` on root folder.
- Default port is 8080.

## Usage
- Endpoints are complaint to the requirements:
1. `GET /transactions` to fetch summary
2. `POST /traansactions` to send statistics, with `Content-Type=application/json` header

## Development notes
- Application is built with plain Java Spring Boot 2.0 and gradle. To build it on local, run `./gradlew build`.
-  To achieve better results on both endpoints, the summary calculation is made on a scheduled task inside
`TransactionService` class. The scheduler rate can be reduced if we need some more real-time data when fetching summary data.
- Load tests were made using https://gatling.io/, and both scripts and results are available on `src/test/resources`
- Project is buildable on travis CI through github integration. See `.travis.yml` for details.
- Have fun using it, and give me your feedback :)