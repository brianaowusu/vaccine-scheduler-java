# vaccine-scheduler-java
A command-line based application for managing COVID-19 vaccination appointments. Patients can book appointments and track their vaccines, while caregivers manage schedules and vaccine inventory.

## 🛠 Features

### 👤 Patients
- Create and log into accounts securely.
- Search caregiver availability by date.
- Reserve appointments with available caregivers.
- View and cancel upcoming appointments.
- Log out securely.

### 🩺 Caregivers 
- Register and log in.
- Upload their availability by date.
- Add doses for specific vaccine types.
- View scheduled appointments.
- Cancel appointments (restoring doses/availability).
- Log out securely.

---

## 💻 Technologies Used

- **Java** – Application logic and CLI
- **SQLite** – Relational database
- **JDBC** – Database connectivity
- **Password Hashing with Salt** – Secure credential storage

---

## 🚀 Getting Started

### ✅ Prerequisites
- Java 8 or higher
- SQLite JDBC driver (ensure it’s in your classpath)

### 🗃 Database Setup

Ensure your SQLite database includes these tables:

```sql
CREATE TABLE Patients (
    Username TEXT PRIMARY KEY,
    Salt BLOB NOT NULL,
    Hash BLOB NOT NULL
);

CREATE TABLE Caregivers (
    Username TEXT PRIMARY KEY,
    Salt BLOB NOT NULL,
    Hash BLOB NOT NULL
);

CREATE TABLE Availabilities (
    Time DATE,
    Username TEXT,
    PRIMARY KEY (Time, Username),
    FOREIGN KEY (Username) REFERENCES Caregivers(Username)
);

CREATE TABLE Vaccines (
    Name TEXT PRIMARY KEY,
    Doses INTEGER NOT NULL
);

CREATE TABLE Reservations (
    AppointmentID INTEGER PRIMARY KEY,
    VaccineName TEXT NOT NULL,
    Time DATE NOT NULL,
    CaregiverUsername TEXT NOT NULL,
    PatientUsername TEXT NOT NULL,
    FOREIGN KEY (VaccineName) REFERENCES Vaccines(Name),
    FOREIGN KEY (CaregiverUsername) REFERENCES Caregivers(Username),
    FOREIGN KEY (PatientUsername) REFERENCES Patients(Username)
);
```
## 📌 Future Improvements
Email/text appointment reminders
Admin analytics dashboard

## 📄 License
This project is licensed under the MIT License.
