# Description

The **flyweight pattern** is a structural design pattern focused on **optimization**. It allows programs to support huge numbers of objects by keeping their memory footprint low.

It achieves this by **sharing** parts of the object state between multiple objects, rather than keeping all of the data in each object.

> **Think of it as:** A library. Instead of every student buying their own copy of "The Great Gatsby" (creating thousands of copies), the library buys a few copies that are shared among students.

---

**The core concept: intrinsic vs. extrinsic:**

To understand Flyweight, you must distinguish between the two types of data an object holds:

1. **intrinsic state (shared):**
   * information that **does not change** regardless of where the object is used;
   * this data is stored in the flyweight object and shared;
   * *example:* the texture of a tree, the shape of a bullet, the font of a letter.

2. **extrinsic state (unique):**
   * information that **is unique** to a specific instance of that object;
   * this data is stored by the client (outside the flyweight) and passed to the flyweight when needed;
   * *example:* the `(x, y)` coordinate of a tree, the velocity of a bullet, the position of a letter on a page.

**Structure:**

Here is the structure of the Flyweight design pattern broken down into a dotted list, along with the specific Java types usually used for each component.

### The Structure (Java Perspective)

* `Flyweight` (interface):
  * this is the common contract for all flyweight objects;
  * it declares the method(s) through which flyweights can receive extrinsic data and act on it.

* `ConcreteFlyweight` (class):
  * this implements the flyweight interface and stores the **intrinsic state** (the shared data);
  * it must be immutable (its internal variables should not change after creation).

* `FlyweightFactory` (class):
  * this manages a pool (often a `Map` or `HashMap`) of existing flyweights;
  * when a client requests a flyweight, the factory checks the pool:
    * if it exists return the existing instance;
    * if not create a new one, add it to the pool, and return it.

* `Client` (class):
  * the code that calls the factory;
  * the client is responsible for storing or calculating the **extrinsic state** (the unique data) and passing it into the `ConcreteFlyweight` methods.
