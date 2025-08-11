# 🚗 ShareTrip - Ride Sharing & Cost Sharing API

ShareTrip is a Spring Boot-based backend for a ride-sharing and travel cost-sharing platform.  
It allows **car owners** to post rides, and **passengers** to book available seats, helping share travel costs in a secure and efficient way.

---

## 📌 Features

- **User Management**
  - Passenger & Driver roles
  - Role-based booking & posting restrictions
- **Ride Management**
  - Create, view, and manage rides
- **Booking System**
  - Passengers can book available seats
  - Booking status tracking (`PENDING`, `CONFIRMED`, `CANCELLED`)
- **Flyway Database Migrations**
  - Automated schema setup on startup
- **PostgreSQL Database Support**
  - Works locally and on Railway
- **REST API**
  - JSON request/response format
  - Postman-friendly endpoints

---

## 🛠 Tech Stack

- **Java 17+**
- **Spring Boot 3**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **Flyway**
- **Maven**
- **Railway (Cloud Deployment)**

---

## ⚙️ Installation & Setup

### 1️⃣ Clone the repository
```bash
git clone https://github.com/your-username/sharetrip.git
cd sharetrip
