# Example
**Here is an example that showcases how foreign keys work:**

1. **`User` Table:**

| user_id | username  | email                                             |
|---------|-----------|---------------------------------------------------|
| 1       | john_doe  | [john.doe@email.com](mailto:john.doe@email.com)   |
| 2       | jane_doe  | [jane.doe@email.com](mailto:jane.doe@email.com)   |
| 3       | bob_smith | [bob.smith@email.com](mailto:bob.smith@email.com) |

2. **`Order` Table:**

| order_id | user_id_fk | order_date          | status    |
|----------|------------|---------------------|-----------|
| 1001     | 1          | 2025-04-25 10:15:00 | pending   |
| 1002     | 1          | 2025-04-26 11:20:00 | shipped   |
| 1003     | 2          | 2025-04-27 14:00:00 | delivered |
| 1004     | 3          | 2025-04-28 09:30:00 | canceled  |
| 1005     | 2          | 2025-04-29 15:45:00 | pending   |

```
-- Create schema (optional)
CREATE SCHEMA IF NOT EXISTS shop_schema;

-- User table
CREATE TABLE shop_schema.User (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

-- Order table
CREATE TABLE shop_schema.Order (
    order_id SERIAL PRIMARY KEY,
    user_id_fk INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered', 'canceled')),
    FOREIGN KEY (user_id_fk)
        REFERENCES shop_schema.User(user_id)
        ON DELETE CASCADE
);
```
### Relationship Explanation

- **Who owns the relationship?**  
  - the `Order` table owns the relationship because it contains the foreign key `user_id_fk`.
- **What type of relationship is it?**  
  - this is a one-to-many relationship:  
  - a user can have many orders, but each order belongs to only one user.
- **How is the relationship created?**  
  - the foreign key `user_id_fk` in the `Order` table references the primary key `user_id` in the `User` table.  
  - this is what establishes the link between the two tables.
- **Effect of `ON DELETE CASCADE`:**  
  - if a user is deleted from the `User` table, all orders associated with that user will automatically be deleted as well.
