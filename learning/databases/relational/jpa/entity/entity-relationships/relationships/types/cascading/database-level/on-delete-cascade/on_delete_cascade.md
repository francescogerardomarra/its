# `ON DELETE CASCADE`
The `ON DELETE CASCADE` cascading policy ensures that when a record in the parent table
(`User` in this case) is deleted, all related records in the child table (`Order`) are automatically deleted as well.

This helps maintain referential integrity by preventing orphaned child records that would otherwise 
reference a non-existent parent.

## SQL Table Definitions (Recap)

**`User` Table:**
```
CREATE TABLE shop_schema.User (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**`Order` Table (with `ON DELETE CASCADE`):**

```
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

The foreign key (`user_id_fk`) in the `Order` table references the `user_id` in the `User` table.

The `ON DELETE CASCADE` means that if a `User` record is deleted, any `Order` records that
reference that user will also be deleted automatically.

### Steps to Demonstrate ON DELETE CASCADE
1. Insert Sample Data into `User` Table:
   let's insert a sample user into the `User` table.
   ```
   INSERT INTO shop_schema.User (username, email) 
       VALUES ('john_doe', 'john.doe@example.com');
   ```
   After this query, suppose the `user_id` for `john_doe` is `1`.
2. Insert Sample Data into `Order` Table:
   now, let's insert some orders for `john_doe` into the `Order` table.
   These orders will be associated with `user_id_fk = 1`.
   ```
   INSERT INTO shop_schema.Order (user_id_fk, status)
          VALUES (1, 'pending'), (1, 'shipped'), (1, 'delivered');
   ```
   After this, the `Order` table will contain the following records:

   | order_id | user_id_fk | order_date          | status    |
   |----------|------------|---------------------|-----------|
   | 1        | 1          | 2025-05-06 12:00:00 | pending   |
   | 2        | 1          | 2025-05-06 12:05:00 | shipped   |
   | 3        | 1          | 2025-05-06 12:10:00 | delivered |

3. Delete a `User`:
   Now, let's delete john_doe from the User table.
   ```
   DELETE FROM shop_schema.User WHERE user_id = 1;
   ```
   **Result: Cascading Effect:**
    - since we have specified `ON DELETE CASCADE` on the foreign key constraint in the `Order` table,
      all orders associated with `john_doe (user_id_fk = 1)` will be automatically deleted.
    - after executing the `DELETE` query, the `Order` table will no longer contain any orders associated with `john_doe`.
4. Verify Deletion:
   you can check the `Order` table to confirm that the orders have been deleted:
   ```
   SELECT * FROM shop_schema.Order WHERE user_id_fk = 1;
   ```
   Since `john_doe` was deleted from the `User` table, and the cascading policy was applied,
   the query will return no rows because all orders associated with `user_id = 1` have been deleted.