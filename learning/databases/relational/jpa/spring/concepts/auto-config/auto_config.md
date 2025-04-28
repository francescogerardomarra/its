# Autoconfiguration
**Autoconfiguration in Spring Boot has the following characteristics:**
- it automatically sets up necessary components 
based on the libraries you add and settings in `application.properties` 
or `application.yml`;
- for example, if you include a database dependency, Spring Boot
detects it and configures a [datasource](../datasource/datasource.md) using default settings;
- you can customize this setup by specifying your own properties through the `application.properties` file,
reducing the need for manual configuration.
