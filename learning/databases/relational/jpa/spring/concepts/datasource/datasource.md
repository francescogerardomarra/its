# Datasource
**A datasource in Spring has the following characteristics:**
- it refers to a configuration object that connects your application to the database;
- it typically holds the database URL, username, password, and other connection properties;
- this can be configured in `application.properties` or `application.yml` files or can
be created programmatically.

**Here is an example of a `application.properties` file:**
```.properties
# Database connection settings
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=postgres
spring.datasource.password=secret
spring.datasource.driver-class-name=org.postgresql.Driver
```