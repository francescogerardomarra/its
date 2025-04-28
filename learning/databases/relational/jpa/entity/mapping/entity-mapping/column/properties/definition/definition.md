# Definition
**A persistent property has the following characteristics:**
- **JavaBeans Convention**:
  - the entity follows the [JavaBeans conventions](../../../../../../../../../java/chapter-2/object-oriented/components/bean/convention/convention.md)
  with getter and setter methods for properties.
- **Naming**:
  - for each instance variable, there are corresponding getter and setter methods;
  - for example, for a variable `firstName`, there are methods `getFirstName` and `setFirstName`.
- **Boolean Properties**:
  - for Boolean properties, `isProperty` can be used instead of `getProperty`.
- **Method Signatures**:
  - **Getter**: `Type getProperty()`
  - **Setter**: `void setProperty(Type type)`
- **Annotations**:
  - object/relational mapping annotations are applied to the getter methods, 
  not to fields or properties marked as `@Transient` or `transient`.
