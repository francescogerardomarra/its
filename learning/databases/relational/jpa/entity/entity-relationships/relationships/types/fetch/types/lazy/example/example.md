# Example
**Here is an example of the use of `FetchType.LAZY` in a user management system:**
```java
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private UserProfile profile;

    // Getters and setters
}
```
**In this example:**
- it defines a `User` entity that has a one-to-one relationship with a `UserProfile` entity;
- it uses `FetchType.LAZY` to defer the loading of the associated `UserProfile` until it is accessed for the first time;
- it improves performance by avoiding unnecessary data retrieval when the `User` is retrieved but the `UserProfile` is not needed immediately.
