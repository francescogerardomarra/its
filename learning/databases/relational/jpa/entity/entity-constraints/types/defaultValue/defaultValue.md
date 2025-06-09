# defaultValue
## Definition
In a relational context, the `DEFAULT` clause assigns a value to a column if no value is explicitly provided during insertion.

At the application level, **Jakarta Persistence (JPA)** and **Java** do not provide a standardized `@Default` annotation.
Instead, default values can be assigned in the Java class directly (field initializer or constructor), but **these are not enforced
at the database level** unless the column is defined with a `DEFAULT` clause in SQL or schema generation.

**Database-level (Postgres):**
```
CREATE TABLE person (
    id SERIAL PRIMARY KEY,

    name VARCHAR(100) NOT NULL DEFAULT 'Unknown',

    age INTEGER DEFAULT 30 CHECK (age >= 0 AND age <= 120),

    email VARCHAR(255) NOT NULL UNIQUE CHECK (
        email ~ '^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$'
    ),

    subscribed BOOLEAN DEFAULT TRUE
);
```
- the `DEFAULT` keyword sets fallback values if no explicit input is provided during insertion;
- these defaults are enforced by the database and apply even if the application omits values.

**Application-level (Hibernate/Java 21):**
```java
package com.example;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Field-level default in Java
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100)
    private String name = "Unknown";

    @Min(value = 0)
    @Max(value = 120)
    private int age = 30;  // Default age

    @NotNull
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
        message = "Invalid email format"
    )
    @Column(unique = true)
    private String email;

    private boolean subscribed = true;  // Default subscription status

    public Person() {}

    // Getters and setters ...
}
```
### Notes:
- Java field defaults are applied when no value is set during object instantiation;
- to ensure alignment with the database, it's best to configure both Java field defaults and SQL `DEFAULT` clauses;
- JPA schema generation may not include `DEFAULT` clauses automatically — these usually need to be defined in the DB.