# Governance, Risk & Compliance (GRC) Risk Management Application

## Overview

This is a complete GRC Risk Management application with Angular frontend and Spring Boot backend designed for Internal Audit & Risk Management.

---

## Architecture

- **Backend:** Spring Boot REST API with JPA/Hibernate connected to MySQL database.
- **Frontend:** Angular with Angular Material and JWT-secured communication.
- **Authentication:** JWT tokens with role-based access control.
- **Database:** MySQL (can be adjusted in application.yml).
- **UI Framework:** Angular Material and Bootstrap CSS.
- **Security:** JWT, BCrypt password hashing, input validation, CORS configuration.

---

## Prerequisites

- Java 17+
- Maven 3.8+
- MySQL Server
- Node.js 18+ and npm
- Angular CLI (optional)

---

## Setup Instructions

### Backend Setup

1. Import or clone the repository.

2. Create `grc_db` database in MySQL:

