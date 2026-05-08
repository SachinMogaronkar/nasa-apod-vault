# 🚀 NASA APOD Vault

A full-stack cloud-deployed web application that allows users to explore NASA’s Astronomy Picture of the Day (APOD), securely authenticate using JWT, and save favorite APODs into a personal collection.

---

# 🌌 Overview

NASA APOD Vault is a production-style full-stack application built using Spring Boot, JWT Authentication, PostgreSQL, Docker, and vanilla JavaScript.

The application integrates with NASA’s official APOD API and provides users with a personalized APOD experience.

Users can:

- 🔭 Explore NASA Astronomy Picture of the Day
- 📅 Search APOD by specific date
- 🔐 Register and login securely using JWT authentication
- 💾 Save favorite APODs
- 🗑 Manage saved APOD collection
- ☁️ Access fully deployed cloud-hosted application

---

# 🌐 Live Demo

## Frontend
https://nasa-apod-vault.netlify.app

## Backend Health Endpoint
https://nasa-apod-vault.onrender.com/api/nasa/health

---

# 🔥 Features

- 🌍 Real-time NASA APOD API integration
- 📅 Search APOD by date
- 🔐 JWT-based authentication & authorization
- 💾 Save APOD to personal vault
- 🗑 Delete saved APODs
- ☁️ Cloud deployment with Render & Netlify
- 🐳 Dockerized Spring Boot backend
- 🛡 Secure environment-based configuration
- 🎨 Responsive glassmorphism-inspired UI

---

# 🛠 Tech Stack

## Backend
- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA / Hibernate
- Maven
- Docker

## Frontend
- HTML5
- CSS3
- JavaScript

## Database
- MySQL (Local Development)
- Neon PostgreSQL (Production)

## Deployment
- Netlify (Frontend)
- Render (Backend)
- Neon PostgreSQL (Database Hosting)

## External API
- NASA APOD API

---

# 🏗 System Flow Architecture

```text
                ┌─────────────────────┐
                │     User Browser    │
                └─────────┬───────────┘
                          │
                          ▼
                ┌─────────────────────┐
                │  Frontend (Netlify) │
                │ HTML / CSS / JS UI  │
                └─────────┬───────────┘
                          │ REST API Calls
                          ▼
                ┌─────────────────────┐
                │ Spring Boot Backend │
                │      (Render)       │
                └─────────┬───────────┘
                          │
          ┌───────────────┴───────────────┐
          ▼                               ▼
┌──────────────────┐          ┌────────────────────┐
│ NASA APOD API    │          │ Neon PostgreSQL DB │
│ External API     │          │ User & Saved APODs │
└──────────────────┘          └────────────────────┘
```

---

# 📁 Project Structure

```text
nasa-apod-vault/

├── backend/
│   ├── src/main/java/com/openapi/nasa/
│   │   ├── config/
│   │   ├── restcontroller/
│   │   ├── security/
│   │   ├── service/
│   │   ├── entity/
│   │   └── daorepo/
│   │
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   ├── application-local.properties
│   │   ├── application-docker.properties
│   │   └── application-prod.properties
│   │
│   ├── Dockerfile
│   ├── .dockerignore
│   └── pom.xml
│
├── frontend/
│   ├── index.html
│   ├── Homepage.html
│   ├── Signup.html
│   ├── script.js
│   ├── *.css
│   └── assets/
│
└── README.md
```

---

# 🚀 Running Project Locally

# 1️⃣ Backend Setup

## Step 1: Navigate to backend folder

```bash
cd backend
```

## Step 2: Run Spring Boot application

```bash
mvn spring-boot:run
```

## Step 3: Verify backend

Open:

```text
http://localhost:5000/api/nasa/health
```

Expected response:

```json
{
  "database": "CONNECTED",
  "users": 0,
  "status": "UP"
}
```

---

# 2️⃣ Frontend Setup

Open:

```text
frontend/index.html
```

using Live Server or browser.

---

# 🔑 Environment Configuration

You need a NASA API key.

Get API Key:

https://api.nasa.gov/

---

## Local Development Configuration

Add inside:

```text
backend/src/main/resources/application-local.properties
```

Example:
```properties
# NASA APOD API key
# Generate from: https://api.nasa.gov/
nasa.api.key=YOUR_NASA_API_KEY

# JWT secret key used for token signing
# Replace with a strong random secret string
nasa.jwt.secret=YOUR_SECURE_JWT_SECRET

# Local MySQL database configuration
# Replace database name if different
spring.datasource.url=jdbc:mysql://localhost:3306/nasa-directory

# Local MySQL username
spring.datasource.username=YOUR_DB_USERNAME

# Local MySQL password
spring.datasource.password=YOUR_DB_PASSWORD
```

# 🐳 Docker Support

Backend is fully Dockerized.

## Build Docker Image

```bash
docker build -t nasa-backend .
```

## Run Docker Container

```bash
docker run -p 5000:5000 nasa-backend
```

---

# 🔐 Authentication Flow

1. User registers via Signup page
2. User logs in using credentials
3. Backend validates credentials
4. JWT token is generated
5. Token stored in browser localStorage
6. Frontend sends token in Authorization header
7. Protected APIs validate JWT before access

---

# 📸 Screenshots

## Login Page

<img width="1920" height="869" alt="image" src="https://github.com/user-attachments/assets/342ad22f-a383-4eb8-ab1b-6a1fcc1eab06" />

---

## APOD View Page

<img width="1920" height="869" alt="image" src="https://github.com/user-attachments/assets/d5eb8aa2-c3b7-4cac-afbb-66a769332261" />

---

## Saved APOD Collection

<img width="1920" height="869" alt="image" src="https://github.com/user-attachments/assets/23da4276-e92e-4349-9a22-58e6ea75c8d0" />

---

# ☁️ Deployment Architecture

| Layer | Platform |
|---|---|
| Frontend Hosting | Netlify |
| Backend Hosting | Render |
| Database Hosting | Neon PostgreSQL |
| Containerization | Docker |

---

# 🛡 Production Features

- Environment-based configuration
- Secure JWT authentication
- Cloud PostgreSQL integration
- Dockerized backend deployment
- CORS configuration for production
- Stateless Spring Security architecture
- Separate local / docker / production profiles

---

# 👨‍💻 Author

Sachin Mogaronkar
