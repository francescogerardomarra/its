### Nested Transactions

### Scenario
You're building a user import system.  
You want to **process a list of users** and insert them into the database, but some users may have issues, such as missing email addresses, that would cause the insertion to fail.  
You still want to **ensure that all valid users are saved**, even if a single user causes an error.

That’s where **nested transactions** come in handy.

```java
@Service
public class UserImportService {

    @Autowired
    private UserService userService;

    @Transactional
    public void importUsers(List<User> users) {
        for (User user : users) {
            try {
                userService.createUser(user);
            } catch (Exception e) {
                // Log and continue — individual user failed but batch continues
                System.err.println("Failed to import user: " + user.getName());
            }
        }
    }
}
```
```java
@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Transactional(propagation = Propagation.NESTED)
    public void createUser(User user) {
        // Simulate validation failure
        if (user.getEmail() == null) {
            throw new IllegalArgumentException("Email is required");
        }

        userRepo.save(user);
    }
}
```
### What Happens Here:

- `importUsers()` starts a transaction;
- for each user, `createUser()` is called, but with `PROPAGATION_NESTED`, so it starts **its own transaction**;
- if `createUser()` fails for a particular user, it will **only roll back that transaction** (the "nested" transaction), while the **outer transaction** continues to process the remaining users;
- the **importUsers()** transaction is committed only after all valid users are processed.

This is useful when you want to **isolate** part of a larger process and allow failures in smaller parts (like invalid users) while still ensuring other operations (like valid user imports) succeed.
