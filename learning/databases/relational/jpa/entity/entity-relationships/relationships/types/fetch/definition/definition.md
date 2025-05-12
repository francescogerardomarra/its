# Definition
**In Java, a fetch types has the following characteristics:**
- it defines the loading strategy for related entities in JPA.

**The main fetch types are:**
- it uses `FetchType.EAGER` to retrieve associated entities immediately when the parent entity is loaded;
- it uses `FetchType.LAZY` to defer loading of associated entities until they are accessed for the first time.