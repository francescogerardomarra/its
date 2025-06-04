# notNull


```java
package com.example;

import jakarta.validation.constraints.*;  // Importing validation annotations from Jakarta Validation API

// This class represents a Person entity that is used in JSON deserialization
// It includes validation constraints to ensure that the data is correct before processing.
public class Person {

    // The name field is validated with two annotations:
    // - @NotNull: Ensures that the 'name' field is not null.
    // - @Size(min = 3): Ensures that the 'name' field is at least 3 characters long.
    // If these conditions are not met, a validation message will be provided.
    @NotNull(message = "Name cannot be null")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    // The age field is validated with two annotations:
    // - @Min(value = 18): Ensures that the 'age' field is at least 18.
    // - @Max(value = 100): Ensures that the 'age' field is no greater than 100.
    // If the age does not meet these constraints, a validation message will be provided.
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than or equal to 100")
    private int age;

    // The email field is validated with two annotations:
    // - @NotNull: Ensures that the 'email' field is not null.
    // - @Pattern(regexp = ...): Ensures that the 'email' follows a valid email format.
    // The regular expression defines a simple email pattern that allows alphanumeric characters and common symbols.
    // If these conditions are not met, a validation message will be provided.
    @NotNull(message = "Email cannot be null")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    private String email;

    // Default constructor for deserialization (necessary for some deserialization frameworks like Jackson)
    public Person() {}

    // Getter for 'name' field
    public String getName() {
        return name;
    }

    // Setter for 'name' field
    public void setName(String name) {
        this.name = name;
    }

    // Getter for 'age' field
    public int getAge() {
        return age;
    }

    // Setter for 'age' field
    public void setAge(int age) {
        this.age = age;
    }

    // Getter for 'email' field
    public String getEmail() {
        return email;
    }

    // Setter for 'email' field
    public void setEmail(String email) {
        this.email = email;
    }
}
```