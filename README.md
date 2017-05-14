[![Build Status](https://travis-ci.org/smpavlenko/currency-converting-manager.svg?branch=master)](https://travis-ci.org/smpavlenko/currency-converting-manager)
# Currency Converting Manager

Currency converting application which uses third party currency converter API (https://openexchangerates.org).
Application provides registration/login functionality and a main screen to get current exchange rates.
After the successful login application shows the last 5 queries of logged user.

**Technologies**
- Spring Boot, Spring Security, Spring Data, Mapstruct, Swagger
- H2 in memory database
- Maven as build tool

**Building the App**

1. Build

        mvn clean install

2. Start application

        mvn clean spring-boot:run

3. Go to

        http://localhost:8080