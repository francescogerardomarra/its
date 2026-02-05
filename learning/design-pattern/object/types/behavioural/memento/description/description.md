# Description

- the memento design pattern is a behavioral design pattern used to capture and restore an object’s internal state without violating its encapsulation;
- in simpler terms, it allows you to save and restore the state of an object (like “undo” or “rollback” in software), without exposing its internal details;
- capture the internal state of an object so that it can be restored later, without exposing the object’s implementation details.

**Structure:**

* `Originator` (class):

    * creates a `Memento` containing a snapshot of its current internal state;
    * uses the `Memento` to restore its state later;
    * methods:

        * `createMemento()`;
        * `restoreMemento(Memento m)`.

* `Memento` (inner class of `Originator`):

    * stores the internal state of the `Originator`;
    * protects against access by any object other than the `Originator`;
    * methods:

        * `getState()` (accessible only to `Originator`);
        * may be immutable.

* `Caretaker` (class):

    * responsible for keeping the `Memento` safe;
    * never modifies or inspects the contents of a `Memento`;
    * methods:

        * `addMemento(Memento m)`;
        * `getMemento()`.
