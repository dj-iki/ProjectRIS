# FlightFinder – Skyscanner-Inspired Flight Booking App

FlightFinder is a web application inspired by **Skyscanner**.  
It allows users to search, explore, and book flights with an intuitive step-by-step interface, while supporting different roles with specific permissions.

---

## Features by Role

### Guest (Not Logged In)
- **Register** or **Log In**.
- **Search for flights** (One-way or Return) for selected dates and number of seats.
- Flexible destination search:
  1. Search without selecting a destination → shows **all countries** available.
  2. Select a **country** → shows **all cities** in that country.
  3. Select a **city** → shows **all airports** in that city.
  4. Select an **airport** → displays all available flights for those parameters.
- Cannot book seats until logged in.

---

### Logged-In User
- All guest functionalities.
- **Book seats**:
  - For every seat selected, fill in **personal passenger data**.
  - If booking a **return flight**, the returning ticket is automatically filled with the **same passenger data**.
- View booking confirmation.

---

### Employee
- All user functionalities.
- View **all future flights** the employee is assigned to.

---

### Manager
- All employee functionalities.
- **Promote users** to employees.
- **Add flights** to the system.
- **Add planes** to the system.

---

### Admin
- All manager functionalities.
- **Add cities**, **countries**, and **airlines**.
- **Promote** a user or employee to **manager**.

---

## Tech Stack
- **Backend:** Spring Boot (Java)
- **Frontend:** JSP / HTML / CSS / JavaScript
- **Database:** MySQL
- **Authentication:** Spring Security (JWT stored in cookies)

