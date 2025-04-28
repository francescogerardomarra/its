# Proxy behaviour
The primary role of the proxy is to intercept calls to the methods marked as `@Transactional`.

Every time one of these methods is called from an external client (i.e., from outside the bean itself),
the proxy steps in to manage transaction behavior.

**Here is an in depth explanation of how the proxy behaves and reacts:**

### ✅ Before Method Execution

**Get Current Transaction:**
- Checks if the current thread is already in a transaction (`TransactionManager.getCurrentTransaction()`).

**Read Transactional Metadata:**
- Extracts the configuration from the `@Transactional` annotation, like:
    - Propagation type
    - Isolation level
    - `readOnly` flag
    - Rollback rules

**Decide to Start or Join a Transaction:**
- `Propagation.REQUIRED` + no current transaction → start a new one
- `Propagation.REQUIRES_NEW` → suspend existing transaction and start a new one
- `Propagation.SUPPORTS` → proceed without a transaction if none exists

---

### 🧠 During Method Execution

**Delegate to Actual Bean Method:**
- Calls the business logic method (`targetBean.actualMethod()`), now within the transactional context.

**Transactional Context Propagation:**
- Any DB operations or bean calls during this time will use the active transaction — *only if they go through the proxy*.

---

### 🧹 After Method Execution

**Transaction Finalization:**

- ✅ If the method completes successfully → **commit**
- ❌ If an exception is thrown:
    - Rollback if it matches rollback rules (e.g., runtime exceptions by default)
    - Otherwise → commit

**Cleanup:**
- Clear transactional state (e.g., thread-local variables)
- Release or return resources (e.g., database connections) to their pools


### **Peculiar cases:**
**Self-invocation issue:**
- proxies can't intercept internal method calls;
- if a method inside the bean calls another method in the same class, the proxy doesn't get triggered.