
### Description

Service for shorting links on Java Spring 3 framework.

### Implemented features:

- Shorting link
- Getting full link from shortend link
- Converter Number - Base62
- Cache for shortened links

### Technologies

- Java
- Spring framework 3
- Hibernate
- PostgreSQL
- JUnit 5

### How to start

- Clone repository from Github:

    ```https://github.com/Art3m1y/LinkCutter.git```

- Setup Java Development Kit

- Compile and package the application to an executable JAR

- mvn package

- Run backend part of project (executable JAR)

    ```java -jar link-cutter.jar```

If you have followed all the steps, the backend will be available at port, that you have written

### Backend endpoints

- For cut link you need send post request on this endpoint. In body of request you need write _fullLink_ and _hoursInExpires_. In reply you get shortened link on you full link.

  ```http://localhost:<yourport>/cut```

### SQL statements script for correct project work

You can find SQL statements script for correct project work on this path: ./backend/src/main/resources/statements.sql
