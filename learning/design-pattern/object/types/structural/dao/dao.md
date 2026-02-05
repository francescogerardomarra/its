# DAO
**DAO has the following characteristics:**
- it stands for **Data Access Object**;
- It's a design pattern used to abstract and encapsulate
all access to a data source (such as a database) within a single object;
- DAOs centralize common data operations, such as CRUD 
(Create, Read, Update, Delete), and provide a clean interface 
for interacting with the underlying data source.

**Here's an example of a simple DAO interface in Java:**
```java
import java.util.List;

public interface UserDao {
    User getById(long id);
    List<User> getAll();
    void insert(User user);
    void update(User user);
    void delete(User user);
}
```
**In this example:**
- `UserDao` is the **DAO interface**;
- it declares methods for performing CRUD operations on user
objects (`getById`, `getAll`, `insert`, `update`, `delete`).

**Below is an example of a DAO implementation:**
```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private Map<Long, User> users = new HashMap<>();
    private long nextId = 1;

    @Override
    public User getById(long id) {
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void insert(User user) {
        user.setId(nextId++);
        users.put(user.getId(), user);
    }

    @Override
    public void update(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }
}
```
**In this implementation (`UserDaoImpl`):**
- a simple in-memory storage (a Map) is used to store user objects;
- in a real-world scenario, this storage mechanism would be replaced
with a database or any other data source.

DAO pattern promotes separation of concerns by keeping the 
data access logic separate from business logic.

It also provides a centralized and reusable way to access data,
which can improve maintainability and testability of an application.
