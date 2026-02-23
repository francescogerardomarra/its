# Description

**The analogy: the "universal remote"**

Imagine you have a complex home theater system. To watch a movie, you have to do the following:

1. turn on the TV;
2. set the TV input to HDMI 1;
3. turn on the sound system;
4. set the sound system volume to 20;
5. turn on the streaming stick;
6. dim the living room lights.

If you have to do this every single time, it is tedious and prone to error.

Now, imagine you buy a **universal remote** with a single button labeled **"watch movie."** When you press that one button, the remote sends signals to all those different devices to do the work for you.

* **you** are the client;
* the **universal remote** is the **facade**.
* the **TV, sound system, and lights** are the **complex subsystem**.

---

**What is the facade pattern?**

The Facade Pattern is a **structural design pattern** that provides a simplified interface to a library, a framework, or any other complex set of classes.

It doesn't hide the complex system completely (you can still access the low-level classes if you really need to), but it provides a "friendly face" (a facade) for the most common tasks.

---

**Why use it? (benefits vs. drawbacks):**

**`Benefits`:**

* **simplicity:** it makes a software library easier to use, understand, and test;
* **decoupling:** 
  * it decouples your code from the intricate details of a subsystem;
  * if the subsystem changes (e.g., you swap the `Projector` class for a `TV` class), you only have to change the facade, not every place in your app where the device was used.
* **layering:** it helps define entry points to each level of your software (e.g., a service layer acting as a facade for the data access layer).

**`Drawbacks`:**

* **God object:** if you aren't careful, the facade can become a "God object" coupled to *too many* classes, becoming a bloat point in your application.

**Structure:**

* `Subsystems` (classes):
  * these are the complex, existing classes (often part of a third-party library or legacy code);
  * they are almost always concrete classes because they hold the actual logic you are trying to hide.


* `Facade` (class):
  * is a standard `public class` that holds references to the subsystem classes;
  * in modern enterprise Java (like Spring), the facade is often defined as an **interface** with a separate implementation class (`FacadeImpl`) to make unit testing easier.

* `Client` (class):
  * this is your `main` class or a service class that calls the facade.
