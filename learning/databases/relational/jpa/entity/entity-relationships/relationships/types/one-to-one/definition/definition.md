# Definition
**In a relational context, the `@OneToOne` annotation represents a one-to-one relationship with the following characteristics:**
- **At the database level (relational model):**
  - it maps to a one-to-one relationship between two **tables**;
  - this means that one **row** in a table corresponds to **one and only one row** in another table.

<img src="img/one_to_one.png" width="700">

- **At the application level (object-oriented model):**
  - it defines a one-to-one relationship between two **entities** (i.e., **classes**);
  - this means that one **instance** of an entity is associated with **one and only one instance** of another entity.

**Person class:**
```java
import jakarta.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String email;

    // Inverse side of the relationship
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Passport passport;

    // Getters and setters
}
```
**Passport class:**
```java
import jakarta.persistence.*;

@Entity
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "person_id", unique = true)
    private Person person;

    // Getters and setters
}
```