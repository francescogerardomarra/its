# Example Definition

- in this example, we create an implementation of [SPI](../../definition/definition.md);
- the interface [PaymentProcessor](../service/paymentprocessor/paymentprocessor.md) is the service;
- the concrete classes [CreditCardProcessor](../provider/creditcardprocessor/creditcardprocessor.md) and [PayPalProcessor](../provider/paypalprocessor/paypalprocessor.md) are the service implementations;


- we register the implementations and load them using the `ServiceLoader` java library class.
