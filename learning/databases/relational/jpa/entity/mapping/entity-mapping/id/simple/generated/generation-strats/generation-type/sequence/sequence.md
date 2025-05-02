# Sequence
**The sequence strategy has the following characteristics:**
- it uses database sequences to generate primary key values;
- sequences are database objects that generate unique numbers;
- this strategy is often used in databases that support sequences, such as Oracle.
```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_name")
@SequenceGenerator(name = "sequence_name", sequenceName = "sequence_name", allocationSize = 1)
private Long id;
```