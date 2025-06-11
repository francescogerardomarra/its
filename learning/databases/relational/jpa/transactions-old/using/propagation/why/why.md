# Why does it matter?
Propagation matters because it defines how different parts of your application
handle transactions—whether they **start a new one**, **join an existing one**, or **stay out of it completely**.

**If you don’t get this right, you can run into issues like:**
- data getting saved when it shouldn’t.
- one small failure canceling everything, even unrelated parts.
- logs or audit entries disappearing because they were part of a rolled-back transaction.

Understanding propagation helps you avoid those surprises and makes sure each operation behaves the way you expect—especially when things go wrong.
