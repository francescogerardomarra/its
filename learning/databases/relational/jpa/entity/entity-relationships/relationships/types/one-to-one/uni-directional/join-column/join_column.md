# `@JoinColumn`
**The `@JoinColumn` annotation has the following characteristics:**
- it is used to specify the details of a foreign key column
in a relationship between two entities;
- it defines how the foreign key column in
the database maps to the fields or properties of the entity classes.
- **Purpose**:
    - the `@JoinColumn` annotation specifies the column that joins two tables in a database,
      creating a relationship between two entities.
- **Placement**:
    - it is used on the owning side of the relationship;
    - the owning side is the entity that contains the foreign key.

**Here is an example**:
```java
import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    // Getters and setters
}

@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String passportNumber;

    @OneToOne
    private Person person;

    // Getters and setters
}
```