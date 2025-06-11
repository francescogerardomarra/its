# Why do they matter?

## Without Transactions
If you don't use transactions, each database operation (such as inserts, updates, or deletes) is executed independently. 
This can lead to several issues:

1. ### Partial Updates (Data Inconsistency)
   - if an operation consists of multiple database changes (e.g., creating an `Order`, adding `Items`, and associating them),
   and one-step fails, the previous successful operations won't be rolled back.

   - **Example:**
     - insert `Order` → Success ✅
     - insert `OrderItem` → Success ✅
     - update `Item` stock → Fails ❌
     - **result**: the order exists, but the item stock remains unchanged, leading to incorrect inventory.

2. ### Concurrency Issues (Race Conditions, Dirty Reads, etc.)
   - without transactions, multiple users modifying the same data may face race conditions.

   - **example:** two users attempt to buy the last item simultaneously, and both succeed because the stock check and update aren't atomic.

3. ### Data Integrity Violations
   - if an operation requires multiple related database modifications, failure to wrap them in a transaction may result in an inconsistent state.

   - **Example:** creating an `Order` but failing to associate it with a `User`, leaving orphaned records.

---

## With Transactions
When you use transactions (via `@Transactional` in Spring), all operations within the transaction scope are executed as a single atomic unit. If any step fails, all previous changes within that transaction are rolled back.

1. ### Atomicity (All or Nothing)
   - if one operation fails, the entire transaction is rolled back, ensuring data consistency.

   - **example:**
     - insert `Order` → Success ✅
     - insert `OrderItem` → Success ✅
     - update `Item` stock → Fails ❌
     - **result**: the entire transaction is rolled back, so the `Order` and `OrderItem` insertions are undone.

2. ### Isolation (Prevents Race Conditions & Dirty Reads)
   - transactions ensure that changes made by one transaction are not visible to others until committed.

   - **example:** if two users try to buy the last item, the first transaction locks the item stock update until it completes.

3. ### Consistency (Referential Integrity Maintained)
   - transactions ensure that related entities remain in sync.

   - **example:** if an `Order` is created but payment processing fails, the entire transaction is rolled back, preventing an unpaid order from being recorded.

4. ### Durability (Changes Persist Once Committed)
   - once a transaction is successfully committed, changes are guaranteed to be stored permanently.
