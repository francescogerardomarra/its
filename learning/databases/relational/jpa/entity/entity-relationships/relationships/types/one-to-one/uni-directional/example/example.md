# Example
**Here is an example of a one-to-one uni-directional relationship:**
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
}
```
**In this example:**
- a `Person` entity has a one-to-one uni-directional
relationship with a `Passport` entity, where the
`passport_id` foreign key is stored in
the `Person` table.
