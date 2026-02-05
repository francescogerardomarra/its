# `CreditCardProcessor` Class

- `CreditCardProcessor` is one of the service provider implementations of [PaymentProcessor](../../service/paymentprocessor/paymentprocessor.md) service interface;
- the other service provider implementation is [PayPalProcessor](../paypalprocessor/paypalprocessor.md);
- it simply implements the `processPayment` method by printing a string;


- see the location of this interface within the [project structure](../../project-structure/project_structure.md).

**Code:**

```java
public class CreditCardProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
    }
}
```
