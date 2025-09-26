# **System Design Document (SDD)**

## **Spring Boot Demo Factory API**

**Version:** 1.0.0
**Date:** September 25, 2025

---

## Description

This is a template for Backend Application (Spring Boot) which stores Products.

## Authors

- [@jadogeri](https://www.github.com/jadogeri)

## Screenshots

| ![Screenshot 1](assets/images/screenshot1.png) | ![screenshot 2](assets/images/screenshot2.png) |
| -------------------------------------------- | -------------------------------------------- |
|                                              |                                              |

## Table of Contents

<ul>
    <li><a href="#1-introduction">1. Introduction</a>
        <ul>
            <li><a href="#11-purpose">1.1 Purpose</a> </li>
            <li><a href="#12-scope">1.2 Scope</a> </li>
            <li><a href="#13-intended-audience">1.3 Intended Audience</a> </li>
        </ul>
    </li>
</ul>
    <ul>
      <li><a href="#2-api-reference">2. API Reference</a>
      </li>
    </ul>
    <ul>
      <li><a href="#3-system-architecture">3. System Architecture</a>
        <ul>
          <li><a href="#31-high-level-architecture">3.1 High Level Architecture</a> </li>
          <li><a href="#32-technology-stack">3.2 Technology Stack</a> </li>
          <li><a href="#33-deployment-artifacts">3.3 Deployment Artifacts</a> </li>
        </ul>
      </li>
    </ul>
    <ul>
      <li><a href="#4-data-design">4. Data Design</a>
        <ul>
          <li><a href="#41-data-entities-and-relationships">4.1 Entities and Relationships</a> </li>
          <li><a href="#42-database-conceptual-schema">4.2 Database Conceptual Schema</a> </li>
          <li><a href="#33-deployment-artifacts">3.3 Deployment Artifacts</a> </li>
        </ul>
      </li>
    </ul> 
    <ul>
      <li><a href="#5-installation">5. Installation</a>
      </li>
    </ul> 
    <ul>
        <li><a href="#6-usage">6. Usage</a>
        <ul>
            <li><a href="#61-run-application">6.1 Run Application</a> </li>
            <ul>
              <li><a href="#611-run-locally">6.1.1 Run Locally</a> </li>
              <li><a href="#612-run-docker-container">6.1.2 Run Docker Container</a> </li>
            </ul>
        </ul>
        </li>
    </ul> 
    <ul>
        <li><a href="#7-api-testing">7. API Testing</a>
        </li>
    </ul> 
    <ul>
        <li><a href="#8-tests">8. Tests</a>
        </li>
    </ul>  
    <ul>  
        <li><a href="#9-license">9. License</a>
        </li>
    </ul> 
    <ul> 
        <li><a href="#10-references">10. References</a>
        </li>
    <ul>

## **1. Introduction**

### **1.1 Purpose**

This document outlines the system architecture, components, and design considerations for Factory API. The goal is to provide a template for backend developers to handle CRUD operations.

### **1.2 Scope**

The system will allow users to:

- Create Products.
- Interact with API via swagger.

### **1.3 Intended Audience**

- Junior or Senior backend developers.
- beginners learning Spring Boot (Java).

---

### **3.2 Technology Stack**

- **Programming Languages**: Java, SQL
- **IDE**: IntelliJ and Visual Studio Code (VSCode)
- **Backend Frameworks**: Spring Boot JPA
- **Database**: H2 Database
- **Test**: JUnit and TestContainers
- **Plugins**: Early AI
- **Container**: Docker
- **Version Control**: Git and GitHub
- **CI/CD**: GitHub Actions
- **Code Analsis**: SonarQube
- **Documentation**: Swagger


### **3.3 Deployment Artifacts**

- **Backend Application**: Appllicatio ncontains everyting to build and run Spring Boot instance 

---

## **4. Data Design**

### **4.1 Data Entities and Relationships**

| Entity | Description                                          |
| ------ | ---------------------------------------------------- |
| PRODUCT   | product made in factory. |


---

## **5. Installation**

* [Download and install IntelliJ Community Edition](https://www.jetbrains.com/idea/download/?section=windows)
* [Download and install Java](https://www.oracle.com/java/technologies/downloads/)
* [Download and install Maven](https://maven.apache.org/download.cgi)
* [Download and install Docker - Windows](https://docs.docker.com/desktop/setup/install/windows-install/)
* [Download and install Docker - Mac](https://docs.docker.com/desktop/setup/install/mac-install/)


---

## **6. Usage**

**Prerequisites** :installation of Java, Docker and Maven.

### **6.1 Run Application**

0 open intelliJ IDE.

1 Select option CLONE REPOSITORY .

2 Copy and paste url https://github.com/jadogeri/Spring-Boot-Demo-Factory-API.git and choose location to save project then press clone.

 (Note!! choose empty folder : I created folder factoryAPI)

![start application](assets/images/cloneProject.png)


#### **6.1.1 Run Locally**

0 Change view to Project

1 Navigate to factory --> src --> main -- java

2 Inspect file FactoryApplication.java

3 Press play button to start application.

![start application](assets/images/runApp.png)


## **7. API Testing**

**Prerequisites** :ensure container or local application is running.

**Note** : use [http://localhost:8080/swagger-ui/index.html/](http://localhost:8080/swagger-ui/index.html) docs for testing endpoints.

![API Documentation](assets/images/swagger.png)


---

## **8. Tests**
Tests can be ran by the following means below
1 Terminal
2 IntelliJ IDE


1. Terminal

1In the root of (factory directory), run command mvn test

```bash
  mvn test
```

![tests-terminal](assets/images/tests.png)

---

2. IntelliJ IDE
1 Change folder structure to Tests
2 Right click on project folder (factory) --> Navigate to More Run/Debug --> Run all Tests with Coverage

| ![navigate to tests](assets/images/navigate-to-tests-coverage.png) | ![test results with coverage](assets/images/tests-coverage.png) |
| -------------------------------------------- | -------------------------------------------- |
|                                              |                                              |
## **9. License**

[LICENSE](/LICENSE)

---

## **10. References**

* JUnit5 : [JUnit 5 User Guide](https://docs.junit.org/current/user-guide/).
* Symflower : [Symflower feature overview](https://www.youtube.com/watch?v=17KKqlLNcTc).
* TestContainers : [Awesome Readme Templates](https://awesomeopensource.com/project/elangosundar/awesome-README-templates)
* Swagger :  [Swagger API Documentation for Spring Boot 3](https://www.baeldung.com/spring-rest-openapi-documentation)
* Medium : [Handling exceptions and error responses ](https://leejjon.medium.com/handling-exceptions-and-error-responses-in-java-rest-services-afda273c9d2f)
* TutorialsPoint : [JUnit unit testing tool](https://www.tutorialspoint.com/junit/junit_basic_usage.htm)
