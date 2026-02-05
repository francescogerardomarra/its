# Description

The abstract factory design pattern is a creational design pattern that provides an interface for creating families of related or dependent objects without specifying their concrete classes.

**Structure:**

* `AbstractFactory` (interface):

    * declares methods for creating abstract products;
    * example:

        * `createProductA()`
        * `createProductB()`

* `ConcreteFactory` (class):

    * implements the creation methods defined in `AbstractFactory`;
    * creates specific concrete products;
    * example:

        * `ConcreteFactory1`: creates `AbstractProductA1`, `AbstractProductB1`;
        * `ConcreteFactory2`: creates `AbstractProductA2`, `AbstractProductB2`.

* `AbstractProductA` (interface):

    * declares the interface for a family of products of type A.

* `AbstractProductB` (interface):

    * declares the interface for a family of products of type B.

* `ConcreteProductA1` (class):

    * implements `AbstractProductA`;
    * created by `ConcreteFactory1`.

* `ConcreteProductA2` (class):

    * implements `AbstractProductA`;
    * created by `ConcreteFactory2`.

* `ConcreteProductB1` (class):

    * implements `AbstractProductB`;
    * created by `ConcreteFactory1`.

* `ConcreteProductB2` (class):

    * implements `AbstractProductB`;
    * created by `ConcreteFactory2`.

* `Client` *(class)*:

    * uses only the interfaces defined by `AbstractFactory`, `AbstractProductA`, and `AbstractProductB`;
    * is **decoupled** from concrete classes, can switch product families by changing the factory instance.
