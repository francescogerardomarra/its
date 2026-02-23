# Description

- the iterator design pattern is a behavioral pattern that provides a standard way to access elements of a collection sequentially without exposing the underlying representation (like arrays, linked lists, trees, etc.);

**What problem does the iterator pattern solve?**

Collections contain multiple elements, but different collections store data differently:
  - **arrays**: contiguous memory;
  - **linked** lists: nodes linked by pointers;
  - **trees**: hierarchical structure.

If client code needs to traverse each type, you'd end up writing traversal logic for each structure.
That leads to:

- duplicate code;
- tight coupling between collection and traversal logic;
- hard to change iteration behavior.

Iterator pattern solves this by providing a separate object: the iterator responsible for traversal.

**Structure:**

- `Iterator` (interface):
  * defines operations for iterating over a collection:
      * `boolean hasNext();`
      * `T next();`

- `Iterable` (interface):
  * defines a method for creating an iterator:
      * `Iterator<T> createIterator()`.

- `ConcreteIterator` (class):
  * implements the `Iterator` interface;
  * has internal state to track the current position in the collection;
  * knows *how to traverse* the specific collection structure.

- `ConcreteCollection` (class):
  * implements the `Iterable` (or custom aggregate) interface;
  * stores the actual elements;
  * creates and returns an instance of the `ConcreteIterator`.
