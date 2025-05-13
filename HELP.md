# Credit Service

The **Credit Service** is a core backend microservice responsible for handling credit-related business logic in the banking platform. It covers credit evaluation, credit creation, installment generation, and credit limit management. The service integrates tightly with security, authentication, and customer management systems, ensuring role-based access and secure operations.

> 📌 Note: The original package name `com.banking.ing.credit.credit-service` was invalid due to the hyphen (`-`). The correct package used in this project is `com.banking.ing.credit.creditservice`.

---

## 📦 Modules Overview

The `credit-service` is a modular Spring Boot application consisting of the following key APIs:

### 🔐 Security Layer
- Role-based access control using JWT
- Only `ADMIN` or `CUSTOMER` roles may access endpoints
- Authentication integrated using Spring Security and stateless JWT authorization
- Security context manages the active authenticated user per request

### 👤 User API
- CRUD operations for `UserEntity`
- Mapped to credit entities like `CustomerCreditEntity`
- Soft delete support via Hibernate filters

### 💳 Customer Credit API
- Manages customer's credit limit and score
- Maps customer details to DTOs for safe data transfer
- Includes operations such as:
    - Create or update credit info
    - Retrieve credit by customer ID
    - Soft-delete support at the repository level
- Custom object mappers used for entity/DTO transformation

### 💰 Loan API
- Core service for initiating loans after validation
- Validates customer creditworthiness before approval
- Uses `CustomerLoanCreateRequest` to receive loan initiation data
- Validations include:
    - Sufficient credit limit
    - Valid installment count and interest rate
    - Required non-null fields

### 📆 Installment API
- Automatically generates installments post-loan creation
- Calculates due dates and amounts based on terms
- Linked to parent loans for structured repayment plans

### 🎯 Business Logic Layer
- The central `CreditApi` orchestrates:
    - Credit evaluation
    - Loan and installment generation
    - Transactional data persistence
- Reusable service and mapping logic in dedicated layers for better separation of concerns

---

## 🚀 Getting Started

### 🧱 Prerequisites

- Java 17+
- Maven 3.8+
- Docker (optional for containerization)
- PostgreSQL / H2 database (if running locally)

### 🧰 Technologies Used
- Spring Boot
- Spring Security
- JWT
- JPA/Hibernate (Soft Delete Support)
- MapStruct (or manual mapping layer)
- Maven
- Docker (for containerization)- 

### 🔐 Security
- JWT-based authentication
- Custom token filters for request validation
- @PreAuthorize used on secured endpoints
- User roles: ADMIN, CUSTOMER
- Stateless sessions
- Secure password handling (e.g., BCrypt)


### 📦 Build the Application

```bash
mvn clean install
SPRING_PROFILES_ACTIVE=dev mvn spring-boot:run
```

```bash 
mvn clean package -DskipTests
```
#### if youre using docker-desktop
```
docker build -t credit-service:latest .

docker run --name credit-service \
  -e SPRING_PROFILES_ACTIVE=dev \
  -p 8080:8080 \
  credit-service:latest
```
#### if youre using podman-decktop
```
podman build -t credit-service:latest .

podman run --name credit-service \
  -e SPRING_PROFILES_ACTIVE=dev \
  -p 8080:8080 \
  credit-service:latest
```


