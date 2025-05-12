# JPARepository
Spring Data JPA is a part of the Spring framework that simplifies
working with databases using Java Persistence API (JPA).
It eliminates boilerplate code by providing built-in methods for
common database operations like saving, updating, deleting, and retrieving data.

`JpaRepository` is an interface in Spring Data JPA that extends `CrudRepository` 
and `PagingAndSortingRepository`, providing additional functionalities such as 
pagination, sorting, and more advanced query methods.

## Example
[Here](../../../../../web-services/attachments/22-spring-boot-auto-datasource/java21/java21_springbootdatajpa.zip), you can find an example where we create a layered Spring application
with a PostgreSQL database (this example makes use of Spring Data JPA and `JpaRepository`).

**This project follows a structured architecture with three main layers:**
- **repository layer**: defines data access methods using `JpaRepository`, but does not provide implementations;
- **service layer**: implements business logic by calling the repository methods;
- **controller layer**: exposes `RESTful` endpoints and handles HTTP requests.

Since the repository layer inherits the method signatures from `JpaRepository`,
we can automatically use the standard method implementations from hibernate.

In our case we make use of the `save()` method to insert or update an entity
into our postgresql database table called `person`.

```java
package com.example.service;

import com.example.model.Person;
import com.example.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Save or update a person
    public Person savePerson(Person person) {
        return personRepository.save(person); // Uses JpaRepository's save() method
    }

    // Fetch people by city using repository
    public List<Person> findPeopleByCity(String city) {
        return personRepository.findPeopleByCity(city);
    }
}
```

For this application we use a specific database schema called `my_schema`.

The way to set up this schema is present [here](../../../../../web-services/22_spring_boot_auto_datasource.md).
