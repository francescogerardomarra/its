# Comparison
It’s true that if your method only ever does one save(...)
(or one repository call), you can rely on Spring Data’s built‑in transaction on that single
call and technically drop your own @Transactional.

But as soon as you split your work into multiple repository calls—even on the same entity—you’ll 
end up with multiple, back‑to‑back transactions, and you’ll see exactly the “half‑committed”
problem we’re trying to avoid.

Even if you’re hitting the same repository, every call to its CRUD methods
(e.g. save(), delete(), etc.) opens and closes its own transaction unless you’ve already bound them together.

**Without `@Transactional`:**
```
public void updateFoo(...) {
    repo.save(entity);    // Transaction #1
    entity.setFoo(...);
    repo.save(entity);    // Transaction #2
}
```
If the second save blows up, the first one has already committed.

**With `@Transactional`:**
```
@Transactional
public void updateFoo(...) {
    repo.save(entity);   // joins the surrounding transaction context
    entity.setFoo(...);
    repo.save(entity);   // also in the same transaction context
}
```
Now both calls participate in a single transaction. If anything fails, none of the changes are committed.

## Summary
whenever you do more than one repository call in a single business operation, 
wrap it with `@Transactional` to guarantee atomicity—even if it’s the same repository.