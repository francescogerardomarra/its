# Example
**In this example**:
- we will use JPA (Java Persistence API) annotations to define an entity class with persistent fields.
```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    // Default constructor
    public Person() {}

    // Getters and setters (optional, for application use)
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
}
```
**Explanation**
- **`@Entity` Annotation**: `@Entity` marks the class as a JPA entity.
- **`@Id` and `@GeneratedValue` Annotations**: `@Id` and `@GeneratedValue` specify the primary key and its generation strategy.
- **`@Column` Annotation**: `@Column` specifies column details; here, `nullable = false` means
the column cannot be `null`.
- **Direct Access**: fields are directly accessed by the persistence runtime.