### (RU)
# Monitoring system

## Описание проекта:
Система мониторинга, которая отслеживает работу различных компонентов приложения с помощью Spring Kafka.
Данная система включает в себя ZooKeeper для координации, Producer для отправки метрик, Consumer для их обработки и анализа, а также REST API для просмотра метрик.

## Архитектура

### Metrics Producer

- микросервис, который отслеживает и собирает метрики работы приложения и отправляет их в Kafka топик "metrics-topic".
Содержит REST API для взаимодействия с микросервисом (отправка метрик работы приложения в формате JSON).

#### REST API
URL: http://localhost:8081

- POST /metrics - отправить новые данные метрики.

#### Отправка метрики
Для отправки метрики в Kafka, сформируйте JSON со следующими полями:
```json
{
  "name": "Memory",
  "maxMemory": 4096.0,
  "usedMemory": 135.343,
  "createdAt": "2024-04-20T18:16:37"
}
```

Подробнее: http://localhost:8081/swagger-ui/index.html

### Metrics Consumer
- микросервис, который принимает метрики из Kafka топика "metrics-topic" и анализирует их для выявления проблем и трендов.
Предоставляет доступ к REST API для просмотра метрик.

### REST API
URL: http://localhost:8081

- GET /metrics/{id} - получить информацию о метрике по ее id.
- GET /metrics - получить список всех метрик.

Подробнее: http://localhost:8081/swagger-ui/index.html

## Масштабирование
Для масштабирования Kafka вы можете добавить дополнительные брокеры, обновив файл docker-compose.yml.

## Запуск приложения:
- установить актуальную версию Docker;
- клонировать проект в среду разработки;
- настроить подключение к базе данных в файле application.properties;
- запустить Zookeeper и Kafka в файле docker-compose.yml;
- запустить метод main в файле MonitoringSystemApplication.java

После этого будет доступен OpenAPI http://localhost:8081/swagger-ui/index.html.

## Технологии, используемые в проекте:
- Java 17;
- Spring Boot;
- Spring Data JPA;
- Spring Kafka;
- SpringDoc;
- Maven;
- REST API;
- Lombok;
- PostgreSQL;
- Liquibase.

### (EN)
# Monitoring system

## Project description:
A monitoring system that monitors the operation of various application components using Spring Kafka.
This system includes ZooKeeper for coordination, Producer for sending metrics, Consumer for processing 
and analyzing them, as well as a REST API for viewing metrics.

## Архитектура

### Metrics Producer

- a microservice that monitors and collects application performance metrics and sends them to the Kafka topic "metrics-topic".
  Contains a REST API for interacting with the microservice (sending application performance metrics in JSON format).

#### REST API
URL: http://localhost:8081

- POST /metrics - send new metric.

#### Sending a metric
To send the metric to Kafka, generate a JSON with the following fields:

```json
{
  "name": "Memory",
  "maxMemory": 4096.0,
  "usedMemory": 135.343,
  "createdAt": "2024-04-20T18:16:37"
}
```

More detailed: http://localhost:8081/swagger-ui/index.html

### Metrics Consumer
- a microservice that accepts metrics from the Kafka topic "metrics-topic"
and analyzes them to identify problems and trends. Provides access to the REST API for viewing metrics.

### REST API
URL: http://localhost:8081

- GET /metrics/{id} - get information about a metric by its id.
- GET /metrics - get a liat of all metric.

More detailed: http://localhost:8081/swagger-ui/index.html

## Scaling
To scale Kafka, you can add additional brokers by updating docker-compose.yml file.

## How to run an application:
- install the actual version of Docker;
- clone the project into the development environment;
- configure the connection to the database in the application.properties file;
- run Zookeeper and Kafka in the docker-compose file.yml;
- run the main method in the MonitoringSystemApplication.java file.

After doing that OpenAPI by this URL http://localhost:8081/swagger-ui/index.html.

## Technologies:
- Java 17;
- Spring Boot;
- Spring Data JPA;
- Spring Kafka;
- SpringDoc;
- Maven;
- REST API;
- Lombok;
- PostgreSQL;
- Liquibase.