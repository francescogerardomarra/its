# `ON UPDATE CASCADE`
The `ON UPDATE CASCADE` policy ensures that when a value in the parent table’s primary key is **updated**,
the corresponding foreign key values in the child table are **automatically updated** as well. 
This maintains referential integrity without requiring manual updates across related tables.

Although updates to primary keys are relatively rare, this feature is useful in systems where the
identifier may change (e.g. codes or usernames used as keys).

## SQL Table Definitions (Recap)

**`User` table:**
```
CREATE TABLE shop_schema.User (
    user_id INT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
**Note**: we're using `INT` instead of `SERIAL` to allow manual control over primary key updates.

**`Order` Table (with `ON UPDATE CASCADE`):**
```
CREATE TABLE shop_schema.Order (
    order_id SERIAL PRIMARY KEY,
    user_id_fk INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered', 'canceled')),
    FOREIGN KEY (user_id_fk)
        REFERENCES shop_schema.User(user_id)
        ON UPDATE CASCADE
);
```
- the `user_id_fk` in `Order` is linked to `User.user_id`;
- if `user_id` in the `User` table is updated, the change is cascaded to all related `Order` rows.

### Steps to Demonstrate `ON UPDATE CASCADE`
1. Insert a `User` with a Fixed ID
    ```
    INSERT INTO shop_schema.User (user_id, username, email)
        VALUES (100, 'dave', 'dave@example.com');
    ```
2. Insert Orders for That `User`
    ```
    INSERT INTO shop_schema.Order (user_id_fk, status)
    VALUES
        (100, 'pending'),
        (100, 'delivered');
    ```
    At this point, the `Order` table looks like:
    
    | order_id | user_id_fk | order_date          | status    |
    |----------|------------|---------------------|-----------|
    | 1        | 100        | 2025-05-06 15:00:00 | pending   |
    | 2        | 100        | 2025-05-06 15:10:00 | delivered |

3. Update the User's ID
   Now, simulate a primary key change:
   ```
   UPDATE shop_schema.User
   SET user_id = 200
   WHERE user_id = 100;
   ```
4. Cascading Effect
   - because of `ON UPDATE CASCADE`, all `user_id_fk` values in the `Order` table are automatically updated to `200`.
5. Verify the Result
    ```
    SELECT * FROM shop_schema.Order;
    ```
    Expected output:
    
    | order_id | user_id_fk | order_date          | status    |
    |----------|------------|---------------------|-----------|
    | 1        | 200        | 2025-05-06 15:00:00 | pending   |
    | 2        | 200        | 2025-05-06 15:10:00 | delivered |

⚠️ **Note**: Updating primary keys is generally discouraged in production systems.
This example is meant to demonstrate how `ON UPDATE CASCADE` works at the database level for educational purposes.
In practice, it’s better to use stable, immutable primary keys.