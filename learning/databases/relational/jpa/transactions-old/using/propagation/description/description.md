# Description
**Propagation essentially decides the internal behavior of a transaction**—whether it should create a new transaction,
join an existing one, or run independently.

**It dictates how transactions behave towards each-other** using `propagation` as a parameter and setting `REQUIRES_NEW` or others as the propagation type.

**[Here](../example/generic/generic.md) is an example using `REQUIRES_NEW`.**
