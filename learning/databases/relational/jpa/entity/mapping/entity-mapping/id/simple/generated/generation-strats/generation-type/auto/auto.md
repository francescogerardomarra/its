# Auto
**The auto strategy has the following characteristics:**
- it delegates the primary key generation to the underlying database;
- the choice of strategy (identity, sequence, or table) is determined by the persistence
  provider based on the database capabilities and configuration.
```java
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
```