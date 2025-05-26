## `ON DELETE SET DEFAULT`
The `ON DELETE SET DEFAULT` cascading policy ensures that when a record in the parent table (`User`) is deleted,
the foreign key in the related child records (`Order`) is set to its defined default value.

This can be useful when you want to retain the child records but have them point to a fallback or
placeholder parent, such as a 'guest' or 'unknown' user.

This policy helps maintain referential integrity without losing the child data, and it avoids 
the foreign key violation that would occur if the key were left referencing a non-existent parent.

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
**`Order` Table (with `ON DELETE SET DEFAULT`):**
```
CREATE TABLE shop_schema.Order (
    order_id SERIAL PRIMARY KEY,
    user_id_fk INT NOT NULL DEFAULT 0,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered', 'canceled')),
    FOREIGN KEY (user_id_fk)
        REFERENCES shop_schema.User(user_id)
        ON DELETE SET DEFAULT
);
```

- the foreign key `user_id_fk` in the `Order` table references the primary key `user_id` in the `User` table;
- the column `user_id_fk` is defined with a **default value of `0`**, which must correspond to an existing record
in the `User` table (typically a placeholder like "guest");
- when a user is deleted, any `Order` rows that referenced them will have `user_id_fk` automatically set to `0`.

## Steps to Demonstrate `ON DELETE SET DEFAULT`

1. Insert Placeholder `User (ID = 0)`
    To make use of `SET DEFAULT`, we must first ensure a default user with ID `0` exists:
    ```
    INSERT INTO shop_schema.User (user_id, username, email)
        VALUES (0, 'guest', 'guest@example.com');
    ```
    
    This user acts as a neutral fallback reference for orphaned orders.

2. Insert Real `User`

    Insert a standard user to associate with some orders:
    
    ```
    INSERT INTO shop_schema.User (username, email)
        VALUES ('alice', 'alice@example.com');
        -- Suppose this gets user_id = 1
    ```

3. Insert `Orders` for Real `User`

    Now insert some orders that are tied to `alice` (user_id = 1):
    ```
    INSERT INTO shop_schema.Order (user_id_fk, status)
        VALUES
            (1, 'pending'),
            (1, 'shipped'),
            (1, 'delivered');
    ```

    At this point, the `Order` table contains:

    | order_id | user_id_fk | order_date          | status    |
    |----------|------------|---------------------|-----------|
    | 1        | 1          | 2025-05-06 12:00:00 | pending   |
    | 2        | 1          | 2025-05-06 12:05:00 | shipped   |
    | 3        | 1          | 2025-05-06 12:10:00 | delivered |

4. Delete the Real `User`

    We now delete the user record for `alice`:
    ```
    DELETE FROM shop_schema.User WHERE user_id = 1;
    ```
    **Result: Cascading Effect:**
      - due to `ON DELETE SET DEFAULT`, the `user_id_fk` in the `Order` table is now automatically set to the default value of `0`;
      - the actual order rows are not deleted, only their reference to the deleted user is redirected to the `guest` account.

5. Verify Results

    Check the `Order` table:
    ```
    SELECT * FROM shop_schema.Order;
    ```
    Expected output:
    
    | order_id | user_id_fk | order_date          | status    |
    |----------|------------|---------------------|-----------|
    | 1        | 0          | 2025-05-06 12:00:00 | pending   |
    | 2        | 0          | 2025-05-06 12:05:00 | shipped   |
    | 3        | 0          | 2025-05-06 12:10:00 | delivered |
