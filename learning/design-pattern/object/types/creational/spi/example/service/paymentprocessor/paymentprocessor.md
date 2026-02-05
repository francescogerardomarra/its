# `PaymentProcessor` Interface

- `PaymentProcessor` is the service interface;
- [CreditCardProcessor](../../provider/creditcardprocessor/creditcardprocessor.md) and [PayPalProcessor](../../provider/paypalprocessor/paypalprocessor.md) are its service providers implementations;
- see the location of this interface within the [project structure](../../project-structure/project_structure.md).

**Code:**

```java
public interface PaymentProcessor {
    void processPayment(double amount);
}
```
