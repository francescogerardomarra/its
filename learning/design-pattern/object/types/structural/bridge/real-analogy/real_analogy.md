# Real Life Analogy

Imagine you run a **pizza delivery business**.

At first, your menu is simple. You offer different **types of pizzas**, such as:

* pepperoni pizza;
* veggie pizza.

Naturally, you model this with a base `Pizza` concept and create subclasses for each pizza type.

**The problem appears:**

As your business grows, you expand internationally and start offering **different styles of preparation**, such as:

* american-style pizza;
* italian-style pizza.

If you continue using inheritance alone, you now face a problem:

* `AmericanPepperoniPizza`;
* `ItalianPepperoniPizza`;
* `AmericanVeggiePizza`;
* `ItalianVeggiePizza`.

Every **new pizza type** must be combined with every **new preparation style**.
If you later add Chicken Pizza, you must create even more subclasses.

The number of classes grows **exponentially**, making the system hard to maintain and extend.

This happens because you are trying to vary the system in **two independent dimensions**:

1. **pizza type**;
2. **preparation style (restaurant type)**.

**The bridge solution:**

Instead of mixing both dimensions into one inheritance hierarchy, you **separate them**.

* one hierarchy represents **pizza types** (pepperoni, veggie, chicken, etc.);
* another hierarchy represents **restaurants or preparation styles** (american, italian, etc.).

Now:

* a **restaurant** does not *inherit* a specific pizza;
* a **restaurant holds a reference to a pizza** and uses it to prepare and deliver the order.

This reference acts as a **bridge** between the two hierarchies.

**How this works in the real world:**

When a customer places an order, they simply say:

* *“I want a Pepperoni Pizza from an Italian restaurant.”*

Behind the scenes:

* the **restaurant** controls the overall process (ordering, delivering);
* the **pizza** defines what is being prepared;
* either can change **independently** without affecting the other.

**Why this matches the bridge pattern:**

* **pizza types** and **restaurant styles** evolve independently;
* you can add new pizzas without touching restaurant code;
* you can add new restaurant styles without touching pizza code;
* you can even switch pizza types or restaurant styles **at runtime**.
