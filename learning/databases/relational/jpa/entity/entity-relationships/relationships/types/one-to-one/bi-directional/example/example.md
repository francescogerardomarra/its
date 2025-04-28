# Example
**Here is an example of a one-to-one bi-directional relationship:**
```java
@Entity
public class Person {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "passport_id")
    private Passport passport;
}

@Entity
public class Passport {
    @Id
    private Long id;
    private String passportNumber;

    @OneToOne(mappedBy = "passport")
    private Person person;
}
```
**In this example:**
- **`Person` entity:**
  - the `Person` entity owns the relationship and holds 
  the foreign key column (`passport_id`) in the `Person`
  table;
  - this is indicated by the `@JoinColumn`
  annotation, which creates the foreign key in 
  the `Person` table.
- **`Passport` entity:**
  - the `Passport` entity is on the inverse side
  of the relationship;
  - it uses the `mappedBy` attribute to refer to 
  the `passport` field in the `Person` entity, 
  indicating that the `Person` entity is responsible 
  for managing the relationship.

