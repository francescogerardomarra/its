# Definition
**The `@Id` annotation has the following characteristics:**
- it marks the primary key of the entity;
- it is typically applied to a field or property that
  uniquely identifies each entity instance.

**Here is an example:**
```java
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id
  private Long id;
}

```