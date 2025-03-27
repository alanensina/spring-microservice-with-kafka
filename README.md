
# spring-microservice-with-kafka

This is a simple event-driven-architecture where the order-service-api sends a message to email-service using Kafka after an order has been placed. The email-service consumes this message and send an email to the user that placed the order.

## How to run the project

In the root of the project, there's a `docker-compose.yml` file, where you can start the Docker containers with Kafka and a Postgres SQL.

Run with: `docker compose up -d`

After that, go to email-service folder and find the file:

`email-service/src/main/resources/application.properties`

Set your Gmail and the App Password code. This is mandatory to setup the email-service be able to send emails using JavaMailSender.

With docker running, run both services: `order-service` and `email-service`

Access `http://localhost:8080/swagger-ui/index.html` and use the Swagger to send requests to `order-service-api` and have fun.

Feel free to improve the code and create new methods to manage the products, users and orders.
