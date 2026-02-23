# Description

- the **composite design pattern** is a **structural design pattern** that lets you **treat individual objects and groups of objects uniformly**;
- it’s especially useful when you’re working with **tree-like structures** (hierarchies).

**Core idea:**

> **Compose objects into tree structures and treat both single objects and composites the same way.**

**Problem it solves:**

Imagine you have:

* **single objects** (e.g., a file);
* **groups of objects** (e.g., a folder containing files and other folders).

Without Composite:

* you’d need different logic for files vs folders;
* code becomes full of `if`/`instanceof` checks.

With Composite:

* files and folders implement the **same interface**;
* you can call the same methods on both.

**Structure:**

* `Component` (interface):
  * defines the common operations for both leaf and composite.

* `Leaf` (class):
  * represents an individual object (no children);
  * implements the `Component`.

* `Composite` (class):
  * represents a group of `Component` (can have children);
  * implements the `Component`;
  * contains a collection of `Component`.

* `Client` (class):
  * uses `Component` objects without knowing whether they are leaf or composite;
  * works only with the `Component` type.
