# Rules
**An entity class must follow these requirements:**
- the class must be annotated with the [jakarta.persistence.Entity](../definition/definition.md) annotation;
- the class must have a `public` or `protected`, no-argument constructor;
- the class may have other constructors;
- the class must not be declared `final`;
- no methods or persistent instance variables must be declared `final`;
- if an entity instance is passed by value as a detached object, the class must implement the
[Serializable](../../../../../../../../java/chapter-5/serialization/serializable/definition/definition.md) interface;
- entities may extend both entity and non-entity classes, and non-entity
classes may extend entity classes;
- persistent instance variables must be declared `private`, `protected`, or 
`default` and can be accessed directly only by the entity class’s methods;
- clients must access the entity’s state through accessor or business methods.
