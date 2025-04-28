# The Proxy is around the Bean, not just the method
**When Spring sees something like:**
```java
@Service
public class PaymentService {
    
    @Transactional
    public void pay() {
        // ...
    }
}
```
Spring doesn't just wrap the `pay()` method. It wraps the entire `PaymentService` bean in a proxy object.

That proxy contains logic to **intercept method calls** and apply behaviors like transactions, logging, etc.

**So:**
- you call a method on the bean → you're actually calling it on the proxy;
- the proxy decides what to do **before and after** it calls the real method;
- **it checks:** “Is this method marked with `@Transactional`?” → If yes, it starts the transaction.