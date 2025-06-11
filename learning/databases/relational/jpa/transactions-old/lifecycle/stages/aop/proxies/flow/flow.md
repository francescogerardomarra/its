# Proxy flow
**Here is an example of a service with a method marked in a transactional context through the `@Transactional` annotation:**
```java
@Service
public class PaymentService {

    @Transactional
    public void processPayment() {
        // do some DB operations
    }
}
```
**Behind the scenes:**
- Spring creates a proxy of `PaymentService`;
- the proxy intercepts method calls and checks if the calling method uses `@Transactional` or similar annotations;
- when `processPayment()` is called, the proxy:
  - **opens a transaction**;
  - **calls** the actual original method (not the proxy's method);
  - **commits** if successful, or **rolls back** if an exception is thrown.

This is AOP in action—you didn’t write any transaction code, but it's applied around your method like an "advice."

