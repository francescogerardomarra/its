# Description

- the mediator design pattern is a behavioral design pattern that helps reduce coupling between objects by centralizing their communication through a mediator object instead of letting them refer to each other directly;
- imagine a system with several components (objects) that need to interact, for example, a UI dialog with buttons, text boxes, and lists; if every component directly communicates with others, the code becomes tangled and hard to maintain;
- the mediator pattern introduces a mediator object that:
  - knows all the components;
  - controls how they interact;
  - simplifies communication by acting as a hub.

So instead of objects calling each other, they notify the mediator, and the mediator coordinates the response.

**Structure:**

* `Mediator` (interface):

    * defines the interface for communication between components (colleagues);
    * usually declares methods like `notify(sender, event)` or `send(message, colleague)`.

* `ConcreteMediator` (class):

    * implements the communication and coordination logic between components;
    * knows and maintains references to all colleague objects;
    * decides how to respond to events or messages from colleagues.

* `Colleague` (interface):

    * defines the interface for components that interact with the mediator;
    * holds a reference to the mediator.

* `ConcreteColleague` (class):

    * implements the actual component behavior;
    * communicates **only through the mediator**, not directly with other colleagues;
    * calls `mediator.notify(self, event)` (or similar) to send messages.

* `Client` (class):

    * creates the concrete mediator and components;
    * configures relationships by passing the mediator reference to each component;
    * triggers the interactions.
