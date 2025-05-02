# Table
**The table strategy has the following characteristics:**
- it uses a separate database table to generate primary key values;
- each row in the table corresponds to an entity type and maintains the next available primary key value;
- this strategy is less efficient than sequence or identity strategies and is rarely used in practice.
```java
@Id
@GeneratedValue(strategy = GenerationType.TABLE, generator = "table_name")
@TableGenerator(name = "table_name", table = "pk_table", pkColumnName = "sequence_name", valueColumnName = "next_val", allocationSize = 1)
private Long id;
```