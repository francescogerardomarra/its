# Real Life Analogy

Let’s imagine you own a **computer hardware company** that manufactures components for **two major brands**:
- **MSI**;
- **ASUS**.

---

**Step 1 - factory method (before)**:

initially, your company only made **GPUs**, so, you had:

- an `MsiManufacturer` that produced **MSI GPUs**;
- an `AsusManufacturer` that produced **ASUS GPUs**.

Each of these factories knew **how to make their own brand’s GPU**, this was the [factory method](../../factory-method/index.md) pattern.

> Each manufacturer (factory) = a class with a `createGpu()` method that makes a brand-specific GPU.

---

**Step 2 – new requirement**:

Business is booming, now your company also starts making **monitors**.

You now produce:

* **MSI GPU** and **MSI Monitor**;
* **ASUS GPU** and **ASUS Monitor**.

So, there are **two families of products**:

1. **MSI family**: GPU + Monitor;
2. **ASUS family**: GPU + Monitor.

---

**The problem:**

If you try to modify your existing factory classes (that used to only make GPUs) to also make monitors, you’ll have to:

* add new logic for new product types inside the same class;
* update that logic each time you add another product type (like a keyboard, motherboard, etc.).

That means your code keeps changing, violating the **Open-Closed Principle**.

**More details [here](../wrong-way/wrong_way.md)**.

---

**Step 3 – abstract factory comes in:**

Instead of one big messy factory, you now define a **family of related factories**.

You start by defining:

* a **`Gpu` interface** (implemented by `MsiGpu` and `AsusGpu`);
* a **`Monitor` interface** (implemented by `MsiMonitor` and `AsusMonitor`).

Then, you create an **abstract factory**, think of it as a **"blueprint"** that lists what any manufacturer must be able to produce:

```text
ComputerPartsFactory
 ├── createGpu()
 └── createMonitor()
```

Each brand now has its **own concrete factory** that follows this blueprint:

* `MsiFactory`: creates MSI GPU + MSI Monitor;
* `AsusFactory`: creates ASUS GPU + ASUS Monitor.

---

**The key idea**:

Each factory produces a **consistent family of products** that work well together.

So if you’re assembling a computer:

* you choose one factory (say `MsiFactory`);
* you get an **MSI GPU** and an **MSI Monitor** that belong to the same family.

Your client code never needs to know which brand it’s using, it just calls:

```python
factory.createGpu()
factory.createMonitor()
```

and gets the right brand automatically.

---

**In real-world terms:**

Think of your **abstract factory** as a **standardized contract** that every brand’s production line follows.

* MSI’s factory uses **MSI components and design** to produce its family of parts;
* ASUS’s factory uses **ASUS designs and materials** to make its family;
* but both factories expose the **same methods** (`createGpu`, `createMonitor`).

That way, if tomorrow you add another brand, say **Gigabyte**, you just create a new `GigabyteFactory`, no need to modify any existing code.
