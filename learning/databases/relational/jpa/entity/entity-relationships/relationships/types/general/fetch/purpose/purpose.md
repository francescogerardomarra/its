# Purpose
The purpose of fetch types in Java is to control when related entities
are loaded from the database, optimizing performance and resource management.

**By specifying a fetch type, developers can**:
- it avoids unnecessary loading of data when using `FetchType.LAZY`, improving memory and performance efficiency;
- it ensures immediate availability of associated entities when using
`FetchType.EAGER`, useful when all related data is required upfront.