# Project Title

OOP Coursework - Real-Time Event Ticketing System

## Description

This guide aims to provide a detailed explanation of how to prepare and deploy each component of the Real-Time Event Ticketing System as per the coursework specification.
The focus is on utilizing Object-Oriented Programming (OOP) principles and the Producer-Consumer pattern to simulate a dynamic ticketing environment.
The system handles concurrent ticket releases and purchases while maintaining data integrity.

## Prerequisites

Backend (Spring Boot):
    Java Development Kit (JDK):
    Version: JDK 11 or higher.
    Verify installation: java -version in your terminal.
    Maven or Gradle (Build Tool):
    Maven: Comes bundled with most IDEs.
    Verify Maven: mvn -version.
    Integrated Development Environment (IDE):
    IntelliJ IDEA.

Frontend (Angular):
    Node.js and npm:
    Download and install from Node.js Official Site.
    Verify installation:
    node -v for Node.js version.
    npm -v for npm version.
    Angular CLI:
    Install globally: npm i @angular/cli@16.0.0
    Verify installation: ng version.

Database (MySQL):
Relational Database:
Install MySQL.
Database Client:
MySQL Workbench.

## Getting Started

Local Setup
Start the backend (Spring Boot) on http://localhost:8080.
Start the frontend (Angular) on http://localhost:4200.

Database Preparation
Preload the database with realistic, sample data that aligns with your demo scenarios.

## Usage Instructions

Frontend 
    Start the System:
    Show the initial Stopped status.
    Click the "Start" button to change the status to Running.

    Modify Configuration:
    Edit values in the configuration form.
    Save the configuration and show the updated values.

    Stop the System:
    Click the "Stop" button and demonstrate the Stopped status.

Backend 
    System Status Monitoring:
    Display the current system status (Running/Stopped).
    Show the dynamic updates when the system starts or stops.

    Configuration Management:
    Saving configured values.
    Validate for required fields.

    Backend Integration:
    Frontend interacts with the backend using APIs.
    Showcase real-time feedback on backend logs.
