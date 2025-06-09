# pattern
## Definition
In a relational context, a `CHECK` constraint with a regular expression (`~`) in **PostgreSQL** ensures
that a column’s value matches a specific pattern — typically for formats like emails, phone numbers, or identifiers.

At the application level, the `@Pattern` annotation from the **Jakarta Bean Validation API** ensures that a `String`
field matches a specified regular expression **before** the data is persisted or further processed.

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
- the `CHECK (... ~ 'regex')` syntax in PostgreSQL enforces that values in the email column match the specified pattern;
- in this case, the regular expression ensures that email resembles a valid address format;
- this rule is enforced at the database level, preventing invalid rows from being inserted directly.

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
- `@Pattern` allows for custom validation at the Java level, ideal for form fields, REST APIs, and DTO validation;
- the PostgreSQL `CHECK` with `~` offers backend protection — even if Java validation is bypassed;
- Regex support in PostgreSQL is powerful, but syntax may vary slightly from Java regex rules — test expressions in both environments.