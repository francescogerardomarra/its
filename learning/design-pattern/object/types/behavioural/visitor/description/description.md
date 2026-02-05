# Description

The visitor design pattern is a behavioral design pattern that lets you add new operations to existing object structures without modifying their classes.

It’s especially useful when:

- you have a collection of objects of different types (a class hierarchy);
- you frequently need to perform new kinds of operations on these objects;
- you want to keep these operations separate from the object classes themselves.

**Core idea:**

Instead of putting all operations inside your classes, you:

1. create a visitor object that represents an operation;
2. each element accepts the visitor, which allows the visitor to “visit” it and run the correct method.

This is called **double dispatch**:

- first dispatch: `element.accept(visitor)`;
- second dispatch: `visitor.visitConcreteElement(this)`.

This ensures the right method is chosen based on:

the visitor type and the element type.

**Structure:**

- `Visitor` (interface):

  - declares one `visitSomething()` method for each concrete element type.

- `ConcreteVisitor` (class):

  - implements the `Visitor` interface;
  - contains actual behavior for each element type.

- `Element` (interface):

  - declares `accept(Visitor v)`;
  - all concrete elements implement this.

- `ConcreteElement` (class):

  - implements `Element`;
  - inside `accept`, calls the correct method on `Visitor`.

- `ObjectStructure` (class):

  - holds a collection of elements;
  - provides a way to iterate and apply visitors.
