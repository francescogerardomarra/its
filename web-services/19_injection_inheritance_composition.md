# Dependency Injection, Inheritance, and Composition in Java

## Introduction
In software design, understanding the differences and relationships between Dependency Injection, Inheritance, and Composition is critical. These concepts help developers write modular, reusable, and maintainable code. In this article, we will explore each concept and demonstrate their use in Java with practical examples.

---

## Dependency Injection (DI)

### What is Dependency Injection?
Dependency Injection is a design pattern in which an object receives its dependencies from an external source rather than creating them internally. It promotes loose coupling between components, making the code easier to maintain and test.

### Example of Dependency Injection
In the following example, a `Car` class depends on an `Engine` class. The dependency is injected via the constructor:

```java
// Dependency Interface
interface Engine {
    void start();
}

// Implementation of the Dependency
class PetrolEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Petrol Engine starting...");
    }
}

class DieselEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Diesel Engine starting...");
    }
}

// Class with Dependency Injection
class Car {
    private final Engine engine;

    // Constructor Injection
    public Car(Engine engine) {
        this.engine = engine;
    }

    public void startCar() {
        engine.start();
        System.out.println("Car started.");
    }
}

// Main class to test Dependency Injection
public class Main {
    public static void main(String[] args) {
        Engine petrolEngine = new PetrolEngine();
        Car car = new Car(petrolEngine);
        car.startCar();

        Engine dieselEngine = new DieselEngine();
        Car anotherCar = new Car(dieselEngine);
        anotherCar.startCar();
    }
}
```

### Key Advantages
1. Loose coupling.
2. Better testability with mock dependencies.
3. Enhanced flexibility for swapping implementations.

---

## Inheritance

### What is Inheritance?
Inheritance is an object-oriented programming (OOP) principle where a class (subclass) derives properties and behavior from another class (superclass). It is a form of "is-a" relationship.

### Example of Inheritance

```java
// Superclass
class Animal {
    public void eat() {
        System.out.println("This animal eats food.");
    }
}

// Subclass
class Dog extends Animal {
    @Override
    public void eat() {
        System.out.println("The dog eats bones.");
    }

    public void bark() {
        System.out.println("The dog barks.");
    }
}

// Main class to test Inheritance
public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.eat(); // Overridden method
        dog.bark();
    }
}
```

### Key Advantages
1. Code reuse through shared behavior in the superclass.
2. Hierarchical relationships between classes.

### Drawbacks
1. Can lead to tightly coupled code.
2. Inflexibility in modifying the superclass without affecting all subclasses.

---

## Composition

### What is Composition?
Composition is a design principle where an object contains other objects to achieve its functionality. It is a form of "has-a" relationship and is preferred over inheritance in many scenarios due to better flexibility.

### Example of Composition

```java
// Component Class
class Engine {
    public void start() {
        System.out.println("Engine starting...");
    }
}

// Composite Class
class Vehicle {
    private final Engine engine;

    public Vehicle(Engine engine) {
        this.engine = engine;
    }

    public void startVehicle() {
        engine.start();
        System.out.println("Vehicle is ready to go.");
    }
}

// Main class to test Composition
public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();
        Vehicle vehicle = new Vehicle(engine);
        vehicle.startVehicle();
    }
}
```

### Key Advantages
1. Promotes loose coupling.
2. Greater flexibility in composing behavior.
3. Avoids the pitfalls of deep inheritance hierarchies.

---

## Comparing Inheritance and Composition

### Key Differences

| Aspect       | Inheritance                      | Composition                    |
|--------------|----------------------------------|--------------------------------|
| Relationship | "Is-a"                           | "Has-a"                        |
| Coupling     | Tightly coupled                  | Loosely coupled                |
| Reusability  | Limited to superclass behavior   | High; components can be reused |
| Flexibility  | Less flexible                    | More flexible                  |
| Use Case     | Suitable for hierarchical models | Suitable for dynamic behavior  |

---

## Dependency Injection vs Composition

Dependency Injection is often implemented using composition. By injecting dependencies, you essentially compose objects at runtime, making DI an application of composition principles.

### Key Similarities
- Both promote loose coupling.
- Both enhance testability and flexibility.

### Key Differences
- Dependency Injection specifically refers to the process of providing dependencies from external sources.
- Composition refers to the structural relationship between objects.

---

## Conclusion
Understanding Dependency Injection, Inheritance, and Composition equips developers with tools to build robust and maintainable applications. While inheritance provides code reuse in a hierarchical structure, composition offers greater flexibility by encapsulating behavior within components. Dependency Injection leverages composition to enhance modularity and testability.

Choose the right approach based on the problem requirements and strive for clean, maintainable designs.

