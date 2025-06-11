# Definition
## DTO (Data Transfer Object)
- A **DTO** is a simple object that holds data and is specifically designed to transfer data
between layers, such as from the `@Service` layer to the `@Controller` layer;
- It helps in **separating** the internal representation of data (entities) from what is exposed
to the client, providing a clear and controlled interface;
- DTOs are used for data transfer, especially when there is a need to move data between different 
layers of an application (e.g., from backend to frontend);
- DTOs are typically designed to expose only the necessary data needed by external consumers
(e.g., API clients), hiding unnecessary internal details like database-specific annotations or methods;
- **DTOs** are exchanged between the `@Service` and `@Controller` layers. The `@Service` 
prepares the data (often based on entities) and passes it to the `@Controller` layer, 
which then sends it to the client (e.g., via an API);
- By using DTOs, you can avoid exposing unnecessary internal details of your application,
preventing issues such as **recursive nesting** or data over-fetching.
