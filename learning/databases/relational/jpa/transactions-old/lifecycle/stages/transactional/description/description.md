# Description
`@Transactional` is an annotation that controls transactions on the database by managing when they start, commit or roll back.

It ensures that all operations within the annotated method follow the rules of a transaction.

Transactions should be used if there are more than one operation or transaction towards the database
inside a method, there are some exceptions like read only operations.

`@Transactional` can only be used on public methods.

**It works by:**
1. **starting a transaction** when the method begins;
2. **executing database operations** inside that transaction;
3. **committing the transaction** if everything completes successfully;
4. **rolling back the transaction** if an exception occurs, preventing partial updates.



