# Running
In this example the only change have pertained the addition of a single custom native query.

Below a testing guide for said query can be seen:

### Retrieve Items by Price Range
Retrieve all items with prices between 10.00 and 199.99.

**Successful Call:**

```bash
curl "http://localhost:8080/shop/items/price-range?minPrice=10.00&maxPrice=199.99"
```

### Invalid Price Range Input (Non-Numeric)
If someone inputs a non-numeric value (e.g., a letter) for one of the price parameters,
the exception handler will catch the type mismatch.

**Example Call:**

```bash
curl "http://localhost:8080/shop/items/price-range?minPrice=abc&maxPrice=199.99"
```
**Expected Response: a 400 Bad Request with an error message similar to:**
```
{   
    "path":"getItemsByPriceRange",
    "error":"Invalid input for parameter: minPrice",
    "message":"Value 'abc' is not a valid BigDecimal",
    "timestamp":"2025-03-18T18:30:13.079949106",
    "status":400
}
```

### Invalid Price Range Logic (minPrice > maxPrice)
If the provided price range is logically incorrect (e.g., minPrice is greater than maxPrice), the controller throws an IllegalArgumentException.

**Example Call:**

```bash
curl "http://localhost:8080/shop/items/price-range?minPrice=4999.99&maxPrice=199.99"
```

**Expected Response: A 400 Bad Request with the message:**
```
{
    "error":"Internal Server Error",
    "message":"minPrice must be less than or equal to maxPrice.",
    "timestamp":"2025-03-18T18:33:19.892618725",
    "status":500
}
```
