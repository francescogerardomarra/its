# The differences between merge and rebase

Essentially the differences between a merge and a rebase are:

**Merge**: what a merge basically does, is take the changes from a `feature-branch` and a `target branch`(ex. `master-branch`), and merge them into a merge commit placed on the `target branch`.  

![](img/merge.gif)

**Rebase**: a rebase cuts the commits on your `feature-branch` and pastes them into a `new commit` in front of a `target branch` in the commit history(in front of the HEAD).

![](img/rebase.png)

| Feature                 | Merging                                   | Rebasing                                      |
|-------------------------|-------------------------------------------|-----------------------------------------------|
| **Commit History**      | Preserves branching structure with merges | Creates a linear history without extra merges |
| **Branch Structure**    | Preserves divergences and merge points    | Presents a sequential, non-divergent flow     |
| **Readability**         | May result in a more complex history      | Results in a cleaner, more readable history   |
| **Conflict Resolution** | Conflicts are resolved in merge commits   | Conflicts are resolved during the rebase      |
| **Use Cases**           | Integration of changes between branches   | Keeping feature branches up-to-date           |
