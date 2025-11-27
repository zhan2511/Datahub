# Datahub

## Introduction

Datahub is a data management platform for research assistant to manage their data assets.

## 🛠 Tech Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Maven**
- Spring Web


## 📦 项目结构

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── org.example.datahub
│   │   │       ├── api
│   │   │       ├── model
│   │   │       ├── common
│   │   │       ├── classes
│   │   │       └── DatahubApplication.java
│   │   └── resources
│   │       ├── openapi
│   │           └── api.yml
│   │       ├── application.properties
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── org.example.datahub
├── todos
│   └── todoList.md
├── pom.xml
└── README.md
```


## 🚀 Run the project

### 1. Clone the repository

```bash
git clone https://github.com/zhan2511/datahub.git
cd datahub
```

### 2. Compile and run the project

```bash
mvn clean install
mvn spring-boot:run
```




