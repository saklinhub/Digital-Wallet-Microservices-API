# Digital Wallet & Transaction API

A production-ready microservices-based Digital Wallet and Payment System built with Spring Boot.

## 🏗️ Architecture

This system consists of 7 microservices:

- **Eureka Server** (Port 8761) - Service discovery and registration
- **API Gateway** (Port 8080) - Single entry point with JWT authentication
- **Auth Service** (Port 8081) - User authentication and JWT token generation
- **User Service** (Port 8082) - User profile management
- **Wallet Service** (Port 8083) - Digital wallet operations
- **Transaction Service** (Port 8084) - Payment processing
- **Notification Service** (Port 8085) - Event notifications

## 🚀 Quick Start

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL 14+
- Git

### Database Setup

Create the following PostgreSQL databases:

```sql
CREATE DATABASE auth_db;
CREATE DATABASE user_db;
CREATE DATABASE wallet_db;
CREATE DATABASE transaction_db;
```

### Environment Configuration

Each service requires environment variables. Copy the `.env.example` files to `.env` in each service directory:

```bash
# Auth Service
cp auth-service/.env.example auth-service/.env

# User Service
cp user-service/.env.example user-service/.env

# Wallet Service
cp wallet-service/.env.example wallet-service/.env

# Transaction Service
cp transaction-service/.env.example transaction-service/.env

# API Gateway
cp api-gateway/.env.example api-gateway/.env
```

Then edit each `.env` file with your actual credentials:

**auth-service/.env:**
```env
DB_PASSWORD=your_postgres_password
JWT_SECRET=your_256_bit_secret_key
```

**user-service/.env, wallet-service/.env, transaction-service/.env:**
```env
DB_PASSWORD=your_postgres_password
```

**api-gateway/.env:**
```env
JWT_SECRET=your_256_bit_secret_key
```


### Running the Services

Start services in this order:

1. **Eureka Server** (Service Discovery)
```bash
cd eureka-server
mvn spring-boot:run
```

2. **Auth Service**
```bash
cd auth-service
mvn spring-boot:run
```

3. **User Service**
```bash
cd user-service
mvn spring-boot:run
```

4. **Wallet Service**
```bash
cd wallet-service
mvn spring-boot:run
```

5. **Transaction Service**
```bash
cd transaction-service
mvn spring-boot:run
```

6. **Notification Service**
```bash
cd notification-service
mvn spring-boot:run
```

7. **API Gateway**
```bash
cd api-gateway
mvn spring-boot:run
```

### Verify Services

- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8080

## 📡 API Endpoints

All requests go through the API Gateway at `http://localhost:8080`

### Authentication

#### Register New User
```bash
POST /auth/register
Content-Type: application/json

{
  "username": "john.doe",
  "password": "SecurePass123!",
  "email": "john.doe@example.com"
}
```

**Response:**
```json
{
  "id": 1,
  "username": "john.doe",
  "email": "john.doe@example.com",
  "createdAt": "2025-12-19T20:00:00"
}
```

#### Login
```bash
POST /auth/token
Content-Type: application/json

{
  "username": "john.doe",
  "password": "SecurePass123!"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1,
  "username": "john.doe",
  "expiresIn": 1800
}
```

### User Management

#### Get User Profile
```bash
GET /users/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:**
```json
{
  "id": 1,
  "username": "john.doe",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "createdAt": "2025-12-19T20:00:00"
}
```

### Wallet Operations

#### Create Wallet
```bash
POST /wallets
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "userId": 1,
  "currency": "USD"
}
```

**Response:**
```json
{
  "id": 1,
  "userId": 1,
  "balance": 0.00,
  "currency": "USD",
  "status": "ACTIVE",
  "createdAt": "2025-12-19T20:00:00"
}
```

#### Get Wallet Balance
```bash
GET /wallets/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:**
```json
{
  "id": 1,
  "userId": 1,
  "balance": 1500.00,
  "currency": "USD",
  "status": "ACTIVE",
  "lastUpdated": "2025-12-19T21:00:00"
}
```

### Transactions

#### Create Transaction
```bash
POST /transactions
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "fromWalletId": 1,
  "toWalletId": 2,
  "amount": 100.00,
  "description": "Payment for services"
}
```

**Response:**
```json
{
  "id": 1,
  "fromWalletId": 1,
  "toWalletId": 2,
  "amount": 100.00,
  "status": "SUCCESS",
  "description": "Payment for services",
  "transactionDate": "2025-12-19T21:00:00",
  "referenceNumber": "TXN-20251219-000001"
}
```

#### Get Transaction History
```bash
GET /transactions/wallet/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:**
```json
[
  {
    "id": 1,
    "fromWalletId": 1,
    "toWalletId": 2,
    "amount": 100.00,
    "status": "SUCCESS",
    "description": "Payment for services",
    "transactionDate": "2025-12-19T21:00:00",
    "referenceNumber": "TXN-20251219-000001"
  },
  {
    "id": 2,
    "fromWalletId": 3,
    "toWalletId": 1,
    "amount": 250.00,
    "status": "SUCCESS",
    "description": "Received payment",
    "transactionDate": "2025-12-19T20:30:00",
    "referenceNumber": "TXN-20251219-000002"
  }
]
```

## 🔒 Security

- All secrets are externalized to environment variables
- JWT-based authentication
- API Gateway filters protect sensitive endpoints
- Database passwords never stored in code

## 🛠️ Technology Stack

- **Framework**: Spring Boot 
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Security**: JWT (JSON Web Tokens)
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Java Version**: 21

## 📁 Project Structure

```
.
├── eureka-server/          # Service registry
├── api-gateway/            # API Gateway with JWT validation
├── auth-service/           # Authentication service
├── user-service/           # User management
├── wallet-service/         # Wallet operations
├── transaction-service/    # Transaction processing
└── notification-service/   # Notifications
```

## 🔧 Development

### Building All Services

```bash
mvn clean install
```

### Running Tests

```bash
mvn test
```

## 📝 License

This project is licensed under the MIT License.

## 👥 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
