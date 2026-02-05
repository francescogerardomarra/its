# Description

- the command design pattern is a behavioral design pattern that turns a request or operation into a standalone object containing all the information about the request, such as what method to call, who should perform it, and when it should be executed;
- this allows you to parameterize objects with operations, queue or log requests, and support undo/redo operations.

**Structure:**

- `Command` (interface): declares an `execute()` method;
- `ConcreteCommand` (class): implements the `Command` interface and defines a link between the `Receiver` and the action;
- `Receiver` (class): the actual object that performs the action;
- `Invoker` (class): asks the `Command` to carry out a request (e.g., a button click);
- `Client` (class): creates and configures `Command` and `Receiver`.
