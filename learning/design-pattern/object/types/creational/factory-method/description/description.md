# Description

- it provides a way to delegate the instantiation of objects to subclasses, rather than creating them directly in the client code;
- the factory method pattern defines an interface for creating an object, but allows subclasses to decide which class to instantiate;
- in short, it lets a class delegate the responsibility of object creation to its subclasses;


- instead of using `new` directly in your code, you call a factory method that returns an instance of a product interface or superclass;
- this allows your code to work with abstract types, making it more flexible and extensible.

**Structure:**

- `Product` (interface):
    - defines the **interface** for objects the factory method creates.
- `ConcreteProduct` (class)
    - implements the `Product` interface;
    - represents the **actual object** created by the factory.
- `Creator` (abstract class):
    - declares the **factory method** (`createProduct()`);
    - may provide a **default implementation** that calls the factory method;
    - defines **business logic** that uses `Product` objects, but **relies on subclasses** to provide the actual product.
- `ConcreteCreator` (class):
    - implements the `factoryMethod()` to create a **specific product**;
    - decides which `ConcreteProduct` to instantiate.
- `Client`:
    * works with `Creator` and `Product` interfaces;
    * doesn’t know the concrete classes used, relies on the factory method to get objects.
