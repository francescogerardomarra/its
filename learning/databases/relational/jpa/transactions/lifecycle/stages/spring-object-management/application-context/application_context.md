# `ApplicationContext`
`ApplicationContext` is a central interface in Spring that
represents the Spring IoC (Inversion of Control) container.

**It’s responsible for:**
- instantiating beans
- configuring them
- managing their lifecycle
- injecting dependencies

**In short:** it’s where all your Spring beans live and how they’re wired together.

```java
package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        MyService service = context.getBean(MyService.class);
        service.doSomething();

        context.close(); // optional but good practice
    }
}
```

Spring Boot completely automates the creation and configuration of the `ApplicationContext`.