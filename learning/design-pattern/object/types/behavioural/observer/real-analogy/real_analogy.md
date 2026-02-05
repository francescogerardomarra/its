# Real Life Analogy

Imagine you own a store that sells all sorts of products. One day, a customer asks if you have a particular item, but unfortunately, it’s out of stock. You reassure them that more stock will arrive soon.

Now both you and the customer face a problem:

* **option 1:** the customer can keep visiting the store every day to check if the item is available; but most of those trips will be a waste of time if the product hasn’t arrived yet;
* **option 2:** you could send an email to *every* customer every time new stock arrives; but that would annoy customers who aren’t interested in that item, and they might consider it spam.

**So, what’s the better approach?**

You create a **subscription system** where customers can **choose** to be notified only about the products or events they care about, for example, when a new phone arrives or when there’s a sale on gaming laptops.

In this setup:

* your **store** acts as the **publisher (subject)**: it manages products and sends notifications when relevant events occur;
* each **customer** acts as an **observer (subscriber)**: they can choose which notifications they want to receive (e.g., “notify me when new phones arrive”);
* a **notification service** inside your store keeps track of all these subscriptions: when a new product arrives or a sale starts, the store notifies only the customers who subscribed to those specific events.

If later you add more notification channels, say, push notifications through a mobile app, you don’t need to change the store’s core logic. You just add a new type of observer (like a **MobileAppListener**) that knows how to handle updates differently.
