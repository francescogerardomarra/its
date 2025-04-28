# Example
Here's a simple Java code example demonstrating `CascadeType.PERSIST` and `CascadeType.REMOVE` in action.

**Scenario**:
- a `Department` (parent) can have multiple `Employees` (children);
- when a `Department` is saved, all its `Employees` should also be saved;
- when the `Department` is deleted, its `Employees` should also be removed automatically.
```java
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "department", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Employee> employees = new ArrayList<>();
    
    // Constructors, getters, setters, and helper methods
    public Department() {}
    
    public Department(String name) {
        this.name = name;
    }
    
    public void addEmployee(Employee employee) {
        employee.setDepartment(this);
        this.employees.add(employee);
    }

    // Getters and setters...
}
```
```java
@Entity
class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    // Constructors, getters, setters
    public Employee() {}
    
    public Employee(String name) {
        this.name = name;
    }

    // Getters and setters...
    
    public void setDepartment(Department department) {
        this.department = department;
    }
}
```
```java
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        // Creating a new department
        Department department = new Department("HR");

        // Creating employees and adding them to the department
        Employee emp1 = new Employee("Alice");
        Employee emp2 = new Employee("Bob");
        
        department.addEmployee(emp1);
        department.addEmployee(emp2);
        
        // Persisting the department (which will also persist the employees due to CascadeType.PERSIST)
        em.persist(department);
        
        em.getTransaction().commit();

        // Later, deleting the department (which will also delete the employees due to CascadeType.REMOVE)
        em.getTransaction().begin();
        Department deptToRemove = em.find(Department.class, department.getId());
        em.remove(deptToRemove);
        
        em.getTransaction().commit();
        
        //always close transactions EntityManagerFactory and EntityManager
        em.close();
        emf.close();
    }
}
```
**In this example**:
- `CascadeType.PERSIST`:
  - when `em.persist(department)` is called, the `Department` and all its associated `Employees` are automatically persisted.
- `CascadeType.REMOVE`:
  - when `em.remove(department)` is called, the `Department` and all its associated `Employees` are automatically deleted.