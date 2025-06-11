# Solution
To avoid problems like non-repeatable reads or race conditions, you need:

### 1. **Stronger Isolation Levels**
Use `REPEATABLE READ` or `SERIALIZABLE` to prevent stale reads or racey writes.

> These tell the DB: “give me a consistent view, even if the real world changes underneath.”

---

### 2. **Locks (explicit or implicit)**
Lock the rows so other transactions can’t modify them while you're using them.

> **SQL Example:**  
> `SELECT ... FOR UPDATE`

---

### 3. **Optimistic Concurrency Control**
Instead of locking, check if the data has changed before committing.

> Common approach: use a `version` column or timestamp to detect changes.

---

## 💡 TL;DR

- ✅ **Transactions are thread-safe internally**
- ❌ **They don’t automatically guard against concurrent transactions**
- 🧱 **Isolation level + concurrency strategy = real protection**
