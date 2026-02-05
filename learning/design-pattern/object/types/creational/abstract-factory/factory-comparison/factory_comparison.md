# Factory Method Comparison

[Factory method](../../factory-method/index.md) vs [Abstract Factory](../index.md).

| Feature                        | Factory Method                        | Abstract Factory                                          |
|--------------------------------|---------------------------------------|-----------------------------------------------------------|
| **Level of abstraction**       | creates **one** product.              | creates **families** of products.                         |
| **Purpose**                    | let subclasses decide what to create. | provide an interface for creating related objects.        |
| **Inheritance vs Composition** | relies on **inheritance**.            | relies on **composition**.                                |
| **Number of products created** | usually one per factory.              | multiple related products.                                |
| **Example analogy**            | a factory that makes one type of car. | a factory that makes a whole set: car, engine, and tires. |
