# Example
**A uni-directional many-to-one relationship has the following characteristics:**
- only the "many" side knows about the relationship (owning side);
- the "one" side does not have any reference back to the "many" side (inverse side).

**Here is an example of a uni-directional many-to-one relationship:**
```java
@Entity
public class Employee {
    @Id
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")  // The foreign key column in the Employee table
    private Department department;
}

@Entity
public class Department {
    @Id
    private Long id;
    private String departmentName;
}
```
**In this example:**
- the `Employee` entity has a many-to-one uni-directional relationship with the `Department` entity,
  where the `department_id` foreign key is stored in the `Employee` table;
- the `Employee` entity knows about the relationship through its `department`
field, but the `Department` entity does not reference the `Employee` entity.
