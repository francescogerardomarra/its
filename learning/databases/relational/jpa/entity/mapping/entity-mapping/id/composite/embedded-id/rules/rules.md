# Rules
**Rules for `@Embeddable` Classes in JPA**
1. **Annotation**:
   - the class must be annotated with `@Embeddable`.
2. **Serializable**:
   - the class should implement `Serializable`;
   - this is required because JPA might need to serialize the instance
   to persist its state;
   - serialization ensures that the object can be converted
   to a byte stream and stored or transferred as needed.
3. **Default Constructor**:
   - the class should have a public or protected no-argument constructor;
   - JPA uses this constructor to instantiate the object when retrieving data from the database.
4. **Field or Property Access**:
   - the class should use either field-based or property-based access consistently;
   - this means using annotations on fields or getter methods, but not mixing both approaches.
5. **`equals()` and `hashCode()`**:
   - you should override the equals and hashCode methods
   to ensure correct behavior when instances of the embeddable
   class are compared or used in collections;
   - this is important for identity and equality checks.
