# enumerated
## Definition
In a relational context, an `ENUM` type restricts the values in a column to a predefined set — only one of the listed values is allowed.

At the application level, the `@Enumerated` annotation from **Jakarta Persistence API (JPA)** maps a Java `enum` to a database column.
You can store either the enum’s **name** or its **ordinal** (index), but storing the name is recommended for clarity and stability.

**Database-level (Postgres):**
```
-- First, define the ENUM type
CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE', 'OTHER');

-- Then, use it in a table
CREATE TABLE person (
    id SERIAL PRIMARY KEY,

    name VARCHAR(100) NOT NULL CHECK (char_length(name) >= 3),

    age INTEGER CHECK (age >= 18 AND age <= 100),

    email VARCHAR(255) NOT NULL UNIQUE CHECK (
        email ~ '^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$'
    ),

    gender gender_enum NOT NULL
);
```
- the `gender_enum` limits the column to only **'MALE', 'FEMALE', or 'OTHER'**;
- this ensures strong type safety at the database level — invalid values are rejected outright.

**Application-level (Hibernate/Java 21):**
```java
package com.example;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Person {

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than or equal to 100")
    private int age;

    @NotNull(message = "Email cannot be null")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Invalid email format"
    )
    @Column(unique = true)
    private String email;

    @NotNull(message = "Gender cannot be null")
    @Enumerated(EnumType.STRING)  // Store as 'MALE', 'FEMALE', etc.
    private Gender gender;

    public Person() {}

    // Getters and setters ...
}
```
### Notes:
- `@Enumerated(EnumType.STRING)` stores the enum name as a string — this is safer than using ordinal indexes;
- PostgreSQL `ENUM` types must be created explicitly before use — they aren't generated automatically;
- changes to enum values (e.g., adding/removing options) must be reflected both in Java and in the DB schema.