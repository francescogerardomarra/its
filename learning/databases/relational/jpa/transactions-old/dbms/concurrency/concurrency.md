# Concurrency control
In multi-user environments, transactions prevent data conflicts by isolating 
changes until they’re committed. 

**This avoids issues like:**
- lost updates
- dirty reads
- non-repeatable reads