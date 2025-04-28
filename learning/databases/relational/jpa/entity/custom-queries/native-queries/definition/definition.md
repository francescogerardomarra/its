# Definition
While JPQL provides a powerful way to write queries using entity objects, 
there are cases where we need to use raw SQL queries for performance optimizations
or to leverage database-specific functions.

In such scenarios, we can define native queries using the `@Query` annotation with
the `nativeQuery` attribute set to `true`.
