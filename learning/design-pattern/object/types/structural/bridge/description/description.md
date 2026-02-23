# Description

The bridge design pattern is a structural pattern whose goal is to:

- decouple an abstraction from its implementation so that the two can vary independently.

**In simpler terms:**

It lets you separate what an object does from how it does it.

**The problem it solves:**

- imagine you have two dimensions that can change independently;
- example:
  - shapes: circle, square;
  - colors: red, blue.
- without bridge, you might create classes like:
  - `RedCircle`;
  - `BlueCircle`;
  - `RedSquare`;
  - `BlueSquare`.


- this quickly leads to a class explosion.

**The core idea:**

Instead of combining everything in subclasses, the bridge pattern:

  - keeps abstraction and implementation in separate class hierarchies;
  - connects them using composition, not inheritance.

**Structure:**

- `Abstraction` (abstract class):
  - defines high-level behavior;
  - holds a reference to the `Implementor`;
  - example:

    ```java
    abstract class Shape {
        protected Color color; // bridge
    
        protected Shape(Color color) {
            this.color = color;
        }
    
        abstract void draw();
    }
    ```

- `RefinedAbstraction` (class):
  - extends or specializes the abstraction;
  - example:

    ```java
    class Circle extends Shape {
        public Circle(Color color) {
            super(color);
        }
    
        @Override
        void draw() {
            System.out.println("Drawing Circle");
            color.applyColor();
        }
    }
    ```

- `Implementor` (interface):
  - defines operations used by the abstraction;
  - does NOT implement high-level logic;
  - example:

    ```java
    interface Color {
        void applyColor();
    }
    ```

- `ConcreteImplementor` (class):
  - provides platform- or detail-specific behavior;
  - example:

    ```java
    class Red implements Color {
        @Override
        public void applyColor() {
            System.out.println("Applying red color");
        }
    }
    ```
