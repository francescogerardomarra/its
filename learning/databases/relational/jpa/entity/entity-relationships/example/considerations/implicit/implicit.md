# Implicit junction table
# Join Table in Many-to-Many Relationships in Hibernate

In a typical many-to-many relationship, Hibernate can automatically handle the creation of a join table if the relationship does not require any additional columns beyond those that represent the foreign keys from both sides of the relationship.

## Automatic Join Table Creation

In the case of a simple many-to-many relationship between two entities (e.g., `Order` and `Item`), Hibernate can create a join table to hold the foreign keys of both entities. The join table will contain two columns:

- `order_id` referencing the `Order` entity
- `item_id` referencing the `Item` entity

Hibernate handles this automatically without requiring any extra configuration or additional columns in the join table.

## Custom Join Table with Additional Columns

However, when there is a need to store additional information in the join table (e.g., the quantity of items for a specific order), the join table cannot be automatically created by Hibernate. In such cases, an explicit junction table (e.g., `order_item`) is required, which not only stores the foreign keys but also includes the additional column(s), like `quantity`, to capture the necessary data.

### Example

If you want to quantify how many items are associated with each order, you would need an additional `quantity` column in the `order_item` table. This table would contain three columns:

- `order_id` (foreign key referencing the `Order` entity)
- `item_id` (foreign key referencing the `Item` entity)
- `quantity` (to store the quantity of items)

### Why Hibernate Can't Create This Table Automatically

Since Hibernate cannot automatically create a table with extra columns (like `quantity`), it is necessary to explicitly define the `order_item` table and the mapping logic in your entity classes to reflect this requirement.

