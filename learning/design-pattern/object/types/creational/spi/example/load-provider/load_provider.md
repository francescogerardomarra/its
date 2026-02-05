# Load the Service Providers

- after [registering](../register-provider/file-creation/file_creation.md) the service providers, we load them, within the main class using the `ServiceLoader` java library class;
- see the location of this interface within the [project structure](../project-structure/project_structure.md).

**Code overview:**

- when the `ServiceLoader.load(PaymentProcessor.class)` method is called, the `ServiceLoader` searches for the `META-INF/services/main.service.PaymentProcessor` file in the classpath;
- then it reads this file and instantiates each listed implementation, allowing the application to iterate over them and invoke their methods;
- **this is because we are not using JPMS in this example**;


- JPMS doesn't need to use the `META-INF/services/main.service.PaymentProcessor` to register the service providers, but use the [provides](../../../../../../../java/chapter-x/versions/java9/features/jpms/after/module/how-work/module-info/directive/use-provide/provide/definition/definition.md) directive.

**Code:**

```java
import main.service.PaymentProcessor;
import java.util.ServiceLoader;

public class PaymentLoader {
    public static void main(String[] args) {
        ServiceLoader<PaymentProcessor> serviceLoader = ServiceLoader.load(PaymentProcessor.class);

        for (PaymentProcessor processor : serviceLoader) {
            processor.processPayment(100.00);
        }
    }
}
```
