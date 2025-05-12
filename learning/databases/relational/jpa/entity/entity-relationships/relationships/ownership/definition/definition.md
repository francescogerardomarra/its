# Definition
Ownership refers to which side of a relationship has control over certain behaviors,
particularly in operations like deletion or updating of records.

It dictates how cascading operations (like `ON DELETE CASCADE`, `ON UPDATE CASCADE`) are handled in the database.

## Who Owns the Relationship?
In relational database design, ownership typically refers to the side of the relationship that holds
the foreign key and manages the link between related entities.

**Key Points on Ownership:**
- the "many" side of a relationship usually owns the relationship because it holds 
the foreign key that links back to the "one" side.
- the side that owns the relationship is responsible for storing the reference (foreign key)
and ensuring the integrity of the relationship;
  - this ownership typically defines how cascading operations (deletes, updates) are carried out.

**Ownership Determination at the Database Level:**
- ownership is established and determined at the database level, specifically by which table holds the foreign key;
- the side that holds the foreign key is the one that owns the relationship, and this is where cascading operations
are enforced, such as deletions or updates;
- this is typically defined in the table schema using foreign key constraints that specify cascading
behaviors (e.g., `ON DELETE CASCADE`, `ON UPDATE CASCADE`).

## **Example:**

Consider the relationship between an Order and a User in an e-commerce system.

**A user can place many orders, so we have a one-to-many relationship:**

```
CREATE TABLE shop_schema.User (
  user_id SERIAL PRIMARY KEY,
  user_name VARCHAR(100)
);
```
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

**In this case:**
- the `Order` table owns the relationship because it holds the foreign key (`user_id_fk`) that references the `User` table;
- the `User` table is the "one" side of the relationship, and `Order` is the "many" side.

If you delete a user, all the orders related to that user will also be deleted automatically because
of the `ON DELETE CASCADE` rule.