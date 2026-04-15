# Employee Management System

A console-based **Employee Management System** built with **Core Java**, **JPA (Jakarta Persistence API)**, **Hibernate ORM**, and **PostgreSQL**. Supports full CRUD operations for employees and departments with a clean menu-driven interface.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| ORM | Hibernate 6.4 (JPA Provider) |
| Persistence API | Jakarta Persistence (JPA 3.1) |
| Database | PostgreSQL |
| Build Tool | Maven |
| IDE | Eclipse |

---

## Features

- Add, view, update, and delete **Departments**
- Add, view, update, and delete **Employees**
- Assign employees to departments (ManyToOne relationship)
- Filter employees by department
- Auto table creation via Hibernate `hbm2ddl.auto`
- Full transaction management with rollback on failure
- Input validation for all console inputs

---

## Project Structure

```
src/main/java
├── com.employee.entity
│   ├── Department.java       # Department entity (@Entity, @OneToMany)
│   └── Employee.java         # Employee entity (@Entity, @ManyToOne)
├── com.employee.dao
│   ├── DepartmentDAO.java    # CRUD operations for Department
│   └── EmployeeDAO.java      # CRUD operations for Employee
└── com.employee.main
    ├── JPAUtil.java           # EntityManagerFactory singleton
    ├── MainMenu.java          # Console UI and app entry point
    └── TestConnection.java    # DB connection test

src/main/resources
└── META-INF
    └── persistence.xml        # JPA configuration
```

---

## Database Schema

```sql
CREATE TABLE department (
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    location VARCHAR(100)
);

CREATE TABLE employee (
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(100) NOT NULL,
    last_name     VARCHAR(100) NOT NULL,
    email         VARCHAR(150) UNIQUE NOT NULL,
    salary        NUMERIC(10, 2),
    department_id INTEGER REFERENCES department(id)
);
```

> Tables are auto-created by Hibernate on first run — no manual SQL needed.

---

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.6+
- PostgreSQL installed and running
- Eclipse IDE (or any IDE with Maven support)

### Setup

**1. Clone the repository**
```bash
git clone https://github.com/kirangithub09/EmployeeManagementSystem.git
cd EmployeeManagementSystem
```

**2. Create the database**

Open psql or pgAdmin and run:
```sql
CREATE DATABASE employee_db;
```

**3. Configure your database credentials**

Open `src/main/resources/META-INF/persistence.xml` and update:
```xml
<property name="jakarta.persistence.jdbc.url"      value="jdbc:postgresql://localhost:5432/employee_db"/>
<property name="jakarta.persistence.jdbc.user"     value="postgres"/>
<property name="jakarta.persistence.jdbc.password" value="YOUR_PASSWORD"/>
```

**4. Build the project**
```bash
mvn clean install
```

**5. Run the application**

Right-click `MainMenu.java` → `Run As` → `Java Application`

Or via Maven:
```bash
mvn exec:java -Dexec.mainClass="com.employee.main.MainMenu"
```

---

## Sample Console Output

```
========================================
   Employee Management System (JPA)     
========================================

─── Main Menu ───
1. Department Management
2. Employee Management
3. Exit
Enter choice: 1

─── Department Menu ───
1. Add Department
2. View All Departments
3. Update Department
4. Delete Department
5. Back to Main Menu
Enter choice: 1

── Add Department ──
Department name: Engineering
Location: Mumbai
Department added: Engineering

── All Departments ──
  [1] Engineering — Mumbai
  [2] Human Resources — Pune
  [3] Finance — Bangalore
```

---

## Key Concepts Demonstrated

- **JPA / Hibernate ORM** — entity mapping, annotations, JPQL queries
- **Entity Relationships** — `@ManyToOne`, `@OneToMany`, `@JoinColumn`
- **Transaction Management** — `EntityTransaction` with commit and rollback
- **DAO Pattern** — separation of database logic from UI layer
- **persistence.xml** — JPA standard configuration (not Spring, raw JPA)
- **PostgreSQL** — relational database with foreign key constraints

---

## What I Learned

- How Hibernate maps Java objects to relational database tables
- The difference between JPA (specification) and Hibernate (implementation)
- Writing JPQL queries vs native SQL
- Managing EntityManager lifecycle and transactions manually
- Why the DAO pattern keeps code maintainable and testable

---

## Future Improvements

- [ ] Add Spring Boot layer on top of existing JPA code
- [ ] Build a REST API with Spring MVC
- [ ] Add search by employee name
- [ ] Add pagination for large result sets
- [ ] Write unit tests with JUnit 5 and H2 in-memory database

---

## Author

**Kiran Rawle**
- GitHub: @kirangithub09
- Email: kiranrawle07@gmail.com
