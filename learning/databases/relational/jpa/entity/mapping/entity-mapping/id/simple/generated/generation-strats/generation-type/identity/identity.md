# Identity
**The identity strategy has the following characteristics:**
- it relies on an auto-incremented database 
column to generate unique primary key values;
- it's commonly used for databases that support auto-incremented columns.
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```