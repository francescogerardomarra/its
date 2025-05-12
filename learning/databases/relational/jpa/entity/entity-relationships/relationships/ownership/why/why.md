# Why does it matter?
Understanding ownership is important for handling cascading operations, ensuring that relationships
are maintained properly and that operations like deletions or updates don’t break referential integrity.

Cascading Deletes:

- the "many" side, which holds the foreign key, controls cascading delete operations.
- if a User is deleted, the Order records associated with that user will also be deleted automatically (via ON DELETE CASCADE).
- this ensures that no "orphaned" records (like orders that point to a non-existent user) are left in the database.

Cascading Updates:
- if a record in the "one" side (e.g., User) changes (e.g., the user's ID changes), the "many" side (e.g., Order) typically needs to be updated as well.
- for instance, if a User changes their user_id, all the associated Order records need to reflect the updated user_id. This update can be handled by the ON UPDATE CASCADE rule.
- cascading updates ensure that references to the modified record remain valid across related tables.

Ownership in Cascading Operations:
- the table that owns the relationship (i.e., the table that holds the foreign key) typically controls the behavior of cascading operations like delete and update.
- for instance, in the example above, the Order table (the "many" side) will ensure that all related orders are deleted when a User is deleted. Similarly, if the User's user_id is updated, the Order records will also be updated.
