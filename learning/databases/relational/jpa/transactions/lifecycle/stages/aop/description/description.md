# Description
Spring AOP plays a huge role in transaction management—in fact, when 
you annotate a method with `@Transactional`, you're using Spring AOP under the hood.

## **What’s the Role of Spring AOP in Transactions?**

**When you use `@Transactional`, Spring uses proxy-based AOP to:**
- **wrap your method call inside a transactional context**;
- **start a transaction** before the method executes;
- **commit or roll back** the transaction based on whether the method completes normally or throws an exception.