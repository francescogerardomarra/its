# Column-name convention
By default, JPA uses certain naming conventions
for foreign key columns based on the names of 
the associated entities and their primary key fields.

**If you don't specify a column name explicitly,
JPA will derive the foreign key column name as follows:**
- if you have a field in an entity class that represents a relationship, and it's named after another entity, JPA typically combines the field name with the primary key column name of the referenced entity.
- **for example:**
  - if an `Order` entity has a `Customer` field referencing
  a `Customer` entity, and `Customer` has a primary 
  key named `id`, the foreign key column in the `Order`
  table might be named `customer_id`;
- however, you can customize the column name using
  the `@JoinColumn` annotation.
