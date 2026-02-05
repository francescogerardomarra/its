# `PayPalProcessor` Class

- `PayPalProcessor` is one of the service provider implementations of [PaymentProcessor](../../service/paymentprocessor/paymentprocessor.md) service interface;
- the other service provider implementation is [CreditCardProcessor](../creditcardprocessor/creditcardprocessor.md);
- it simply implements the `processPayment` method by printing a string;


- see the location of this interface within the [project structure](../../project-structure/project_structure.md).

**Code:**

```java
public class PayPalProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
    }
}
```
