# Application-level
Transactions can also be managed by the application logic, which interacts with the database
using client libraries or ORM frameworks.

In Java applications using **Spring JPA**, transaction management is often handled declaratively 
using the `@Transactional` annotation.
This is typically applied to **service layer methods**, where the business logic resides.

Here's an example using **Spring Boot** with **JPA repositories**:
```java
@Service
public class TransferService {

    private final AccountRepository accountRepository;

    @Autowired
    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transferFunds(Long senderId, Long receiverId, BigDecimal amount) {
        Account sender = accountRepository.findById(senderId)
            .orElseThrow(() -> new RuntimeException("Sender not found"));
        Account receiver = accountRepository.findById(receiverId)
            .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);
    }
}
```
When using application-level transaction management, such as the `@Transactional` annotation
in Spring JPA, it's important to understand that this is not replacing the database's transaction 
mechanism — it's orchestrating it.

**What Happens Under the Hood:**
- the `@Transactional` annotation **does not create a new kind of transaction logic**;
- instead, it **delegates** transaction control to the underlying database through JDBC or another
persistence provider;
- at runtime, Spring opens a **database transaction** at the beginning of the annotated method 
and **commits or rolls back** the transaction based on whether the method completes successfully 
or throws an exception.

This means that a method annotated with `@Transactional` ultimately results in a database-level 
transaction, which behaves similarly to a transaction block written directly in a database-specific
procedural language like **PL/pgSQL** (PostgreSQL) or **PL/SQL** (Oracle).

| Application-Level (`@Transactional`)        | Database-Level (PL/pgSQL)                    |
|---------------------------------------------|----------------------------------------------|
| Written in Java/Kotlin (Spring, Jakarta EE) | Written in procedural SQL (PL/pgSQL, PL/SQL) |
| Uses annotations (`@Transactional`)         | Uses `BEGIN`, `COMMIT`, `ROLLBACK` logic     |
| Managed by the application framework        | Managed directly by the database engine      |
| Automatically rolled back on exceptions     | Requires explicit exception handling         |


Even though the code looks different on the surface, the **transactional behavior is executed
by the same underlying database engine**. Whether initiated by Java application code or by SQL
procedures, the database handles consistency, atomicity, and rollback/commit in the same way.

This abstraction allows developers to write transactional business logic in the application code
without directly managing database-level syntax — while still leveraging the robustness of the
database’s transaction system.