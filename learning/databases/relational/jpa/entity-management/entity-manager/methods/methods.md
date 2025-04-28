# Methods
| Operation           | Method               | Description                                    |
|---------------------|----------------------|------------------------------------------------|
| **Create (Insert)** | `persist(entity)`    | Makes a new entity persistent (adds to DB)     |  
| **Read (Find)**     | `find(Class<T>, id)` | Fetches an entity by its primary key           |
| **Update**          | `merge(entity)`      | Merges a detached entity back into the context |
| **Delete**          | `remove(entity)`     | Marks an entity for deletion                   |
| **Query**           | `createQuery(...)`   | Executes JPQL or native SQL queries            |
| **Transaction**     | `getTransaction()`   | Begins, commits, or rolls back transactions    |