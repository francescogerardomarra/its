# Implementation
At the database level, cascading policies are implemented through foreign key constraints.
These constraints define how changes in the parent table (in this case, the `User` table) should
affect related records in child tables (such as the `Order` table).
Cascading policies are essential for maintaining referential integrity between related tables
and ensuring that changes to parent records automatically propagate to dependent child records.

**The main cascading actions that can be defined in a foreign key constraint are:**
- `ON DELETE`
- `ON UPDATE`

These policies can specify whether related records should be deleted, updated, 
or modified when the parent record is deleted or updated.

## Example: `User` and `Order` Tables

**Let's use the following `User` and `Order` tables to explain how cascading policies are implemented:**

**`User` Table:**
```
CREATE TABLE shop_schema.User (
  user_id SERIAL PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**`Order` Table:**
```
CREATE TABLE shop_schema.Order (
  order_id SERIAL PRIMARY KEY,
  user_id_fk INT NOT NULL,
  order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered', 'canceled')),
  FOREIGN KEY (user_id_fk) REFERENCES shop_schema.User(user_id) ON DELETE CASCADE
);
```

In the current setup, the `Order` table has a foreign key (`user_id_fk`) that references the `user_id` in the `User` table.
The foreign key constraint includes the `ON DELETE CASCADE` policy, which means that when a `User` is deleted,
all related `Order` records are automatically deleted.
Let's dive deeper into how this and other cascading policies work with these tables.

**When to Use Cascading in the Database:**
- `ON DELETE CASCADE`:
  - use this when you want to automatically delete dependent records in child tables when
  the parent record is deleted;
  - commonly used in scenarios like `Order` and `Customer`, where deleting
  a customer should automatically delete all their orders.
- `ON DELETE SET NULL`:
  - use this when you want to preserve child records but nullify the foreign key 
  value when the parent record is deleted;
  - this is useful when child records should not be deleted but need 
  to reflect the absence of the parent.
- `ON UPDATE CASCADE`:
  - this is used when you want changes to a parent record’s primary key to be
  reflected in all associated child records.
- `ON DELETE RESTRICT or NO ACTION`:
  - these are used when you want to prevent the deletion of a parent
  record if there are any dependent child records;
  - this ensures that foreign key constraints are
  respected and helps preserve data integrity.


