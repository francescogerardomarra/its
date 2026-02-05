# Description

- the observer pattern defines a one-to-many dependency between objects so that when one object (the subject) changes its state, all its dependents (observers) are notified automatically;
- you can think of it as a subscription system, like how you subscribe to a YouTube channel or follow someone on social media:
  - the channel (subject) publishes updates;
  - the subscribers (observers) get notified when there’s new content.

**Structure:**

- `Subject` (interface):
  - keeps a list of observers;
  - provides methods to attach, detach, and notify observers.
- `Observer` (interface):
  - defines an interface with an `update()` method;
  - this method is called when the subject’s state changes.
- `ConcreteSubject` (class):
  - a specific implementation of the `Subject`;
  - holds the actual state and calls `notifyObservers()` when it changes.
- `ConcreteObserver` (class):
  - implements the `Observer` interface;
  - updates its state or behavior when notified.
