# Use cases
A typical use case for cascade operations in JPA is a `@OneToMany` relationship
between a `Parent` entity and its `Child` entities.

For example, when saving or deleting a Parent entity, you want the Child entities to be automatically saved or deleted as well.
