# Real Life Analogy

Imagine you are building a **food delivery platform like Zomato**.

**The situation:**

* you collect **menus and restaurant data** from many different restaurants;
* all restaurants send their data in **XML format**;
* your app already works perfectly with this XML data.

Later, your app becomes successful and you decide to **upgrade the user interface**.

**The problem:**

You find a **third-party UI system** that offers:

* beautiful menu layouts;
* smart recommendations;
* advanced customizations.

However, this UI system:

* **only accepts menu data in JSON format**;
* is **third-party software**, so:

    * you cannot modify it;
    * changing it might break existing users.

Now you have a mismatch:

* your system: **XML**;
* UI system: **JSON**.

Both represent the *same information*, but in **incompatible formats**.

---

**The adapter (real-world role):**

Instead of rewriting your entire system or modifying the UI library, you introduce a **menu translator**:

* this translator:

    * takes **XML menus** from your platform;
    * converts them into **JSON**;
    * passes the converted data to the UI system.
* the UI system believes it is working with native JSON;
* your app continues working with XML as before.

Neither system knows the other is incompatible.
