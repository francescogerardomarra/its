# Generic
### Scenario
You're building an order system.  
You want to **log events** like "Order placed" or "Stock updated" to a database for auditing.  
But if something goes wrong with the order, you **still want the logs to be saved**, even
if the main transaction rolls back.

That’s where `REQUIRES_NEW` comes in handy.

```java
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private AuditLogService auditLogService;

    @Transactional
    public void placeOrder(Order order) {
        orderRepo.save(order);

        // Log event in a separate transaction
        auditLogService.logEvent("Order placed: " + order.getId());

        // Simulate failure
        if (order.getAmount() > 1000) {
            throw new RuntimeException("High-value orders need manual approval.");
        }
    }
}
```
```java
@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository logRepo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logEvent(String message) {
        AuditLog log = new AuditLog(message, LocalDateTime.now());
        logRepo.save(log);
    }
}
```
### What happens here:

- `placeOrder()` starts a transaction;
- Inside it, `logEvent()` is called, but with `REQUIRES_NEW`, so it starts **its own transaction**;
- If `placeOrder()` later throws an error, the **order will roll back**, but the **log will still be saved**.

This is super useful for things like logging, auditing, or notifications—where you don’t 
want those actions to disappear just because the main transaction failed.
