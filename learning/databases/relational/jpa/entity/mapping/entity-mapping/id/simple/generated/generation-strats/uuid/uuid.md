# UUID
**The UUID strategy has the following characteristics:**
- it generates primary key values as universally unique identifiers (UUIDs);
- UUIDs are unique across different systems and are generated based
on timestamp and unique machine identifier.
```java
@Id
@GeneratedValue(generator = "UUID")
@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
private UUID id;
```