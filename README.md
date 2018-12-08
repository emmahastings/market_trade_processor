# market_trade_processor
Backend developer challenge

### Project Description

The application is written in Java using Spring Boot.

It exposes a REST-API endpoint to which you can pass a request as described below. Sending a GET request will return user instructions. Sending a POST request will return total number of transactions processed so far and the total number for that user ID. It uses an in-memory H2 database.

All requests are passed to a controller which forks these out to a service containing the business logic. This service writes the supplied information to the in-memory database and then generates a response.



### Building this Project

Before building make sure you have [maven](http://maven.apache.org) installed. Clone the project and follow either option below.

```bash
git clone https://github.com/emmahastings/market_trade_processor.git
cd market_trade_processor/
mvn spring-boot:run
```

In addition you can build a jar file

```bash
git clone https://github.com/emmahastings/market_trade_processor.git
cd market_trade_processor/
java -jar target/market_trade_processor-1.0.0.jar
```
Application will be available at http://localhost:8080/

### Usage

```bash
curl -H "Content-Type: application/json" -X POST -d '{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP","amountSell": 1000, "amountBuy":747.10, "rate": 0.7471, "timePlaced" : "24-JAN-18 10:27:44", "originatingCountry" : "FR"}' localhost:8080
```
### Testing this Project

To run all tests use ...
```bash
git clone https://github.com/emmahastings/market_trade_processor.git
cd market_trade_processor/
mvn test
```

### Authors and Contributors
Emma Hastings (@emmahastings)
