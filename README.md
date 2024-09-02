# Weather Data System

![Architectural_Diagram](arc.jpg)

## Overview

This project is a Spring Boot application that ingests weather data from the Weatherstack API, processes and stores it
in a database, and provides an authenticated REST API for accessing the data. The system can enrich the dataset using AI
models to predict future weather conditions or analyze patterns related to climate change.

## Features

- **Data Ingestion**: Fetches weather data from the Weatherstack API at regular intervals.
- **Data Transformation**: Processes the incoming weather data and applies custom business logic.
- **Data Storage**: Stores unique weather records in a relational database.
- **REST API**: Provides authenticated access to the stored weather data.
- **Data Delivery**: Sends processed data increments to a remote location.
- **Error Handling & Backfill**: Manages data corruption and automatically fills any gaps in the data.
- **AI Enrichment** (Optional): Enhances the dataset with machine learning models to predict future weather conditions
  or analyze patterns.

## Project Structure

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── weatherreport
│   │   │           └── weatherdata
│   │   ├── resources
│   │   │   └── application.properties
├── README.md
├── pom.xml
└── docker-compose.yml
```

## Prerequisites

- **Java 17**
- **Maven 3.6+**
- **MySQL/PostgreSQL** (or any other relational database)
- **Docker** (Optional, for containerization)
- **Weatherstack API Key**: Obtain from [Weatherstack](https://weatherstack.com/).

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/phoenixtype/WeatherReport.git
cd WeatherReport
```

### 2. Configure the Application

Update the `application.properties` file with your specific configuration:

```properties
# Weatherstack API configuration
weatherstack.api.key=YOUR_API_KEY
weatherstack.api.url=http://api.weatherstack.com/current
# Database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/weatherdata
spring.datasource.username=root
spring.datasource.password=yourpassword
# Other configurations
spring.jpa.hibernate.ddl-auto=update
spring.security.user.name=admin
spring.security.user.password=password
```

### 3. Build and Run the Application

You can run the application using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

Or, using Docker if you have a `docker-compose.yml` file:

```bash
docker-compose up --build
```

### 4. API Endpoints

- **GET /api/weather**: Retrieve all weather records (authenticated).
- **GET /api/weather/{id}**: Retrieve a specific weather record by ID (authenticated).
- **POST /api/weather**: Ingest new weather data (requires API key).

Authentication is required for all endpoints. The default username and password are defined in `application.properties`.

### 5. AI Enrichment (Optional)

To enable AI enrichment, integrate a machine learning model of your choice. This can be done by adding a service that
processes the weather data and enhances it with predictions or pattern analysis.

### 6. Error Handling & Backfill

The application includes built-in error handling and will attempt to backfill any missing data in case of an ingestion
failure.

## Development Process

- **Phase 1**: Set up project and basic API consumption from Weatherstack.
- **Phase 2**: Implement data transformation, storage, and API services.
- **Phase 3**: Add AI enrichment and error handling mechanisms.
- **Phase 4**: Finalize the system, conduct testing, and deploy.

## Contributing

If you want to contribute to this project, please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any inquiries, please contact `basil.ikpe@gmail.com`.
