# DB build

## 1. Login to PostgreSQL CLI
Since the database `my_app_db` is already created, log in to the PostgreSQL CLI:
```bash
sudo -u postgres psql
```
Then switch to `my_app_db`:
```sql
\c my_app_db
```

## 2. Create Schema
We create a new schema named `shop_schema` to encapsulate the tables:
```sql
CREATE SCHEMA shop_schema;
```

## 3. Populate the schema
[Here](resources/scripts/populate) you can use these scripts to populate the schema for this particular example.

## 4. Verify Table Creation
To check that all tables have been created correctly, use:
```sql
\dt shop_schema.*
```
This will list all tables in the `shop_schema` schema.

## 5. Conclusion
Now, we have:
- **`User`**: Stores user information.
- **`Order`**: Stores orders placed by users (one-to-many with `User`).
- **`Item`**: Stores product details (many-to-many with `Order`).
- **`Order_Item`**: Join table linking orders and items (many-to-many relationship).

**Naming Convention:**
- **Foreign keys should have the `_fk` suffix** to clearly indicate their role.
- This makes queries more readable and avoids confusion.
- Example: `user_id_fk` instead of just `user_id`.