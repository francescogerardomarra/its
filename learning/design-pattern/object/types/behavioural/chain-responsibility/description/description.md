# Description

The **chain of responsibility** design pattern is a **behavioral design pattern** used to pass a request along a chain of handlers. Each handler in the chain either **processes the request** or **forwards it to the next handler** in the chain.

* you have multiple objects that can handle a request;
* instead of hard-coding which one will handle it, you link them in a **chain**;
* the request travels along the chain until one of the handlers **takes responsibility** for it.

Imagine a **customer support system**:

* level 1 support tries to handle the issue;
* if it can’t, it forwards the request to level 2;
* if level 2 can’t, it forwards to level 3, and so on.

**Structure:**

- `Handler` (abstract class):

    * declares a method to handle requests;
    * may store a reference to the next handler.
- `ConcreteHandlers` (class):

    * implement request handling;
    * optionally forward requests to the next handler.
- `Client` (class):

    * sends the request to the first handler in the chain.
