# `mappedBy`
**The `mappedBy` attribute has the following characteristics:**
- it is used to specify the field or property that owns the relationship 
in a bi-directional association;
- it is used on the inverse side of the relationship to
indicate that the relationship is managed by the 
other entity (the owning side).

**Explanation**
- **Purpose**:
  - the `mappedBy` attribute helps define the
  inverse side of a bi-directional relationship;
  - it tells JPA that the specified field or property 
  on the other entity owns the relationship.
- **Placement**:
  - the `mappedBy` attribute is placed on the entity
  that does not contain the foreign key, effectively 
  indicating that the relationship is controlled by the entity on the owning side.
- **Value**:
  - the value of the `mappedBy` attribute is the name of the field 
  or property in the owning entity that manages the relationship.
