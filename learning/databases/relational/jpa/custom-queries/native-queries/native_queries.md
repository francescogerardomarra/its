# Native queries
When JPQL does not provide the necessary capabilities, we can use native SQL queries..


### **Meaningful Example of a Native Query (Where JPQL Is Not Sufficient)**
A truly significant example where JPQL is not sufficient is when we need to use
database-specific functions, perform complex joins on unmapped tables, query views,
stored procedures, or tables in a schema different from the default one used by the entities.

### **Example: Join with a Table That Is Not Mapped as a JPA Entity**

Imagine you have a table called `audit_logs` that is **not mapped** as a JPA entity, but
you want to perform a join between the `person` table (which **is** a mapped entity) and `audit_logs`.

```java
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query(
            value = "SELECT p.* FROM my_schema.person p " +
                    "JOIN my_schema.audit_logs a ON p.id = a.person_id " +
                    "WHERE a.event_type = :eventType",
            nativeQuery = true
    )
    List<Person> findPeopleByAuditEvent(@Param("eventType") String eventType);
}
```
### Why isn't JPQL sufficient here?
- JPQL cannot perform joins with tables that are not mapped as entities (audit_logs in this case);
- you cannot explicitly specify a schema (like my_schema) in JPQL.

**This is because mapping such heavy entities can slow down the application, consume memory, 
and complex operations are rarely performed on them using JPA.**

## What to do when we can't use JPQL?
When JPQL does not provide the necessary capabilities, we can use native SQL queries;
in the following, the `@Query` annotation has the `nativeQuery = true` attribute.
This signifies that the query is written in native SQL, which is the actual SQL syntax 
supported by the underlying database.
```java
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query(value = "SELECT * FROM my_schema.person WHERE city = :city", nativeQuery = true)
    List<Person> findPeopleByCityNative(@Param("city") String city);
}
```
the `@Query` annotation has the `nativeQuery = true` attribute.
This signifies that the query is written in native SQL, which is the actual SQL
syntax supported by the underlying database.

**In our case, the query is directly written as:**
```
SELECT * FROM my_schema.person WHERE city = :city
```
This is native SQL because it's not using the entity object model or JPQL.
It directly refers to the database schema and tables.

Putting `nativeQuery = true` in the `@Query` annotation explicitly tells Spring Data JPA
to treat the query as native SQL rather than JPQL. This is the primary way to distinguish
between the two types of queries.

**JPQL:**

Refers to entities and their fields, not the underlying database tables or columns.

**For example:**
```
@Query("SELECT p FROM Person p WHERE p.city = :city") List<Person> findPeopleByCity(@Param("city") String city);
```
Here, `Person` is an entity, and `city` is a field of the `Person` entity, not a database column.

**Native SQL:**

Refers to the actual database tables and columns.

**For example:**
```
@Query(value = "SELECT * FROM my_schema.person WHERE city = :city", nativeQuery = true) List<Person> findPeopleByCityNative(@Param("city") String city);
```
This query is native SQL because it explicitly refers to the `my_schema.person` table
and the city column from the underlying database.

JPQL (Java Persistence Query Language) is designed to work with entity
mappings and object-oriented models rather than directly interacting with
the underlying database schema (like specific schemas or tables)

JPQL operates on entities (Java objects), not on database tables.

When you write a JPQL query, you're querying the entity class and its fields,
not the actual database table or column names.

JPQL abstracts away database-specific details like schemas, table names, and column names.
It operates on the object model defined by your JPA entities.

**For example, if you want to query a schema or table that isn't directly mapped to an entity
class or is part of a different schema, JPQL won't allow you to specify a schema:**

```
// This is incorrect in JPQL @Query("SELECT p FROM my_schema.Person p WHERE p.city = :city") // JPQL won't accept this
```

**USAGE**

```java
@Autowired
private PersonRepository personRepository;

public void findPeopleInCityNativeExample() {
    List<Person> people = personRepository.findPeopleByCityNative("Los Angeles");
    people.forEach(person -> System.out.println(person.getName()));
}
```