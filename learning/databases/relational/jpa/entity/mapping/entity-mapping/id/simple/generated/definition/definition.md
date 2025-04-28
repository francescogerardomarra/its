# Definition
**The `@GeneratedValue` annotation has the following characteristics:**
- specifies the strategy used to generate values 
for the primary key automatically;
- it's often used in conjunction with `@Id`.

**Here is an example:**
```java
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
```