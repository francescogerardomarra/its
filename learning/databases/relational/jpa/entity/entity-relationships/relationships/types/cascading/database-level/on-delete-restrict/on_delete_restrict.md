# `ON DELETE RESTRICT`
The `ON DELETE RESTRICT` policy prevents deletion of a parent row if there are any existing child records referencing it.
This is a way to enforce strict referential integrity, ensuring that you cannot accidentally delete
data that is still being relied on elsewhere.

It is particularly useful in cases where child records must always be associated with a valid parent,
such as in financial systems, audit trails, or when records are legally required to maintain traceability.

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
**`Order` Table (with `ON DELETE RESTRICT`):**
```
CREATE TABLE shop_schema.Order (
    order_id SERIAL PRIMARY KEY,
    user_id_fk INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered', 'canceled')),
    FOREIGN KEY (user_id_fk)
        REFERENCES shop_schema.User(user_id)
        ON DELETE RESTRICT
);
```
- the foreign key `user_id_fk` in the `Order` table references the primary key `user_id` in the `User` table;
- the `ON DELETE RESTRICT` clause prevents deletion of a `User` if any `Order` rows reference that user;
- this ensures that the parent record cannot be deleted while dependent child rows exist.

## Steps to Demonstrate `ON DELETE RESTRICT`
1. Insert a `User`
   Add a new user who will place some orders:
   ```
   INSERT INTO shop_schema.User (username, email)
    VALUES ('bob', 'bob@example.com');
    -- Suppose this gets user_id = 1
   ```
2. Insert Orders for that `User`
   ```
   INSERT INTO shop_schema.Order (user_id_fk, status)
    VALUES
        (1, 'pending'),
        (1, 'shipped');
   ```
   At this point, the Order table contains:
   
   | order_id | user_id_fk | order_date          | status  |
   |----------|------------|---------------------|---------|
   | 1        | 1          | 2025-05-06 13:00:00 | pending |
   | 2        | 1          | 2025-05-06 13:05:00 | shipped |

3. Attempt to Delete the `User`
   ```
   DELETE FROM shop_schema.User WHERE user_id = 1;
   ```

   **Result: Deletion Fails**
   - the database will raise a foreign key constraint violation;
   - since `Order` rows still reference the `User`, deletion is restricted.

4. Resolution (Optional)

   To successfully delete the user, you must first delete or reassign all related `Order` records:
   ```
   DELETE FROM shop_schema.Order WHERE user_id_fk = 1;
   -- Then:
   DELETE FROM shop_schema.User WHERE user_id = 1;
   ```