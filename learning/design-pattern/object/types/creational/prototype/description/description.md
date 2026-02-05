# Description

- the **prototype design pattern** is a creational design pattern that allows you to create new objects by **cloning existing ones rather than creating them from scratch**;
- it’s especially useful when object creation is **costly or complex**, and you want to avoid repeating that process;
- instead of instantiating a new object directly (using `new`), you:
  1. create a prototype: an existing object configured as needed;
  2. clone this prototype to produce new objects with the same state.


- each clone can then be customized independently.

**Structure:**

* `Prototype` (interface):

    * declares the `clone()` method;
    * defines the contract for cloning itself.

* `ConcretePrototype` (class):

    * implements the **`clone()`** method defined in the `Prototype`;
    * performs the actual copying of the object’s internal state;
    * each subclass defines how it duplicates its data (deep copy or shallow copy);
    * represents the **real, cloneable objects** used by the client.

* `Client` (class):

    * does **not instantiate objects directly** using constructors;
    * instead, it keeps references to `Prototype` objects and creates new ones by calling their `clone()` method;
    * may store a **registry of prototypes** for convenient reuse (e.g., a "prototype factory").

**Optional supporting structure:**

* `PrototypeRegistry` (class):

    * maintains a **collection (map)** of named prototypes;
    * provides methods to:

        * register a new prototype;
        * retrieve and clone prototypes by name.
    * often used to manage multiple prototype types dynamically.
