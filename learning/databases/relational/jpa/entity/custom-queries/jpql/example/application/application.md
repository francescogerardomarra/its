# Without dto
[Here](resources/src/webservicerestrefactored.zip), you can find the same
[layered Spring application](../../../../../dto/example/application/application.md)
with a **PostgreSQL database**, but this version demonstrates how to use **JPQL** to 
implement custom queries instead of relying solely on `JpaRepository`'s built-in methods.

**In this example:**
- we define a **JPQL query** using Spring Data JPAs method signature comprehension to 
fetch orders based on a specific status;
- this allows us to write queries in an 
**object-oriented way**, operating on entity objects rather than raw database
tables;
- the **service layer** calls these custom queries, 
and the **controller layer** provides an endpoint to retrieve the filtered data.
