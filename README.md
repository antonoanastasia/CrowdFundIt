# CrowdfundIt
Crowdfunding Management Platform

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Roles and Functions](#roles-and-functions)
  - [Administrator](#administrator)
  - [Creator](#creator)
  - [Backer](#backer)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Clone the Repository](#clone-the-repository)
  - [Open the Project in IDE](#open-the-project-in-ide)
  - [Configure the Database](#configure-the-database)
  - [Run the Application](#run-the-application)
  - [Access the Web Application](#access-the-web-application)

---

## Overview
**CrowdfundIt** είναι πανεπιστημιακή εργασία στο μάθημα *Κατανεμημένα Συστήματα* (Χειμερινό εξάμηνο 2024–2025).  
Αποτελεί μια web-based πλατφόρμα που επιτρέπει τη δημιουργία, διαχείριση και χρηματοδότηση έργων μέσω crowdsourcing.  
Το σύστημα παρέχει διαφορετικά dashboards και λειτουργίες για κάθε ρόλο: **Administrator**, **Creator**, **Backer**.

---
### Homepage  
![Homepage](src/main/resources/static/img/homepage.png) 

---
## Features
- **Project Creation & Submission**: Οι δημιουργοί καταχωρούν έργα προς έγκριση.  
- **Project Approval**: Ο admin εγκρίνει ή απορρίπτει έργα πριν εμφανιστούν δημόσια.  
- **Funding**: Οι backers μπορούν να χρηματοδοτούν έργα με pledge ποσά.  
- **Progress Tracking**: Προβολή στόχου, ποσού που έχει συγκεντρωθεί και κατάστασης.  
- **Role-Based Dashboards**: Κάθε ρόλος έχει το δικό του dashboard (Admin, Creator, Backer).  
- **Notifications & Updates**: Οι creators μπορούν να ενημερώνουν backers για την πρόοδο.  

---

## Roles and Functions

### Administrator
- Εγκρίνει ή απορρίπτει νέα έργα.  
- Διαχειρίζεται χρήστες και το σύνολο της πλατφόρμας.  
- Παρακολουθεί την εξέλιξη όλων των έργων.  

### Creator
- Υποβάλλει έργα για έγκριση.  
- Διαχειρίζεται τα δικά του έργα μέσω του Creator Dashboard.  
- Ενημερώνει τους backers για την πρόοδο.  

### Backer
- Αναζητά έργα που βρίσκονται σε χρηματοδότηση.  
- Υποστηρίζει έργα με pledge ποσά.  
- Παρακολουθεί έργα που έχει υποστηρίξει μέσω του Backer Dashboard.  

---

## Getting Started

### Prerequisites
- **Java Development Kit (JDK 22)**  
- **Maven** (για build)  
- **IDE** όπως IntelliJ IDEA  
- **Web Browser** (π.χ. Chrome)  
- **PostgreSQL Database** (local ή cloud π.χ. Render)  

### Installation

#### Clone the Repository
```bash
git clone https://github.com/antonoanastasia/CrowdFundIt
```

#### Open the Project in IDE
- Άνοιξε το project στο IntelliJ IDEA ή σε άλλο IDE.  
- Βεβαιώσου ότι έχεις Maven build.  

#### Configure the Database
Στο αρχείο `application.properties`:  
```properties
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.datasource.url=jdbc:postgresql://YOUR_DB_HOST:YOUR_DB_PORT/YOUR_DB_NAME
```

#### Build the Project
```bash
mvn clean install
```

#### Run the Application
```bash
java -jar target/your-app-name.jar
```

#### Access the Web Application
Άνοιξε browser και πήγαινε στο:  
```
http://localhost:8080/
```
