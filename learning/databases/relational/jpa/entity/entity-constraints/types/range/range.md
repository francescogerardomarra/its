# range
## Definition
In a relational context, the `CHECK` constraint enforces that a numeric column's value falls within
a specific range — e.g., between a minimum and maximum value.

At the application level, the `@Min` and `@Max` annotations from the **Jakarta Bean Validation API** ensure that
a numeric field is within a valid range **before** it is persisted or processed in the system.

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
- the `CHECK (age >= 18 AND age <= 100)` ensures that the value for `age` is within the acceptable range;
- this rule is enforced directly by the database at the time of insertion or update — if violated, the operation fails.

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
### Notes:
- `@Min` and `@Max` act as **pre-validation guards** — useful during API validation, form submission, or data binding;
- `CHECK` at the database level guarantees consistency even if the validation annotations are bypassed or forgotten;
- always combine both layers to enforce business rules **and** protect against invalid data at the persistence level.