# Example
**A bi-directional many-to-many relationship has the following characteristics:**
- both sides are aware of the relationship, and each side holds a reference to the other side, 
allowing navigation from either direction (e.g., in the example below, both the `Student` and
`Course` entities hold references to each other);
- one side is designated as the owning side, which manages the join table and foreign keys, while
the other side is the inverse side (e.g., in the example below, the `Course` entity 
is the owning side because it defines the `@JoinTable` annotation, while the `Student` entity is the inverse side).

**Here is an example of a bi-directional many-to-many relationship:**
```java
@Entity
public class Student {
    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;
}

@Entity
public class Course {
    @Id
    private Long id;
    private String courseName;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students;
}
```

**In this example:**
- the `Student` and `Course` entities have a bi-directional many-to-many relationship;
- the `Student` entity holds a collection of `Course` entities, and the `Course` entity holds a collection of `Student`
  entities, allowing navigation from both sides;
- the `student_course` join table holds the foreign keys `student_id` and `course_id` to establish the many-to-many
  relationship, and either entity can manage the relationship depending on the context.
