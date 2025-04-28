# What do they affect?
Isolation levels affect how strictly a transaction is protected from others and control when a transaction can see changes made by others.

**There are four standard isolation levels, ranging from least strict (faster, more concurrency) to most strict (safer, but slower):**
1. **Read Uncommitted (Least Isolation, Most Risk)**
   - a transaction can see uncommitted changes from other transactions;
   - **risk:** dirty reads, non-repeatable reads, phantom reads;
   - **use case:** rarely recommended—only for scenarios where performance is critical, and data inconsistencies are acceptable;
2. **Read Committed (Default in Many Databases)**
   - a transaction can only see committed data from other transactions;
   - **risk:** non-repeatable reads, phantom reads;
   - **use case:** most common level, balances consistency and performance;
3. **Repeatable Read (Stronger Isolation)**
   - a transaction sees the same data throughout its execution, even if other transactions commit changes;
   - **risk:** phantom reads (new rows may appear in repeated queries);
   - **use case:** when consistency matters more than performance, like banking transactions;
4. **Serializable (Most Strict, Least Concurrent)**
   - transactions are fully isolated; they execute one after another, avoiding all concurrency issues;
   - **risk:** performance impact due to locking;
   - **use case:** when accuracy is more important than speed, such as in financial or inventory systems.
