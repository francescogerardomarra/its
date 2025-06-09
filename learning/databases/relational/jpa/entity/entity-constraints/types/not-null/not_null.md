# notNull
## Definition
In a relational context, the `NOT NULL` constraint ensures that a column must have a value — it cannot be left empty (`null`).

At the application level, the `@NotNull` annotation from the **Jakarta Bean Validation API** ensures that the field is not `null` during
validation, typically before persisting the entity.


**Database-level (Postgres):**
```
CREATE TABLE person (
    id SERIAL PRIMARY KEY,

    name VARCHAR(255) NOT NULL CHECK (char_length(name) >= 3),

    age INTEGER CHECK (age >= 18 AND age <= 100),

    email VARCHAR(255) NOT NULL UNIQUE CHECK (
        email ~ '^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$'
    )
);
```
**Application-level (Hibernate/Java 21):**
```java
package com.example;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than or equal to 100")
    private int age;

    @NotNull(message = "Email cannot be null")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Invalid email format"
    )
    @Column(unique = true)  // <-- Enforces UNIQUE at the database schema level
    private String email;

    public Person() {}

    // Getters and setters ...
}
```