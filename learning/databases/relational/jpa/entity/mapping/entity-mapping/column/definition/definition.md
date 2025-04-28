# Definition
**The `@Column` annotation has the following characteristics:**
- it is used to map a field or property
  of the entity to a column in the corresponding database
  table;
- it allows you to specify various column attributes
  such as `name`, `length`, `nullable`, etc.

**Here is an example of a field case:**
```java
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id
  private Long id;  
    
  @Column(name = "user_name", length = 50, nullable = false)
  private String userName;

  // other fields and methods
}
```