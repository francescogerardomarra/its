# Real Life Analogy

Imagine you’re using a food delivery app, like Uber Eats or DoorDash.
You pick your favorite meal, go to checkout, and it’s time to pay.

At first, the app only supports credit card payments.
So the payment process is simple:

1. the app asks for your card details;
2. it validates the information;
3. it charges your card.

Everything works fine, until the company decides to add PayPal as a new payment option.
Suddenly, the developer has to open up the existing payment code and wrap everything in if-else or switch statements:

```text
if (paymentType == CREDIT_CARD) {
// credit card logic
} else if (paymentType == PAYPAL) {
// PayPal logic
}
```


It works, but it’s messy and hard to maintain. Every time a new payment method (like Apple Pay or Google Pay) is added, the developer has to modify the same code again, breaking the open-closed principle.

---

**So instead, the developer applies the strategy design pattern:**

Each payment method, Credit Card, PayPal, Apple Pay, becomes its own class that follows a common interface like `PaymentStrategy`.
Now, the app doesn’t care how the payment is done; it just calls:

```text
paymentService.processOrder(strategy);
```

and the right strategy handles the details.

This means the app can easily switch between strategies (payment methods) at runtime, or add new ones later, without changing the rest of the code.
