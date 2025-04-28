# Definition
**In Java, `FetchType.LAZY` has the following characteristics:**
- it delays the loading of associated entities until they are accessed for the first time;
- it improves performance by avoiding unnecessary data retrieval when the related entities are not immediately needed;
- it may cause a `LazyInitializationException` if the entities are accessed outside of the persistence context.
