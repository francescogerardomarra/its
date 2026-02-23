# Real Life Analogy

We use the example of building a **cryptocurrency investing app** to illustrate the facade pattern.

In this scenario, you want to offer a simple feature to your users: **buying crypto**. However, the actual process involved in making that happen is messy and complicated.

---

**The complex subsystem (the "nasty stuff"):**

Behind the scenes, you are relying on a complex third-party library and various internal services to get the job done. If a user wants to buy bitcoin, several disconnected things must happen in a specific order:

* **database access:** you must fetch the logged-in user's details;
* **balance check:** you need to verify they have enough local currency to cover the transaction;
* **3rd party library:** you have to interact with a complex external library, fetch specific parameters, and call the correct `buyCurrency` method;
* **notification service:** you need to trigger a `mailService` to send a confirmation email.

Without a facade, every part of your app that needs to buy crypto would have to interact with all these complex moving parts directly.

---

**The facade (the `BuyCryptoFacade`):**

To solve this, you create a "middleman" or a centralized entry point called `BuyCryptoFacade`.

This facade provides a **simplified interface** to the client. Instead of worrying about the database, the balance check, or the email service, the client simply tells the facade: *"I want to buy 50 units of bitcoin."*

The Facade handles all the heavy lifting internally:

1. it talks to the **database**;
2. it runs the **balance check**;
3. it executes the transaction via the **3rd party library**;
4. it sends the receipt via the **email service**.
