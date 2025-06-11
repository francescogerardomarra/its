# Description
**Nested transactions** allow for a more granular control over transaction boundaries in Spring.  
A **nested transaction** enables you to start a new transaction within the context of an existing (**outer**)
transaction.  
The key benefit is that if the **inner** (**nested**) transaction fails, the changes it made can be
rolled back, while the **outer transaction** continues to process.  
If the **outer transaction** fails, both the **outer** and **nested transactions** will be rolled back together.

Spring does not support true nested transactions in the way that some databases do (where the inner transaction can be fully independent of the outer one). Instead, Spring uses **savepoints** to simulate this behavior, provided the underlying database supports savepoints.

The `PROPAGATION_NESTED` setting is used to manage nested transactions in Spring.  
It creates a **savepoint** within the **outer transaction**, which can be rolled back independently.  
If the **inner transaction** is successful, the changes are committed to the **outer transaction** when it commits.  
However, if the **inner transaction** fails, the **outer transaction** remains unaffected,
but the **inner transaction** is rolled back to the **savepoint**.



### In Spring:

The transaction calling a nested transaction is the **outer transaction**.

The method with `@Transactional(propagation = Propagation.NESTED)` is the **inner transaction**.

#### Here's the breakdown:

- the **outer transaction** is the transaction that is active when calling a method.
- the method with `@Transactional(propagation = Propagation.NESTED)` starts a **nested transaction**, which is conceptually a child of the outer one, but independently controlled in terms of rollbacks (through savepoints) if it fails.

#### To summarize:
- the **outer transaction** starts and manages the broader scope of work (like saving multiple records or completing a larger process).
- the **nested transaction** (using `PROPAGATION_NESTED`) allows a portion of that work to be rolled back independently if something goes wrong, but if it succeeds, it is eventually committed along with the outer transaction when it commits.

### Example Clarification:

**If you have:**
```java
@Transactional
public void outerMethod() {
    innerMethod(); // This is the inner method being called in the outer method.
}

@Transactional(propagation = Propagation.NESTED)
public void innerMethod() {
    // This is the nested transaction
}
```
### In this case:

- `outerMethod()` is the **outer transaction** (because it's the method that initiates the transaction and calls `innerMethod`).
- `innerMethod()` is the **nested transaction** (because it has `PROPAGATION_NESTED`, meaning it tries to run inside the outer transaction but is able to roll back independently if it fails).

So in simple terms, the **nested transaction** is always the one inside the method annotated with `PROPAGATION_NESTED`, and the **outer transaction** is the one calling that method.
