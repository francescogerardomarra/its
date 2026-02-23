# Description

- the adapter design pattern is a structural pattern that lets incompatible interfaces work together;
- think of it as a translator between two objects.

**The problem it solves:**

- sometimes you have:
  - existing code that works well (often a library or legacy class);
  - a new system that expects a different interface;
- you cannot (or should not) change the existing code, but you still want to use it;
- the adapter solves this by wrapping the existing class and converting its interface into one the client expects.

**Structure:**

- `Client` (class):
  - uses objects through a `Target` interface;
- `Target` (interface):
  - defines the interface expected by the `Client`;
- `Adaptee` (class):
  - existing class with an incompatible interface
- `Adapter` (class):
  - converts the `Adaptee`’s interface into the `Target` interface;
  - delegates calls from `Target` methods to `Adaptee` methods.
