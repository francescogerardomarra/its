# Simple primary key
When designing an explicit junction table (often needed when you want to add extra columns like a quantity field), 
you have two common options for the table’s primary key:

1. **Compound (Composite) Primary Key:**
   - this key is formed by combining the two foreign keys (e.g., order_id and item_id);
   - it directly reflects the many-to-many relationship by ensuring that each combination of order and item is unique.

2. **Simple Primary Key:**
   - instead of relying on the combination of foreign keys, you introduce a separate auto-generated primary key
   (such as an integer or UUID) to uniquely identify each row;
   - the foreign keys remain as individual columns, but they no longer form the primary key.
