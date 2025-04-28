# Mapping entity relationships
**Here's an example of how you can map entity relationships in Java using JPA annotations:**
1. **One-to-One Relationship**
   - in a one-to-one relationship, each record in one table is associated with exactly one record in another table.
```java
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Address address;
    
    // Getters and setters
}

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String city;
    
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;
    
    // Getters and setters
}
```
2. **One-to-Many Relationship**
   - in a one-to-many relationship, one record in one table is associated with multiple records in another table.
```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees;
    
    // Getters and setters
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    // Getters and setters
}
```
3. **Many-to-One Relationship**
   - in a many-to-one relationship, multiple records in one table are associated 
   with exactly one record in another table.
```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;
    
    // Getters and setters
}

@Entity
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<Student> students;
    
    // Getters and setters
}
```
4. **Many-to-Many Relationship**
   - in a many-to-many relationship, multiple records in one table are associated with 
   multiple records in another table.
```java
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
    
    // Getters and setters
}

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
    
    // Getters and setters
}
```