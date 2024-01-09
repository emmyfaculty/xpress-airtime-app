## pay_xpress_task
The Airtime Purchase System is a Java-based application that facilitates the purchase of airtime products using a secure API. The system is designed to integrate with pay Xpress VTU api, with a focus on reliability, security, and scalability.
## Prerequisites
Before you begin, ensure you have the following installed:
Java 17 Maven Git
## Getting Starte
# Installation
Clone the repository git clone https://github.com/emmyfaculty/pay_xpress_task.git
Change into the project directory
cd xpress-airtime-app Configuration Ensure you configure the necessary API keys and datasource configuration in the application.properties file to enable seamless integration.
## Usage
To purchase airtime, use the provided API endpoint by sending a POST request in your postman. http://localhost:8080/api/airtime/purchase
payload { "requestId": 123344, "uniqueCode": "MTN_24207", "details": { "phoneNumber": "08033333333", "amount": 10000 } }
Response
{ "responseCode": "00", "responseMessage": "Successful", "walletInfo": null, "airtimeApiResponse": { "requestId": 123344, "referenceId": "EMMY28498124010812212023743047", "responseCode": null, "responseMessage": null, "data": { "channel": "API", "amount": 10000.0, "phoneNumber": "08033333333" } } }
## Tools Used:
Java Spring boot MySQL database Intellij IDE
## Project Summary:
The Airtime Payment System is a Java-based application designed to facilitate the purchase of airtime products through a secure API. The system allows users to make airtime transactions for specific telecom providers, with a primary focus on reliability, security, and scalability. Key functionalities of the project include:
Authentication using JWT: Implements an API for user authentication using JSON Web Tokens (JWT) to ensure secure access to the system.
Airtime Purchase: Consumes a specified airtime VTU API, allowing users to purchase airtime products for telecom providers. The system handles successful transactions and provides appropriate responses for failed transactions.
Unit Testing: Includes a comprehensive suite of unit tests with a target of achieving at least 80% code coverage, ensuring the reliability and correctness of the implemented features.
API Endpoints: Provides API endpoints for user registration, user login, and making airtime requests, enabling seamless integration and usage of the system.
Database Interaction: Utilizes a MySQL database for storing user information, wallet details, and transaction history to maintain a comprehensive record of user interactions.
Spring Boot Framework: Implements the project using the Spring Boot framework, adhering to standard software development life cycle practices to ensure maintainability and ease of development.
The Airtime Payment System aims to provide a robust and user-friendly solution for airtime transactions, incorporating best practices in software development and ensuring a high level of security and reliability.
## Author
Emmanuel Ahola
