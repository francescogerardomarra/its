# Use case
A common use case for `FetchType.LAZY` is when you have an entity with
associated data that is not always needed upfront.

For instance, in a user management system, you might retrieve a `User` entity without
immediately needing its `UserProfile` or `UserPermissions`.

Using `FetchType.LAZY` allows you to load the user entity initially and 
defer the loading of related data until it's explicitly accessed.

**This strategy is ideal when:**
- it doesn't need the associated entities immediately, avoiding unnecessary database queries;
- it deals with large collections or relationships that are seldom accessed;
- it wants to improve performance by fetching related data only when required.