# `@Autowired`
It is a Spring annotation used for **automatic dependency injection**.

It tells Spring to **automatically resolve and inject** the required bean by **type**.

```java
@Component
public class Car {
    private Engine engine;

    @Autowired
    public Car(Engine engine) {
        this.engine = engine;
    }

    public void drive() {
        engine.start();
    }
}
```
Spring will inject an `Engine` bean into the `Car` bean automatically.