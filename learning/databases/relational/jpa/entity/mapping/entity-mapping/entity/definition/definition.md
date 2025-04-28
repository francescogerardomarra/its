# Definition
**The `@Entity` annotation has the following characteristics:**
- it is used to mark a Java class as a JPA entity, meaning it
will be mapped to a table in the database;
- each instance of this class represents a row in the database table;
- this annotation is typically placed above the class declaration.

**Here is an example:**
```java
import jakarta.persistence.Entity;

@Entity
public class User {
    // class body
}
```
