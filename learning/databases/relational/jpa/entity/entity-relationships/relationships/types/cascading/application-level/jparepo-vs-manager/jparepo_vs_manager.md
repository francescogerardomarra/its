# JpaRepository vs Entity Manager
When using `JpaRepository` in Spring Data JPA, you interact with higher-level abstractions that
**simplify** persistence operations such as saving, updating, and deleting entities.
This approach **abstracts away** the direct use of `EntityManager`, which means you don't explicitly
invoke operations like `persist()` or `merge()`.

However, **cascade policies** still apply in this context, and they continue to work 
the same way as they would with `EntityManager`, but the underlying details are **abstracted** away.

## How Does It Work Without `EntityManager`?
In a `JpaRepository` approach, you don't have direct access to `EntityManager`.
Instead, Spring Data JPA automatically handles most operations for you via its `save()`,
`findById()`, and other methods.

**Specifically:**
- `save()`: this method **combines the behaviors** of `persist()` and `merge()` under the hood:
  - if the entity is **new** (i.e., it doesn't have an ID or primary key), `save()` behaves like `persist()`
  and inserts the entity into the databaseL;
  - if the entity already exists (i.e., it has an ID and is potentially **detached**), `save()`
  behaves like `merge()` and **updates** the entity in the database.

So, when you call `save()` on an entity, Spring Data JPA is essentially managing the persistence 
and merging of the entity for you, but this is all handled in the background.
You don't explicitly call `merge()` or `persist()`—Spring Data JPA abstracts these operations.

## Cascade Policies Still Apply

Even though you are using the `JpaRepository` approach, **cascade policies** defined on your relationships
(such as `CascadeType.ALL`) will still apply as expected.

**For example:**
- if a **parent entity** (e.g., `User`) is **saved** using the `save()` method,
all related **child entities** (e.g., `Order` entities) will also be persisted,
updated, or deleted automatically based on the cascading behavior defined.

The cascading behavior is defined at the application level
(via annotations like `@OneToMany`, `@OneToOne`, etc.), and it is still enforced even though
you don't manually call `persist()` or `merge()`.
Spring Data JPA takes care of propagating the cascading operations based on the entity's 
state (whether it's **new** or **detached**).

## Cascade Behavior with `EntityManager`
When you use `EntityManager` directly (for example, in a non-Spring-managed context),
you explicitly call `persist()`, `merge()`, and other methods.
However, the **cascade policy** still applies, whether you are using `EntityManager` or
`JpaRepository` because the cascade is determined at the **entity relationship level**
(via the annotations on the parent/child entities).

For example, if you manually call `merge()` on a `User` entity using the `EntityManager`, 
all related `Order` entities will still be merged or saved depending on the cascade settings.


