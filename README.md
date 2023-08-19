# Kafka_For_Message_Retrieval
##Project Description
This repository contains the implementation of a reusable REST API for retrieving paginated list of money account transactions for a given customer in an arbitrary calendar month. The API calculates the total credit and debit values at the current exchange rate for each transaction page. The transaction data is consumed from a Kafka topic.
## Project Structure
api-test is used to test the project deployment.\
Auth is used to handle authorization and authentication.\
Transaction is used to handle transaction retrieval from Kafka.\
Parent is used to manage the maven package version.\
project_detail.pdf is used to explain the detail implementation of the project
##Getting Started
To set up and run the e-Banking Portal REST API on your local machine, follow the steps below:
###Prerequisites
```
Java Development Kit (JDK) 8
Apache Maven
Kafka
Redis
```
You can run the project using Docker or locally
### How to run the project locally?
```
cd Parent
mvn clean install -DskipTests

#start Auth Microservice
cd ../Auth/target
java -jar Video_Project_Auth-0.0.1-SNAPSHOT.jar

#start with Transaction Microservice
cd ../../Transaction/target
java -jar Synpulse_Take_Home_Assessment.jar
```

### How to run the project in Docker?
```
cd Parent
mvn clean install -DskipTests

#start Auth Microservice
cd ../Auth
#build image
docker build -t auth_image .
#run image
docker run -p 3002:3002 auth_image

#start with Transaction Microservice
cd ../Transaction
#build image
docker build -t transaction_image .
#run image
docker run -p 3012:3012 transaction_image
```
Please make sure port number 3002 and 3012 is not being used before running the microservice


