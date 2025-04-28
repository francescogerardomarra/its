# Definition
**A persistent field has the following characteristics:**
- **Direct access**:
  - the Persistence runtime directly accesses the instance variables of the entity class.
- **Default persistence**:
  - all fields are persisted to the database unless they are annotated with `jakarta.persistence.Transient` 
  or marked as `transient` in Java.
- **Annotations**:
  - object/relational mapping annotations are applied directly to the instance variables.