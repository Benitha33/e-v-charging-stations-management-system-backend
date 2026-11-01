EV Charging Stations Management System - Backend

Student: Umulisa Benitha
Student ID: 27608
Project Name: EV Charging Stations Management System
Course: INSY 8322 - Web Technology and Internet
Lecturer: Patrick Dushimimana
Institution: Faculty of Information Technology, AUCA
Academic Year: 2026-2027

---

Overview

A Spring Boot backend for managing a network of EV charging stations and the drivers who use them. A driver books a charging session served by a charging point installed in a bay; each charging point supports several connector types (CCS, Type 2, CHAdeMO, GB/T). A session moves through RESERVED -> CHARGING -> FINISHED or CANCELLED. Once finished, the system records charge receipts.

The API implements 18 functional requirements (A1-A2, B1-B3, C1-C4, D1-D4, E1-E3, F1-F2) plus 2 bonus features (dashboard, pagination). Every endpoint is tested with Postman; the collection is at the repository root.

---

Tech Stack

Language: Java 17
Framework: Spring Boot 4.1.2
Persistence: Spring Data JPA / Hibernate 7
Database: PostgreSQL 17
Build: Maven wrapper included

---

Prerequisites

- Java 17 or newer (verify with java -version)
- PostgreSQL 14 or newer running on localhost:5432
- No separate Maven install needed

---

Steps to Run the API

1. Create the PostgreSQL database

   psql -U postgres -c "CREATE DATABASE ev_charging_db;"

2. Configure credentials (src/main/resources/application.properties)

   spring.datasource.url=jdbc:postgresql://localhost:5432/ev_charging_db
   spring.datasource.username=postgres
   spring.datasource.password=postgres

3. Start the application

   Windows: .\mvnw spring-boot:run
   Linux/macOS: ./mvnw spring-boot:run

4. The API is live at http://localhost:8080/api

   Import e-v-charging-stations-management-system-midterm.postman_collection.json
   into Postman and run requests individually or via the Collection Runner.

5. Stop the server with Ctrl + C

---

API Endpoints (all prefixed /api)

Connector Types
  POST   /connector-types/save
  GET    /connector-types/all
  GET    /connector-types/{id}
  PUT    /connector-types/update/{id}
  DELETE /connector-types/delete/{id}
  GET    /connector-types/unused                  (C3)

Bays
  POST   /bays/save
  GET    /bays/all
  GET    /bays/{id}
  PUT    /bays/update/{id}
  DELETE /bays/delete/{id}
  GET    /bays/free                               (C2)

Charging Points
  POST   /charging-points/save
  GET    /charging-points/all
  GET    /charging-points/{id}
  PUT    /charging-points/update/{id}
  DELETE /charging-points/delete/{id}
  GET    /charging-points/by-connector-type?name=ccs          (C1)
  PUT    /charging-points/{cpId}/connector-types/{ctId}       (D3)
  PATCH  /charging-points/{cpId}/bay?bayNumber=4              (D4)

Drivers
  POST   /drivers/save
  GET    /drivers/all
  GET    /drivers/{id}
  PUT    /drivers/update/{id}
  DELETE /drivers/delete/{id}
  GET    /drivers/by-last-name?lastName=umulisa               (B1)
  GET    /drivers/of-charging-point/{cpId}                    (C4)

Charging Sessions
  POST   /charging-sessions/save                              (D1)
  GET    /charging-sessions/all
  GET    /charging-sessions/{id}
  PUT    /charging-sessions/update/{id}
  DELETE /charging-sessions/delete/{id}                       (E3)
  GET    /charging-sessions/by-status?status=RESERVED         (B2)
  GET    /charging-sessions/between?start=2026-10-01&end=2026-10-31   (B3)
  PATCH  /charging-sessions/{id}/status?status=CHARGING       (D2)
  GET    /charging-sessions/stats/by-status                   (F1)
  PATCH  /charging-sessions/cancel-day?chargingPointId=...&date=2026-10-20  (F2)
  GET    /charging-sessions/page?page=0&size=5&sort=sessionDate,desc        (Bonus)

Charge Receipts
  POST   /charge-receipts/save?chargingSessionId=...          (E1)
  GET    /charge-receipts/all
  GET    /charge-receipts/{id}
  PUT    /charge-receipts/update/{id}
  DELETE /charge-receipts/delete/{id}                         (E3)
  GET    /charge-receipts/driver/{plateNumber}                (E2)

Dashboard
  GET    /dashboard/summary                                    (Bonus)

---

Status Flow (D2)

A charging session may only move along these transitions. Any other move returns HTTP 400.

  RESERVED --> CHARGING --> FINISHED
       |            |
       +------------+------> CANCELLED

---

Package Structure

  buildingtheev.charging.stations.management.system.rw
  +-- Application.java
  +-- entity/       (7 entities)
  +-- repository/   (6 repositories)
  +-- service/      (7 services)
  +-- controller/   (7 controllers)

---

Postman Collection

e-v-charging-stations-management-system-midterm.postman_collection.json at the repository root contains one request per requirement, named by requirement ID. The collection uses a baseUrl variable set to http://localhost:8080/api.

---

Author

Umulisa Benitha - Student ID 27608
Course INSY 8322 Web Technology and Internet
Faculty of Information Technology, AUCA