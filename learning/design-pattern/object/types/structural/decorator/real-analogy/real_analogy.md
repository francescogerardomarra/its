# Real Life Analogy

**Scenario:**

Imagine you are building a **food delivery application**. You have a core `Notifier` service responsible for alerting customers about important events, such as when their order is received or when the driver has departed.

---

**The base component (the "naked" object):**

Initially, your service is simple. It has a single `send` method that notifies the customer via **Email**. This is your base component—the foundational object that does the core job.

---

**The problem: the explosion of subclasses:**

As the app grows, customers demand more. Some want alerts on **WhatsApp**. Others want **Facebook** messages. Some want **SMS**.

If you rely on standard inheritance, you run into a trap.

* if a user wants email + WhatsApp, you create a `EmailAndWhatsAppNotifier` class;
* if a user wants Facebook + SMS, you create a `FacebookAndSMSNotifier` class;
* if a user wants all three, you create yet another class.

This approach causes a "class explosion," where you have to create a new subclass for every possible combination of notification channels. It becomes impossible to maintain.

---

**The solution: the decorator pattern (wrapping):**

Instead of creating rigid subclasses for every combination, you use **composition**. You treat the additional notification channels (WhatsApp, Facebook, SMS) as **Decorators** (wrappers).

1. **the interface:** you create a common interface (`INotifier`) that both the core Email notifier and the new decorators implement;
2. **the wrapping:** you "wrap" the objects inside one another like layers of an onion:
   * you start with the basic `EmailNotifier`;
   * if the user wants WhatsApp, you wrap the `EmailNotifier` object inside a `WhatsAppDecorator`.
   * if they also want Facebook, you wrap that whole bundle inside a `FacebookDecorator`.

---

**How it works in practice:**

When the client calls `send()` on the outermost wrapper (Facebook), the request travels down the chain:

1. the `FacebookDecorator` executes its behavior (sends a generic FB alert) and calls the next layer;
2. the `WhatsAppDecorator` executes its behavior and calls the next layer;
3. the `EmailNotifier` finally sends the email.

---

**Why this fits the analogy:**

Just like putting on clothes, you don't sew a new "shirt-jacket-scarf" garment every time it gets cold. You simply put on a shirt, then *decorate* yourself with a jacket, and then *decorate* that with a scarf. You can add or remove these layers at runtime depending on the weather (or in this case, the user's notification preferences).
