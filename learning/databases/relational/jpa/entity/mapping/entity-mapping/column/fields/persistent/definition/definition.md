# Definition
A persistent field is a field in a class that is stored in a database or another persistent storage.

It is part of the object's state that gets saved when the object is persisted (e.g., via an ORM like JPA).

**Here is an example:**
```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username") // Explicitly mapped to the 'username' column
    private String username;   // Persistent field — this will be stored in the DB
    
}
```
In this case, `username` is a persistent field — when you save a `User` object to
the database, `username` will be saved as a column.

