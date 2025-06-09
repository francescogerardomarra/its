# unique
## Definition
In a relational context, the `UNIQUE` constraint ensures that all values in a column (or a combination of columns) are distinct
— no two rows can have the same value in that column(s).

At the application level, the `@Column(unique = true)` annotation in **JPA/Hibernate** defines this uniqueness at the schema-generation level.
However, it does **not enforce** uniqueness during Java object validation — you would need to handle that logic in your service 
layer or by catching database exceptions during persistence.

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
- the `UNIQUE` keyword applied to email ensures that no two people can share the same email address;
- this is enforced by the database engine, independently of the application.

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
- `@Column(unique = true)` does not guarantee uniqueness during runtime validation (like `@NotNull` does);
- the uniqueness is enforced when Hibernate generates the DDL or when constraints are checked in the database.