# `ON DELETE NO ACTION`
The `ON DELETE NO ACTION` policy means that no automatic change will occur in the child table when
a referenced parent row is deleted. However, referential integrity is still enforced: the database
will prevent deletion of the parent row if any child rows reference it.

In practice, `NO ACTION` behaves similarly to `RESTRICT`, but enforcement timing can differ slightly.
With most databases, they act the same unless deferrable constraints are involved.

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
**`Order` Table (with `ON DELETE NO ACTION`):**
```
CREATE TABLE shop_schema.Order (
    order_id SERIAL PRIMARY KEY,
    user_id_fk INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered', 'canceled')),
    FOREIGN KEY (user_id_fk)
        REFERENCES shop_schema.User(user_id)
        ON DELETE NO ACTION
);
```
- the `user_id_fk` column must always refer to a valid `User`;
- if a referenced `User` is deleted, and orders still point to them, the deletion is blocked;
- no fallback or update happens automatically; you must manually resolve references first.

### Steps to Demonstrate `ON DELETE NO ACTION`
1. Insert a `User`
    ```
    INSERT INTO shop_schema.User (username, email)
        VALUES ('carol', 'carol@example.com');
        -- Suppose this gets user_id = 1
    ```
2. Insert Related Orders
    ```
    INSERT INTO shop_schema.Order (user_id_fk, status)
    VALUES
        (1, 'pending'),
        (1, 'shipped');
    ```
    Current `Order` table:
    
    | order_id | user_id_fk | order_date          | status  |
    |----------|------------|---------------------|---------|
    | 1        | 1          | 2025-05-06 14:00:00 | pending |
    | 2        | 1          | 2025-05-06 14:05:00 | shipped |

3. Attempt to Delete the `User`
    ```
    DELETE FROM shop_schema.User WHERE user_id = 1;
    ```
    **Result: Deletion Fails**
      - the deletion is blocked due to existing child references;
      - this enforces strict referential integrity, with no automatic redirection or nulling.

4. Cleanup for Successful Deletion
    To delete the user:
    ```
    DELETE FROM shop_schema.Order WHERE user_id_fk = 1;
    DELETE FROM shop_schema.User WHERE user_id = 1;
    ```