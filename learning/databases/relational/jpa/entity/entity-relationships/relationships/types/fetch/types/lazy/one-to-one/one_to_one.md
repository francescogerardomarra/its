# `@OneToOne`
Here’s an example of how lazy loading works in a **one-to-one** relationship between
a `Person` and `Passport` in JPA, using `FetchType.LAZY` to control when the associated entity is loaded.

We'll break it down with code, explain the lazy loading behavior, and show what happens at the database level.

## Example: `Person` and `Passport` with Lazy Loading
In this example, a `Person` has one associated `Passport`, and a `Passport` belongs to one `Person`.

### Entity Definitions
**`Person` Entity (Inverse Side)**

**The `Person` entity defines the inverse side of a one-to-one relationship with `Passport`:**
```java
import jakarta.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Passport passport;

    // Getters and setters
}
```
**`Passport` Entity (Owning Side)**

**The `Passport` entity owns the relationship and defines it using `@OneToOne` with a join column:**
```java
import jakarta.persistence.*;

@Entity
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", unique = true)
    private Person person;

    // Getters and setters
}

```
**Explanation of the Code**
- **The `Person` entity is the inverse side of the one-to-one relationship and uses:**
    ```
    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    ```
    - this ensures the `passport` field is lazily loaded — meaning it's not fetched when
    the `Person` is initially retrieved;
    - a second query is issued only when `getPassport()` is called.
- **the `Passport` entity is the owning side and also uses:**
    ```
    @OneToOne(fetch = FetchType.LAZY)
    ```
    - similarly, the `person` field is not immediately fetched; it's only retrieved upon access.

⚠️ **Note: `@OneToOne` defaults to eager loading, so to use lazy loading, you must explicitly
declare `fetch = FetchType.LAZY`.**

## Demonstration of Lazy Loading in Action
Let’s say we have the following database records:

**Database Tables:**

**`Person` Table**

| id | name     | email            |
|----|----------|------------------|
| 1  | John Doe | john@example.com |

**`Passport` Table**

| id  | person_id |
|-----|-----------|
| 101 | 1         |


### Code Demonstration
```java
// PersonRepository.java
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findById(Long id);
}

// Main.java
public class Main {

    @Autowired
    private PersonRepository personRepository;

    public void demoLazyLoading() {
        // Step 1: Fetch Person
        Person person = personRepository.findById(1L).orElse(null);

        System.out.println("Person fetched: " + person.getName()); // Prints: John Doe

        // Step 2: Now accessing the passport triggers lazy loading
        System.out.println("Passport: " + person.getPassport()); // This triggers a second query
    }
}
```
### Database Behavior
1. Initial Fetch (Lazy)
   
   The `Person` is retrieved without the `Passport`:
   ```
   SELECT * FROM person WHERE id = 1;
   ```
   No `Passport` is fetched at this point.
2. Access Triggers Lazy Load
     
   When `person.getPassport()` is called, Hibernate issues:
   ```
   SELECT * FROM passport WHERE person_id = 1;
   ```
   Only now is the related `Passport` entity fetched from the database.

## What Happens in the Database?
- **Step 1:**
  - the `Person` is loaded with only their own fields;
  - the associated `Passport` remains unloaded due to `FetchType.LAZY`.
- **Step 2:**
  - once the `passport` field is accessed, Hibernate sends a second query to fetch the `Passport`.

## Key Points
- **Lazy Loading:**
  - the associated `Passport` is not fetched when the `Person` is loaded;
  - it’s only fetched when `getPassport()` is called.
- **Reduced Initial Load:**
  - useful when the `Passport` info is not always needed immediately.
- **JPA Provider Note:**
  - some JPA providers (like Hibernate) require additional configuration
  (e.g., bytecode enhancement or proxies) to support lazy loading for `@OneToOne`;
  - if not set up properly, `FetchType.LAZY` may still behave as eager loading.

