# Description

- the state pattern is a behavioral design pattern that allows an object to change its behavior when its internal state changes;
- it makes an object appear as if it changes its class at runtime;
- normally, you might use if or switch statements to handle different states:

    ```text
    if state == "Playing":
    # do something
    elif state == "Paused":
    # do something else
    ```


- but as the number of states grows, this becomes messy and hard to maintain;
- the state pattern solves this by encapsulating state-specific behavior inside separate classes.

**Key idea**

- you have a context (the main object);
- the context has a reference to a state object;
- each state class defines behavior for that specific state;


- when the state changes, the context swaps its state object.

**Structure:**

* `Context` (class):

    * holds a reference to a `State` object;
    * delegates behavior to the current `State`;
    * may allow the `State` object to change the current `State`.

* `State` (interface):

    * defines the common interface for all concrete states;
    * declares the methods that each state must implement.

* `ConcreteStateA`, `ConcreteStateB`, ... (classes):

    * implement the behavior associated with a particular state;
    * may trigger state transitions inside the `Context`.

* `Client` (class):

    * interacts with the `Context`;
    * the `Context` handles the internal state transitions automatically.
