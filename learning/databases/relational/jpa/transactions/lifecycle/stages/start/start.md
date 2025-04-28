# What triggers the start of a transaction?
**In Spring Data JPA, a transaction starts when:**
- a method annotated with [`@Transactional`](../transactional/description/description.md) is called.
- [**Spring AOP (Aspect-Oriented Programming)**](../aop/description/description.md) intercepts the method call and opens a transaction
before executing the method.

## **Example:**
**This:**
```java
@Transactional
public void updateEntity(MyEntity entity) {
    entity.setName("Updated");
    repository.save(entity); // transaction starts here
}
```
**is basically the equivalent of this:**
```sql
BEGIN;

UPDATE my_entity
SET name = 'Updated'
WHERE id = ?;

COMMIT;
```