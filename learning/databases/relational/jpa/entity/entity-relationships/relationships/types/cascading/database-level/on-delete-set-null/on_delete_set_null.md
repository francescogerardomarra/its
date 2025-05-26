# `ON DELETE SET NULL`
The `ON DELETE SET NULL` cascading policy ensures that when a record in the parent table
(`User` in this case) is deleted, all related foreign key values in the child table (`Order`)
are automatically set to `NULL`.

This way, the child records remain in the table but are no longer associated with a deleted parent record.

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

**`Order` Table (with `ON DELETE SET NULL`):**

```
CREATE TABLE shop_schema.Order (
    order_id SERIAL PRIMARY KEY,
    user_id_fk INT,  -- must allow NULLs
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered', 'canceled')),
    FOREIGN KEY (user_id_fk) REFERENCES shop_schema.User(user_id) ON DELETE SET NULL
);
```

The foreign key (`user_id_fk`) in the `Order` table references the `user_id` in the `User` table.

The `ON DELETE SET NULL` means that if a `User` record is deleted, the `user_id_fk` in the `Order`
table will be set to `NULL` for all related orders.


**Note**: `user_id_fk` must allow `NULLs` for `ON DELETE SET NULL` to work properly.

### Steps to Demonstrate `ON DELETE SET NULL`

1. **Insert Sample Data into `User` Table**:  
   Let's insert a sample user into the `User` table.
   ```sql
   INSERT INTO shop_schema.User (username, email) 
       VALUES ('jane_doe', 'jane.doe@example.com');
   ```
   After this query, suppose the `user_id` for `jane_doe` is `1`.

2. **Insert Sample Data into `Order` Table**:  
   Now, let's insert some orders for `jane_doe` into the `Order` table.  
   These orders will be associated with `user_id_fk = 1`.
   ```sql
   INSERT INTO shop_schema.Order (user_id_fk, status)
       VALUES (1, 'pending'), (1, 'shipped'), (1, 'delivered');
   ```
   After this, the `Order` table will contain the following records:

   | order_id | user_id_fk | order_date          | status    |
   |----------|------------|---------------------|-----------|
   | 1        | 1          | 2025-05-06 12:00:00 | pending   |
   | 2        | 1          | 2025-05-06 12:05:00 | shipped   |
   | 3        | 1          | 2025-05-06 12:10:00 | delivered |

3. **Delete a `User`**:  
   Now, let's delete `jane_doe` from the `User` table.
   ```sql
   DELETE FROM shop_schema.User WHERE user_id = 1;
   ```
   **Result: Cascading Effect:**
    - Since we have specified `ON DELETE SET NULL` on the foreign key constraint in the `Order` table, the `user_id_fk` in the related orders will be **set to `NULL`** when `jane_doe` is deleted from the `User` table.
    - After executing the `DELETE` query, the orders will remain in the `Order` table, but they will **no longer reference** any user.

4. **Verify Deletion**:  
   You can check the `Order` table to confirm that the `user_id_fk` has been set to `NULL`:
   ```sql
   SELECT * FROM shop_schema.Order WHERE user_id_fk IS NULL;
   ```
   The `Order` table will now look like this:

   | order_id | user_id_fk | order_date          | status    |
   |----------|------------|---------------------|-----------|
   | 1        | NULL       | 2025-05-06 12:00:00 | pending   |
   | 2        | NULL       | 2025-05-06 12:05:00 | shipped   |
   | 3        | NULL       | 2025-05             | delivered |
   Since the `ON DELETE SET NULL` rule was in place, all the orders remain but the foreign key `user_id_fk` is now `NULL`.
