# Spring bean vs Java Class
| Feature                   | Spring Bean                                                         | Java Class                                        |
|---------------------------|---------------------------------------------------------------------|---------------------------------------------------|
| **Definition**            | A Java object **managed by the Spring container**                   | A plain Java object, not managed by any framework |
| **Instantiation**         | Created and controlled by Spring using configuration or annotations | Created manually via `new` keyword                |
| **Dependency Injection**  | Supports automatic dependency injection (via Spring)                | No built-in dependency injection                  |
| **Lifecycle Management**  | Full lifecycle (init, destroy, etc.) handled by Spring              | Developer handles the lifecycle manually          |
| **Configuration Methods** | Defined using annotations (`@Component`, `@Service`, etc.) or XML   | Defined in code without external configuration    |
| **Scope Management**      | Spring supports scopes like `singleton`, `prototype`, etc.          | No built-in concept of scope                      |
| **Framework Integration** | Integrates tightly with other Spring features (AOP, Tx, etc.)       | No automatic integration with frameworks          |

**Summary:**
- a Java class is just a blueprint;
- a Spring Bean is a Java class that has been registered with Spring so the 
framework can manage its lifecycle, wiring, and configuration.
