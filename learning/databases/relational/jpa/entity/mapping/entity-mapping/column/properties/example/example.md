# Example
**In this example**:
- we will define an entity class with persistent properties (getter and setter methods).

```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class Person {
    private Long id;
    private String name;
    private int age;

    // Default constructor
    public Person() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
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
- **`@Id` and @GeneratedValue Annotations**:
  - `@Id` and `@GeneratedValue` specify the primary key and its generation strategy;
  - these annotations are applied to the getter method `getId()`.
- **`@Column` Annotation**:
  - `@Column` specifies column details; here, `nullable = false` means the column cannot be `null`;
  - these annotations are applied to the getter methods `getName()` and `getAge()`.
- **JavaBeans Conventions**: properties are accessed through getter and setter methods.