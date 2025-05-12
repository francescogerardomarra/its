# Definition
**In Java, `FetchType.EAGER` has the following characteristics:**
- it forces the associated entities to be loaded immediately when the parent entity is retrieved;
- it is useful when all related data is needed upfront;
- it can lead to performance issues if unnecessary data is fetched, especially in large relationships.
