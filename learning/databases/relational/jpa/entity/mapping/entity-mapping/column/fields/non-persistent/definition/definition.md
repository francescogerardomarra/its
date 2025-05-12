# Definition
A **non‑persistent field** (also known as a **transient field**) is a field in a class
that is **not** stored in the database when the object is persisted and is ignored by the persistence provider.

These are useful for holding temporary or derived values that you don’t need to persist.

**To declare a field as non‑persistent, you can:**
- annotate it with `@Transient` from the **Jakarta Persistence** API.

```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username") // Explicitly mapped to the 'username' column
    private String username;   // Persistent field — this will be stored in the DB

    @Transient
    private int sessionDuration; // Non-persistent — ignored by JPA, not stored in DB

}
```