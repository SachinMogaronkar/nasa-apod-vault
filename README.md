# 🚀 NASA APOD Vault

A full-stack application that displays NASA’s Astronomy Picture of the Day (APOD) with authentication and save functionality.

🌌 Overview

NASA APOD Vault is a full-stack web application that allows users to explore NASA’s Astronomy Picture of the Day.

Users can:
* Search APOD by date
* Authenticate securely
* Save their favorite images
* Manage their personal collection.

🔥 Features
* 🌍 Fetch real-time APOD data from NASA API
* 📅 Search APOD by date
* 🔐 User authentication (JWT-based login/signup)
* 💾 Save APOD to personal collection
* 🗑 Delete saved APODs
* 🎨 Smooth UI with animations
  
🛠 Tech Stack

Backend:

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* JPA / Hibernate

Frontend:

* HTML
* CSS (Glassmorphism UI)
* JavaScript

Get your API:

* NASA APOD API → https://api.nasa.gov/

📁 Project Structure

apod-vault/

├── backend/

│ ├── src/main/java/com/openapi/nasa/...

│ ├── src/main/resources/application.properties

│ └── pom.xml
│

├── frontend/

│ ├── Homepage.html

│ ├── Login.html

│ ├── Signup.html

│ ├── script.js

│ ├── *.css

│ └── assets/

└── README.md

🚀 How to Run Locally
1. Backend Setup

  Step 1: Navigate to backend folder
  
  cd backend
  
  Step 2: Run Spring Boot application
  
  mvn spring-boot:run
  
  Step 3: Verify server is running
  
  Open browser:
  
  http://localhost:5000/api/nasa/health
  
  Expected response:
  
  OK

2. Frontend Setup

Open the following file in browser:

frontend/Login.html

🔑 Configuration

You need a NASA API key.

Step 1: Get API key
https://api.nasa.gov/

Step 2: Add key in:

backend/src/main/resources/application.properties

Example:

nasa.api.key=YOUR_API_KEY

🔐 Authentication Flow

* User registers via Signup

* Logs in using credentials

* Backend generates JWT token

* Token is stored in browser (localStorage)

* All API requests use Authorization header

📸 Screenshots

Login Page
<img width="1920" height="869" alt="image" src="https://github.com/user-attachments/assets/342ad22f-a383-4eb8-ab1b-6a1fcc1eab06" />

APOD View Page
<img width="1920" height="869" alt="image" src="https://github.com/user-attachments/assets/d5eb8aa2-c3b7-4cac-afbb-66a769332261" />

Saved APOD grid
<img width="1920" height="869" alt="image" src="https://github.com/user-attachments/assets/23da4276-e92e-4349-9a22-58e6ea75c8d0" />


🌐 Future Improvements

Improve mobile responsiveness

👤 Author

Sachin Mogaronkar
