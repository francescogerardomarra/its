# Definition
**In Java, orphan removal has the following characteristics:**
- it refers to the automatic deletion of entities that are no longer referenced in a parent-child relationship;
- it is often used in conjunction with cascading operations in JPA (Java Persistence API);
- it helps maintain data integrity by ensuring that no orphaned child entities exist in the database;
- it is enabled by using the `orphanRemoval = true` attribute in the `@OneToMany` or `@OneToOne` annotations;
- it allows for cleaner database management and reduces the risk of data anomalies.
