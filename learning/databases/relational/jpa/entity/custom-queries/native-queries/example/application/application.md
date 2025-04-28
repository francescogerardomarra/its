# Without dto
[Here](resources/src/webservicerestrefactored.zip), you can find the same
[layered Spring application](../../../../../dto/example/application/application.md) with a **PostgreSQL database**,
but this version builds upon the **JPQL-based implementation** by using **native SQL queries** instead.

Unlike JPQL, which operates on entity objects, **native queries interact directly with 
database tables** and require explicit schema and table names.

**In this example:**
- we use the `@Query` annotation with `nativeQuery = true` to execute raw SQL queries for fetching items based on a minimum and maximum price range;
- the **service layer** calls these native queries, and the **controller layer** exposes an endpoint to return the filtered results;
- since **native queries do not leverage entity mappings**, we must ensure the correct schema and table names are specified, or set a default schema in `application.properties` to avoid schema-related errors.

