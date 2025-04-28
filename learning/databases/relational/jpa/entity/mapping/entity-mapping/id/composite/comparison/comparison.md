# Comparison
**The fundamental difference between using `@IdClass` and `@EmbeddedId` in JPA:**
- is how you define and reference composite keys.

**Here is an in-depth comparison between the two:**
<div style="display: flex; justify-content: space-between; margin: 0 -5px;">
  <div style="width: 50%; margin: 0 5px;">

`@IdClass`:
- **Separate Class for Keys**:
  - you define a separate class (key class) that contains the composite key fields.
- **Fields in Entity**:
  - in the entity class, you directly mark the fields that make up the composite key with `@Id`.
- **Simple Field Access**:
  - you access the key fields directly through the entity class.
    </div>
    <div style="width: 50%; margin: 0 5px;">
`@EmbeddedId`:
- **Single Embeddable Key Class**:
  - you create a single embeddable class that contains the composite key fields and is marked with `@Embeddable`.
- **Embedded in Entity**:
  - in the entity class, you use an instance of this embeddable class, marked with `@EmbeddedId`.
- **Object-based Key Access**:
  - you access the key fields through the embedded key object.
    </div>
</div>