# Use case
A common use case for `FetchType.EAGER` is when you have an entity
with related data that is always required as part of the business logic.

For example, consider a system that retrieves an order along with all 
its associated order items.

In this case, using `FetchType.EAGER` ensures that both the order
and its items are loaded immediately, as the items are essential to process the order.

**This strategy is ideal when:**
- it always needs related entities available without additional queries;
- it deals with small, essential relationships where performance impact is minimal;
- it wants to simplify code by avoiding the need to explicitly handle lazy loading.