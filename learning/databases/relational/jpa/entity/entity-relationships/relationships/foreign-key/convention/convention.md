# Naming conventions
In our database schema, we follow consistent column naming conventions
for primary and foreign keys.

**Primary Keys**
- use the pattern `<entity_name>_id`;
- **example:** in the `User` table, the primary key column is called `user_id`.

**Foreign Keys**
- use the pattern `<referenced_entity_name>_id_fk`;
- the suffix `_fk` clearly marks this column as a foreign key;
- **example:** in the `Order` table, a column referencing `User(user_id)` is called `user_id_fk`.


## `User` Table
```
CREATE TABLE shop_schema.User (
    user_id   SERIAL PRIMARY KEY,
    username  VARCHAR(50) NOT NULL UNIQUE,
    email     VARCHAR(100) NOT NULL UNIQUE
    -- other columns...
);
```
**Explanation:**
- `user_id` is the primary key for `User`.

## `Order` Table
```
CREATE TABLE shop_schema.Order (
    order_id    SERIAL PRIMARY KEY,
    user_id_fk  INT NOT NULL,
    order_date  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    status      VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered', 'canceled')),

    FOREIGN KEY (user_id_fk)
      REFERENCES shop_schema.User(user_id)
      ON DELETE CASCADE
);
```
**Explanation:**
- `order_id` is the primary key `Order`;
- `user_id_fk` is a foreign key referencing  `User(user_id)`.

## `Order_Item` Table (Join Table)
```
CREATE TABLE shop_schema.Order_Item (
    order_id_fk  INT NOT NULL,
    item_id_fk   INT NOT NULL,
    quantity     INT NOT NULL CHECK (quantity > 0),

    PRIMARY KEY (order_id_fk, item_id_fk),
    FOREIGN KEY (order_id_fk)
      REFERENCES shop_schema.Order(order_id)
      ON DELETE CASCADE,
    FOREIGN KEY (item_id_fk)
      REFERENCES shop_schema.Item(item_id)
      ON DELETE CASCADE
);
```
- **Composite primary key:** (`order_id_fk`, `item_id_fk`);
- Both columns follow the `<entity>_id_fk` pattern.