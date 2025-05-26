# `ON UPDATE SET NULL`
The `ON UPDATE SET NULL` cascading policy ensures that when a record in the parent
table (`User`) has its primary key updated, any foreign key in the child table (`Order`) that
referenced it will be set to `NULL`. This allows the child records to remain but without a valid reference to the parent.

This option is useful when you want to preserve the child rows but clearly indicate that they no longer
have a valid link to the updated parent.

## SQL Table Definitions (Recap)
**`User` Table:**
```
CREATE TABLE shop_schema.User (
    user_id INT PRIMARY KEY,  -- no SERIAL so we can update the ID
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
**`Order` Table (with `ON UPDATE SET NULL`):**
```
CREATE TABLE shop_schema.Order (
    order_id SERIAL PRIMARY KEY,
    user_id_fk INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered', 'canceled')),
    FOREIGN KEY (user_id_fk)
        REFERENCES shop_schema.User(user_id)
        ON UPDATE SET NULL
);
```
### Steps to Demonstrate `ON UPDATE SET NULL`
1. Insert a `User`
    ```
    INSERT INTO shop_schema.User (user_id, username, email)
        VALUES (10, 'charlie', 'charlie@example.com');
    ```
2. Insert Orders for That `User`
    ```
    INSERT INTO shop_schema.Order (user_id_fk, status)
    VALUES
        (10, 'pending'),
        (10, 'shipped');
    ```
   Now the `Order` table contains:
   
   | order_id | user_id_fk | order_date          | status  |
   |----------|------------|---------------------|---------|
   | 1        | 10         | 2025-05-06 12:00:00 | pending |
   | 2        | 10         | 2025-05-06 12:05:00 | shipped |

3. Update the User ID
    ```
    UPDATE shop_schema.User SET user_id = 99 WHERE user_id = 10;
    ```
4. **Cascading Effect**
   - because we defined `ON UPDATE SET NULL`, the `user_id_fk` values in the `Order` table are now automatically set to `NULL`;
   - the child rows remain, but the relationship to the user is severed.
5. Verify results
   ```
   SELECT * FROM shop_schema.Order;
   ```
   Expected output:
   
   | order_id | user_id_fk | order_date          | status  |
   |----------|------------|---------------------|---------|
   | 1        | NULL       | 2025-05-06 12:00:00 | pending |
   | 2        | NULL       | 2025-05-06 12:05:00 | shipped |

⚠️ **Note:**
- like with `ON UPDATE CASCADE`, updating primary keys is not common in production systems;
- this example is meant to illustrate behavior at the database level;
- in practice, it's better to design schemas where primary keys are immutable, 
and this feature is used for natural keys or rare reconciliation cases.