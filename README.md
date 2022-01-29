# Diettetican App

Application for managing meals in the diet.
## Table of Contents
- [Technologies](#technologies)
- [Project description](#project-description)
- [How to Install and Run the Project](#how-to-install-and-run-the-project)
- [Scope of funcionalities](#scope-of-funcionalities)
## Technologies
Project is created with:
- Java 16,
- Vaadin 22 - frontend framework for web using Java language,
- Maven, 
- Spring Boot,
- Spring Data JDBC
- PostgreSQL 14,

## Project description

The application is used to manage the menu and control the calories consumed. The system has a database of meals that the user can use. If you have your favorite dishes, you can add them to the database. When preparing a diet, you do not worry about how many calories are in a given dish, the system will calculate the proportion of weight for your caloric needs

## How to Install and Run the Project

**1. Project**
You need to clone this repository. Everything what you need to run project is including in maven dependency and project package. Project you can start by running **DietAppApplication.java** in src.main.java.pl.dietapp. Project wll be launched on **localhost:8082**

If you have error during building the frontend try remove files below
- node_modules,
- package.json,
- package-loc.json
- webpack.config.js

And run this in terminal.

```bash
npm install
```

**2. Database **
You need a database to use the system. I included the database in two text files **Init SQL** and **Data SQL**. First create a database using the **InitSQL.txt** schema and after creating the tables add data to it using **DataSQL.txt **scripts. I have included the database files in the **appDB** folder. You will also need information about database properties, you can also modify them for yourself in **resources / appliciation.properties**:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/dietapp-db
spring.datasource.username=postgres
spring.datasource.password=admin
server.port=8082
```

Login and password for testers users below:

Admin:

- login: KarolAdmin
- password: admin

User:

- login: KarolUser
- password: user

Dietician:

- login: KarolDietician
- password: diet

## Scope of funcionalities
