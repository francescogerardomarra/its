# Description
### **Defining Custom Queries with Native SQL**
While JPQL operates on entity objects, **native queries** allow us to write raw SQL that interacts directly with database tables.

We can define **native SQL queries** using the `@Query` annotation with `nativeQuery = true`
in our repository interface. Since **native queries use SQL instead of JPQL**,
we must ensure that table and schema names are correctly specified, as SQL queries
do not rely on entity mappings.

#### **Example: fetching items between a minimum price and a maximum price using a Native Query**
Suppose we need to retrieve a list of items whose price is between a minimum and maximum price using raw SQL.

**We can define the query as follows:**
```java
@Query(value = "SELECT * FROM shop_schema.Item WHERE price BETWEEN :minPrice AND :maxPrice", nativeQuery = true)
List<Item> findItemsByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
```