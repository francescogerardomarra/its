# Example
**A bi-directional one-to-many relationship has the following characteristics:**
- both the "one" and "many" sides know about the relationship;
- the "many" side owns the relationship and holds the foreign key;
- the "one" side references the relationship through the `mappedBy` attribute.

**Here is an example of a bi-directional one-to-many relationship:**
```java
@Entity
public class Department {
    @Id
    private Long id;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}

@Entity
public class Employee {
    @Id
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
```
**In this example:**
- the `Employee` entity has a bi-directional many-to-one relationship with the `Department` entity, 
where the `department_id` foreign key is stored in the `Employee` table;
- the `Employee` entity is the owning side because it holds the foreign key, while the
`Department` entity is the inverse side, using the `mappedBy` attribute in its `employees` 
field to refer to the `department` field in the `Employee` entity;
- the relationship can be navigated from both sides: `department.getEmployees()` and `employee.getDepartment()`.
