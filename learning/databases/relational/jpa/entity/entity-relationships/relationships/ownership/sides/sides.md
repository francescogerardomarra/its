# Owning and inverse sides
**The concepts of owning and inverse sides has the following characteristics:**
- it refers to the roles entities play in a relationship;
- these roles help manage and maintain bi-directional relationships between entities.

**The owning side has the following characteristics:**
- **it is the entity that contains the foreign key**;
- it is responsible for managing the association 
between the two entities;
- this entity directly maps the relationship to the database.

**The inverse side has the following characteristics:**
- it does not contain the foreign key;
- it maps the relationship using the `mappedBy` attribute,
which refers to the field or property in the owning
entity that manages the relationship.
