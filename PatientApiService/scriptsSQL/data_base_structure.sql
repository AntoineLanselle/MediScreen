DROP DATABASE PatientApi;

CREATE DATABASE PatientApi;

USE PatientApi;

/* -- Création des differentes table. -- */

CREATE TABLE Patient (
id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
firstname VARCHAR(255) NOT NULL,
lastname VARCHAR(255) NOT NULL,
date_of_birth DATE NOT NULL,
gender VARCHAR(255) NOT NULL,
address VARCHAR(255),
phone VARCHAR(255)
);