# `@Table`
**The `@Table` annotation has the following characteristics:**
- this annotation specifies the details of the database
  table to which the entity is mapped;
- it allows you to define the table name, schema, and other properties.

**Here is an example:**
```java
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  // class body
}
```