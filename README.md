Show Me The Money

A Budgeting Application


Updated 2/23/25

The folder "app" includes a Spring Boot project initialized with base dependencies to run the application. See the POM.XML file for details.

The file / folder structure includes src > main> java | resources

Java includes a top level package com.budget.app. 

The underlying packages support a Spring framework web MVC architecture split into controller | domain | entity | repository | service and top level app runner and servlet.

Resources include static | templates | application.properites | data.sql | schema.sql. 

  Static stores images, videos, css, and other static assets for display. 
  
  Templates store a series of .html files integrated with Thymeleaf templating.
  
  Application.properties include a series of settings and configuration the current version largely defines the H2 in memory embedded database. 
  
  Data.sql and Schema.sql provide at runtime configuration of data and database tables. 
  
  Given the JPA Hibernate dependencies on the project the "Entity" classes will be used by H2 to generate table structure.


Before the project is open following this repos standard from your local cps298 folder

-Git branch
 - Confirm you are in the main branch

-Git pull main

-Git branch
 - Confirm you have a named branch. If not Git branch <<enter firstname>>

-Git checkout branchname

-Git merge main
 - While on your named feature branch


RUN AND VIEW THE APP
From IntelliJ or Eclipse with the project open
To run the application open the AppApplication class and click the Run button.
Open a browser tab and enter http://localhost:8080/
You should see 

![image](https://github.com/user-attachments/assets/631d1b98-14cc-434d-b92c-58833dec8dbe)

VIEW AND QUERY THE H2 DB
While the project is running open a browser tab and enter http://localhost:8080/h2-console
Leave the saved settings and setting name as Generic H2 (Embedded).
Enter the Driver Class, JDBC URL, Username, Password. If you need this information contact Josh McGuire.
![image](https://github.com/user-attachments/assets/2c15416d-f19e-4bf5-b4f6-847b379f1da1)

