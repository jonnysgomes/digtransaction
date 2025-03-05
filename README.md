# Digital transaction API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

This project is an API built using **Java, Java Spring, H2 as the database.** I have developed this project to exercise my Java skills.

## Table of Contents

- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)

## Technologies

- Maven
- Java, spring boot, spring web, spring data jpa
- H2 database
- Bean validation
- Lombok
- JUnit, Mockito and AssertJ

## Installation

1. Clone the repository:

```bash
git clone https://github.com/jonnysgomes/digtransaction.git
```

2. Install dependencies with Maven

## Usage

1. You can import the `digitalTransaction.postman_collection.json` file on Postman
2. Start the application with Maven
3. The API will be accessible at http://localhost:8080

## API Endpoints
The API provides the following endpoints:

**CREATE USER**
```
POST /user/new - Create a new user
```

**GET USERS**
```
GET /user/all - Retrieve a list of all users
```

**CREATE ACCOUNT**
```
POST /account/new - Create a new account
```

**GET ACCOUNTS**
```
GET /account/all - Retrieve a list of all accounts
```

**ADD MONEY TO ACCOUNT**
```
POST /account/addMoney - Add money to a given account
```

**CREATE TRANSACTION**
```
POST /transaction/new - Create a transaction: transger money from account A to account B. 
```

**GET TRANSACTIONS**
```
GET /transaction/all - Retrieve a list of all transactions
```

