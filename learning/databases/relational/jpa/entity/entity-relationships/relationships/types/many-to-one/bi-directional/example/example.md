# Example
**A bi-directional many-to-one relationship has the following characteristics:**
- both the "many" side and the "one" side are aware of the relationship;
- the "many" side (owning side) holds the foreign key, and the "one" side (inverse side) 
can access the "many" side through a collection.

**Here is an example of a bi-directional many-to-one relationship:**
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

    @OneToMany(mappedBy = "department")  // Mapped by the 'department' field in Employee
    private List<Employee> employees;
}
```
**In this example:**
- the `Employee` entity has a many-to-one bi-directional relationship with the `Department` entity,
  where the `department_id` foreign key is stored in the `Employee` table;
- the `Employee` entity knows about the relationship through its `department`
field, and the `Department` entity knows about the relationship through its `employees` collection.
