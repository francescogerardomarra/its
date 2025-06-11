# Definition
## Entity (JPA Entity)
- an entity is a special type of POJO mapped to a relational database table using JPA (Java Persistence API);
- entities are annotated with `@Entity` and must have a primary key (`@Id`);
- they allow ORM (Object-Relational Mapping) tools like Hibernate to handle database operations automatically;
- **Entities** are exchanged between the `@Service` and `@Repository` layers. The `@Repository`
is responsible for database operations (CRUD), and the `@Service` layer uses these entities to 
implement business logic.
  
Entities are POJOs that are specifically designed to map to a relational database
and typically come with JPA annotations like `@Entity` and `@Id`.

Entities are used to represent actual database records and include more logic tied to
persistence and the underlying database schema.  
