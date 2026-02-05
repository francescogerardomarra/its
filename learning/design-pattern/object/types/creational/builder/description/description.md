# Description

- the builder design pattern is a creational design pattern that provides a flexible solution to constructing complex objects step-by-step;
- it’s especially useful when an object requires many configuration options or when you want to avoid having a constructor with a long list of parameters;
- the builder pattern separates the construction of a complex object from its representation, allowing the same construction process to create different representations.

**Structure:**

- `Builder` (interface): declares the steps required to build a product;
- `ConcreteBuilder` (class): implements the `Builder` interface and assembles parts of the final product;
- `Product` (class): the complex object being built;
- `Director` (class): controls the building process using the `Builder` interface;
- `Client` (class): creates the `Builder` and `Director`, and starts the construction.

Note:

- the `Director` is **optional**;
- you can omit it if:
  - your client code directly knows how to assemble the product (i.e., it can call the builder steps in the right order itself);
  - you only need to build a few custom variations, not standardized models.
