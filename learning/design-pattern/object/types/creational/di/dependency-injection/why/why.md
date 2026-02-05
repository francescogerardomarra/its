# Why use DI?
It makes your code easier to test and modify!

Let's take our example of a `Car` and it's `Engine` dependency.

You can easily swap real objects with mock or fake ones in tests.

No need to change the actual `Car` class — just pass in a different version of `Engine`.

```java
// Real
Engine engine = new Engine();
Car car = new Car(engine);

// Test
Engine mockEngine = new MockEngine(); // your test double
Car testCar = new Car(mockEngine); // we simply need to swap in our new engine type to test out new code
```

In this way you only need to swap out the `Engine` dependency to test out a whole new `MockEngine` type, to test new behaviours it might have! 