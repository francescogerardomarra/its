# Description

**The core concept:**

- the **decorator pattern** is a structural design pattern that allows you to attach new behaviors to objects by placing these objects inside special wrapper objects that contain the behaviors;
- in simpler terms: **it allows you to add functionality to an object dynamically without changing the original class or using inheritance.**

---

**The problem: "class explosion":**

_Why not just use inheritance (subclassing)?_

Imagine you have a class `TextNotifier`. You want to add email capabilities, so you create `EmailNotifier`. Then you want SMS, so you create `SMSNotifier`.

But what if a user wants **both**? You would have to create `EmailAndSMSNotifier`.
What if you add Slack? Now you need `SlackNotifier`, `SlackAndEmailNotifier`, `SlackAndSMSNotifier`, `SlackAndEmailAndSMSNotifier`.

This leads to an **explosion of subclasses**. It becomes impossible to maintain.

---

**The solution: "wrapping"**

Instead of inheriting, you use **composition**.

Think of a set of **matryoshka dolls (russian nesting dolls)**.

* the smallest doll is the **Component** (the original object);
* the larger dolls are **Decorators**;
* you can put the component inside a decorator, and then put that entire package inside *another* decorator.

Each "doll" (decorator) adds its own layer of behavior, but the whole package still looks and acts like a doll.

---

**Structure:**

* `Component` (interface):
  * defines the common method(s) for objects that can be decorated;
  * this ensures the base object and the decorators can be used interchangeably;
  * **why an interface:** because both the real object and the decorators must share the exact same type signature.


* `ConcreteComponent` (class):
  * the basic object that you intend to decorate;
  * it implements the *Component* interface;
  * **why a class:** this is the actual implementation of the core logic (e.g., the `EmailNotifier`).


* `BaseDecorator` (abstract class):
  * wraps the *Component*;
  * it implements the *Component* interface **and** contains a field to store a reference to a *Component* object;
  * **why an abstract class:** 
    * it usually implements the interface methods by simply delegating them to the wrapped object (passing the call through);
    * we make it abstract because we don't want to create an instance of a generic "wrapper" that does nothing;
    * we only want to instantiate specific decorators (like `SMSDecorator` or `SlackDecorator`).


* `ConcreteDecorator` (class):
  * extends the `BaseDecorator`;
  * it overrides the methods to add its own behavior before or after calling the parent method;
  * **why a class:** these are the actual features you are adding (e.g., `SMSDecorator`, `SlackDecorator`).
