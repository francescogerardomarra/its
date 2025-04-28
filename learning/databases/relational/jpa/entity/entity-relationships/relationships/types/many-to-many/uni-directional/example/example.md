# Example
**A uni-directional many-to-many relationship has the following characteristics:**
- only one side, called the owning side, knows about and manages the relationship, including 
handling the join table and foreign keys (e.g., in the example below, the `Student` entity is the owning side);
- the other side, called the inverse side, does not have any reference back to the
owning side and does not manage the relationship (e.g., in the example below, the `Course` entity is the inverse side).

**Here is an example of a uni-directional many-to-many relationship:**
```java
@Entity
public class Student {
    @Id
    private Long id;
    private String name;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
}

@Entity
public class Course {
    @Id
    private Long id;
    private String courseName;
}
```
**In this example:**
- the `Student` entity has a many-to-many uni-directional relationship with the `Course` entity, where the relationship is managed by the `Student`;
- the `Student` entity holds a collection of `Course` entities, but the `Course` entity does not maintain any reference to the `Student`;
- the `student_course` join table holds the foreign keys `student_id` and `course_id` to establish the many-to-many relationship.
