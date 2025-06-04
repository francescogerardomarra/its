# Why are Next Layers Invalidated?

- each Dockerfile step creates a new layer that represents a change to the image’s filesystem;
- later layers depend on the exact state of the layers below them;
- even if a later step hasn’t changed, it can’t be reused if the layers it builds on have changed;


- because it would now be applied on top of a different base.

**Example:**

```dockerfile
# Step 1
COPY mycode/ /app/        # Copies Java source code into /app

# Step 2
COPY a.txt /data/a.txt    # Adds a text file into /data

# Step 3
RUN javac /app/Main.java  # Compiles the Java code
```

Let’s say you update some Java files in `mycode/`:

- Docker sees that `COPY mycode/ /app/` has changed: step 1 is invalidated;
- because step 2 depends on the filesystem state after step 1, Docker must also invalidate step 2, even though `a.txt` hasn't changed;
- step 3 relies on `/app/Main.java` existing in a specific form from step 1.
