# notBlank
## Definition
In a relational context, ensuring that a string column is not empty or just whitespace is **not natively supported** by SQL alone.
Instead, this logic must be enforced using a `CHECK` constraint that verifies the trimmed string is not empty.

At the application level, the `@NotBlank` annotation from **Jakarta Bean Validation** ensures that a string is not `null`, not empty,
and does not consist solely of whitespace — making it stricter than `@NotNull`.

**Database-level (Postgres):**
```
CREATE TABLE person (
    id SERIAL PRIMARY KEY,

    username VARCHAR(100) NOT NULL CHECK (trim(username) <> ''),

    email VARCHAR(255) NOT NULL UNIQUE CHECK (
        email ~ '^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$'
    )
);

```
- `trim(username) <> ''` ensures the column value is not empty or just whitespace;
- this constraint is enforced at the database level during insertion or update.

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

    // Ensures the username is not null, empty, or only whitespace
    @NotBlank(message = "Username must not be blank")
    @Size(max = 100)
    private String username;

    @NotNull(message = "Email cannot be null")
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
        message = "Invalid email format"
    )
    @Column(unique = true)
    private String email;

    public Person() {}

    // Getters and setters ...
}
```

### Notes:
- `@NotBlank` is commonly used for form input validation;
- at the database level, this requires manual `CHECK` constraints using `trim()` or similar functions;
- unlike `@NotNull`, `@NotBlank` is specific to String fields and validates content, not just nullability.