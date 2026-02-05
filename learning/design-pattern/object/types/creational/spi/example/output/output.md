# Output

In this chapter, we show the [previous](../load-provider/load_provider.md) main output.

**Output overview:**

- as you can see from the output, all the registered service providers are correctly loaded by `ServiceLoader` java library class;
- [CreditCardProcessor](../provider/creditcardprocessor/creditcardprocessor.md) and [PayPalProcessor](../provider/paypalprocessor/paypalprocessor.md) classes print their respective strings from their implementation of the `processPayment` method of the service interface.

**Output:**

```java
Processing credit card payment of $100.0
Processing PayPal payment of $100.0
```
