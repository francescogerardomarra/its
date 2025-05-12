# Example
**In this version, we add a field that will not be stored in the database, using the `@Transient` annotation.**
```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Transient
    private String sessionToken; // Non-persistent — not stored in the database

    // Default constructor
    public Person() {}

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
```
## Explanation of the Change

### `@Transient` Field (`sessionToken`):
- this field is **excluded from the database schema**;
- it's useful for **temporary data** such as tokens, flags, or UI-specific states;
- JPA **ignores it completely** during persistence operations (e.g., `save()`, `findById()`).
