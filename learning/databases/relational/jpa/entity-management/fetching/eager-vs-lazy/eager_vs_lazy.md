# Eager vs lazy
In JPA (Java Persistence API), fetching strategies define when related entities are loaded from the database.

**There are two main strategies**:

1. **Eager loading**
    - **Definition**: the related entity or collection is loaded immediately along with the parent entity, even if you don't access it right away.
    - **Behavior**: as soon as you fetch the parent entity, all eagerly loaded associations are also fetched.
    - **Annotation**:
   ```
    @OneToMany(fetch = FetchType.EAGER)
   ```
   
2. **Lazy loading**
   - **Definition**: the related entity or collection is fetched only when it is accessed for the first time.
   - **Behavior**: fetching the parent entity doesn't load related entities immediately; it loads them on-demand when you actually call the getter.
   - **Annotation**:
   ```
    @ManyToOne(fetch = FetchType.LAZY)
   ```
   
