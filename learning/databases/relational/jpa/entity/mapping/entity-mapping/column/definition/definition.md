# Definition
**The `@Column` annotation has the following characteristics:**
- it is used to map a field or property
  of the entity to a column in the corresponding database
  table;
- it allows you to specify various column attributes
  such as `name`, `length`, `nullable`, etc.

**Here is an example of a field case:**
<div style="display: flex; justify-content: space-between; margin: 0 -5px;">
  <div style="width: 50%; margin: 0 5px;">

**CODE:**
```java
package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User entity representing a user in the shop system.
 */
@Getter // Lombok annotation to generate getter methods
@Setter // Lombok annotation to generate setter methods
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@Entity // Marks this class as a JPA entity
@Table(name = "User", schema = "shop_schema") // Maps this entity to the "User" table in "shop_schema"
public class User {

  /**
   * Primary key, automatically generated.
   */
  @Id // Marks this field as the primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies auto-incrementing primary key generation
  @Column(name = "user_id")
  private Integer userId;

  /**
   * Unique username for the user.
   */
  @Column(name = "username", nullable = false, unique = true, length = 50)
  // Ensures this column is non-null and unique
  private String username;

  /**
   * Unique email for the user.
   */
  @Column(name = "email", nullable = false, unique = true, length = 100) // Ensures this column is non-null and unique
  private String email;

  /**
   * Timestamp of user creation.
   */
  @Column(name = "created_at", nullable = false, updatable = false)
  // Prevents updates and ensures non-null constraint
  private LocalDateTime createdAt = LocalDateTime.now();

  /**
   * One user can have many orders.
   * The `mappedBy = "user"` indicates that the `Order` entity owns the relationship,
   * with the `user` field in the `Order` entity holding the foreign key reference to `User`.
   */
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Order> orders;
}
```

  </div>
  <div style="width: 50%; margin: 0 5px;">

**DDL:**
```
CREATE TABLE shop_schema.User (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

  </div>
</div>
