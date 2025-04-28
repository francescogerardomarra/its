# Example 
**A uni-directional one-to-many relationship has the following characteristics:**
- only the "one" side knows about the relationship (owning side);
- the "many" side does not have any reference back to the "one" side (inverse side).

**Here is an example of a uni-directional one-to-many relationship:**
```java
@Entity
public class Department {
    @Id
    private Long id;

    @OneToMany
    @JoinColumn(name = "department_id")  // The foreign key column in the Employee table
    private List<Employee> employees;
}

@Entity
public class Employee {
    @Id
    private Long id;
    private String name;
}
```
**In this example:**
- the `Department` entity has a one-to-many uni-directional relationship with the `Employee` entity,
where the `department_id` foreign key is stored in the `Employee` table;
- the `Department` entity knows about the relationship through its 
`employees` field, but the `Employee` entity does not reference the `Department` entity.

