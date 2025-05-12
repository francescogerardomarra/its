# Purpose
**In Java, the purpose of cascade operations in JPA:**
- is to manage the lifecycle of related entities by automatically
propagating persistence operations from a parent entity to its associated entities;
- in other words it propagates a persistence operation across all related tables;
- for example, while using cascade operations if you update a parent table/entity
it would automatically update all the other tables that are related to that parent entity.