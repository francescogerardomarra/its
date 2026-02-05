# Description

The strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable.

This lets the algorithm vary independently from the clients that use it.

**In simple terms:**

It allows you to choose which behavior (strategy) to use at runtime, instead of hardcoding it.

**Imagine you’re using a navigation app:**

You can choose a route strategy: “fastest route”, “shortest route”, or “avoid tolls”.

The app’s core logic (displaying maps, starting navigation) doesn’t change.

Only the strategy for calculating the route changes.

So, the app = context.

The different route calculation methods = strategies.

**Structure:**

* `Strategy` (interface):

    * declares a method that all concrete strategies must implement;
    * example: `calculateRoute(start, end)`.

* `ConcreteStrategies` (class):

    * implement the algorithm in different ways;
    * example:

      * `FastestRouteStrategy`;
      * `ShortestRouteStrategy`.

* `Context` (class):

    * has a reference to a `Strategy` object;
    * delegates the algorithm execution to the current strategy;
    * example:

        * `NavigationApp` holds a `RouteStrategy` and calls `strategy.calculateRoute()`.
