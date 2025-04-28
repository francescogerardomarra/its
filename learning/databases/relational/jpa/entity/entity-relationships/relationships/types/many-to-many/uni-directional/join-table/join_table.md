# `@JoinTable`
**In Java, `@JoinTable` has the following characteristics:**
- it defines the purpose of reflection to specify the table that is used for many-to-many or one-to-many join operations between entities;
- it maps the intermediate join table that associates two entities, typically for a many-to-many relationship;
- it contains details such as the table name, join columns, and inverse join columns;
- it allows customization of how the relationship is handled at the database level, providing flexibility in the join table structure;
- it is placed on the owning side of the relationship in the entity class.
