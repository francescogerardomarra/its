# Rules
**Rules for `@IdClass` Classes**
1. **Annotation**:
   - the primary key class must be specified in the entity using the `@IdClass` annotation.
2. **Serializable**:
   - the primary key class must implement Serializable.
3. **No-argument constructor**:
   - the primary key class must have a public or protected no-argument constructor.
4. **Matching fields**:
   - the fields in the primary key class must match the fields 
   annotated with `@Id` in the entity class in terms of names and types.
5. **`equals()` and `hashCode()`**:
   - the primary key class must override the equals and hashCode 
   methods to ensure correct behavior when instances are compared or used in collections.
6. **`public` or `protected` Fields/Methods**:
   - the primary key class should have `public` or `protected` fields, or `public`
   getter methods for the fields.