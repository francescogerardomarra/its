# Description

- the template method design pattern is a behavioral design pattern that defines the skeleton of an algorithm in a method, deferring some steps to subclasses;
- it allows subclasses to redefine certain steps of the algorithm without changing its overall structure;
- you have an algorithm that has a fixed structure, but some parts can vary;


- you define the invariant steps in a base class;
- you let subclasses override specific steps without changing the algorithm’s order or structure.

**Structure:**

* `AbstractClass` (abstract class):

    * defines the **template method** that outlines the algorithm’s structure;
    * calls a series of steps, some implemented here, others deferred to subclasses;
    * contains:

        * `.templateMethod()`: defines the sequence of operations (usually `final` or non-overridable);
        * `.primitiveOperation1()`: abstract method (to be implemented by subclasses);
        * `.primitiveOperation2()`: abstract method (to be implemented by subclasses);
        * `.hookOperation()`: optional method with default behavior (can be overridden).

* `ConcreteClassA` (class):

    * inherits from `AbstractClass`;
    * implements:

        * `.primitiveOperation1()`;
        * `.primitiveOperation2()`;
    * may override `.hookOperation()` if needed.

* `ConcreteClassB` (class):

    * inherits from `AbstractClass`.
    * implements:

        * `.primitiveOperation1()`;
        * `.primitiveOperation2()`;
    * may override `.hookOperation()` differently.
