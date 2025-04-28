# `@Component`
In Spring Framework, `@Component` is a core annotation used to indicate that a class is a **Spring-managed component**. 

It tells Spring to **automatically detect** the class through **classpath scanning** and register it as a **Spring bean**.

**It basically marks a class as a [Spring bean](../../aop/spring-beans/description/description.md).**

```java
import org.springframework.stereotype.Component;

@Component
public class MyService {
    public void doSomething() {
        System.out.println("Doing something...");
    }
}
```