# Why do they matter?
Even though transactions make methods atomic, multiple transactions can still run concurrently.

**Without proper isolation, one transaction might read, modify, or interfere with another unfinished 
transaction’s data, leading to issues like:**
- **Dirty Reads** → Reading uncommitted data from another transaction.
- **Non-Repeatable Reads** → Getting different results when querying the same data within a transaction.
- **Phantom Reads** → New records appearing in repeated queries within the same transaction.