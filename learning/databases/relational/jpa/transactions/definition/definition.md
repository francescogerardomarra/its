# Definition
**A transaction has the following characteristics:**
- it is a sequence of operations performed as a single logical
unit of work, typically encapsulating one or more database
operations (like reading, writing, or updating records).

## Mark a service method as transactional
When we say **mark a service method as transactional**, we’re referring to the act of associating
the method with a [transactional context](../lifecycle/boundaries/transactional-context/transactional_context.md) — meaning that the operations performed within
the method will be treated as part of a single, atomic unit of work.
- if the method’s operations succeed, the transaction is committed (i.e., changes are persisted).
- if an error occurs, the transaction is rolled back (i.e., all changes are discarded).


<https://www.geeksforgeeks.org/transaction-in-dbms/>
<https://www.geeksforgeeks.org/sql-transactions/>
<https://www.geeksforgeeks.org/transaction-isolation-levels-dbms/>
<https://www.geeksforgeeks.org/dbms-dirty-read-in-sql/>
<https://www.geeksforgeeks.org/types-of-schedules-in-dbms/>
<https://www.geeksforgeeks.org/concurrency-problems-in-dbms-transactions/>
<https://memgraph.com/blog/acid-transactions-meaning-of-isolation-levels>
<https://www.geeksforgeeks.org/conflict-serializability-in-dbms/>