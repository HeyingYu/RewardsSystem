# RewardsSystem

## This code is only for Charter Communication Assessment. Not for commercial or personal use.

A Spring Boot project including Junit tests and data set. Description specified below:

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase. 
 
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction 
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
 
Given a record of every transaction during a three-month period, calculate the reward points earned for each customer per month and total.

## REST APIs

Customers (/api/customers): 

- create customer
- get all customers
- get customer by id:  /api/customers/{id}

Transactions (/api/transactions)

- create transaction
- get all transactions
- get certain transaction by transactionId: /api/transactions/{id}
- get all transactions for certain customer: /api/transactions/customer/{customerId}

Rewards (/api/rewards)

- calculate reward points for certain customer by month: /api/rewards/calculateRewards/{customerId}/{month}
- calculate all reward points for certain customer: /api/rewards/calculateRewards/{customerId}/total

## How to run the project:
From an IDE:
- Open the project in your preferred IDE (Eclipse, IntelliJ, etc.)

- Locate the RewardsSystemApplication.java class 

- Run the main method by right-clicking on the class and selecting "Run As > Java Application"

From the command line:
- Navigate to the root directory of the project (where the pom.xml file is located)
- Build the project by running the following command: `mvn clean install`
- Run the application by using the following command: `java -jar target/[jar-file-name].jar`

Using the Spring Boot Maven Plugin:
- Navigate to the root directory of the project (where the pom.xml file is located)
- Run the application using the following command: `mvn spring-boot:run`

## Tests

Junit tests are available for controller and service classes (customer, transaction, rewards). 

Below are instructions on how to run Junit tests.

From an IDE:
- Open the project in your preferred IDE (Eclipse, IntelliJ, etc.)
- Locate the JUnit test cases (classes annotated with @Test)
- Run the test cases by right-clicking on the class and selecting "Run As > JUnit Test"

From the command line:
- Navigate to the root directory of the project (where the pom.xml file is located)
- Run the test cases using the following command: mvn test

Using the Spring Boot Maven Plugin:
- Navigate to the root directory of the project (where the pom.xml file is located)

## Configuration

The following environment variables (found in application.properties) can be used to configure the application:

- `PORT`: The port number on which the application will run (defaults to 8091)
- `LOG_LEVEL`: The log level for the application (defaults to "debug")

## Built in dataset

All data is stored in the local SQL Server instance. 

In order to access the database:
- open database window in IDE
- Select MySQL 
- change configuration based on environment variables (found in application.properties)

## Exception Handling

Exception will be thrown if certain customer does not exist. Will be updated based on future need

## Logging 

Logging availabe in RewardsController.java class only for now. Will be updated based on future need



