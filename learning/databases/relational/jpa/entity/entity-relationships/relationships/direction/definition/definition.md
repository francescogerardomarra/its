# Definition
**Direction has the following characteristics:**
- it refers to the navigability of the relationship between entities;
- it determines whether one or both entities are aware of the relationship.

Direction refers to the navigability or point of reference in a relationship between two tables
at the database level or the equivalent entities at the application level.

Specifically, which side references the other, and how data flows or is accessed between them.


The direction of a relationship is primarily defined at the database level, based on foreign key constraints.

However, how that direction is interpreted or used is often defined and enforced at the application level.


At the database level, direction comes from foreign keys. For example:

```java
FOREIGN KEY (user_id_fk) REFERENCES shop_schema.User(user_id)
```


This says: each Order "points to" or is associated with one User.
That implies a one-to-many relationship (1 User → many Orders).

Application level:

At the application level (in ORM frameworks like Hibernate, SQLAlchemy, Entity Framework, etc.), you often explicitly model the relationship in both directions:

- On the User model, you might have: user.orders (a list of that user’s orders).
- On the Order model, you might have: order.user (the user who made the order).

So the application defines the navigability of the relationship (which side can "see" or query the other), while the database defines the structure and constraints of the relationship.
imagine:

You have two tables: Table A and Table B.
- Table A contains a foreign key that references Table B.

Then, the direction of the relationship is:
- A → B

This means:
- Table A is the "many" side of the relationship.
- Table B is the "one" side of the relationship.

The foreign key in Table A points to or references Table B. As a result, the relationship is directed from A to B.

In other words:
- Each record in Table A is associated with exactly one record in Table B.
- Table A depends on or relies on Table B.

In More Detail:

- In a relational database, direction is established by foreign keys.
  - The table with the foreign key "points to" the referenced table.
  - Example: If Order.user_id_fk references User.user_id, then:
    - The relationship is directed from Order to User.
    - You can say: "Each order belongs to a user."
  - In an application or ORM, direction determines how you navigate relationships in your code.
    - One-way: Only one side can access the other (e.g., order.user, but no user.orders).
    - Two-way (bi-directional): Both sides can navigate the relationship.
