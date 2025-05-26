# What is a simple example of the three-way merge algorithm used by Git?

**The three-way merge algorithm, also known as the recursive merge strategy, is a common method used by Git to combine changes from two branches.** 

`NOTE: it considers the common ancestor of the branches, the current branch (HEAD), and the branch being merged.` 

## Here's a simple example to illustrate the three-way merge algorithm

Let's say you have a file `example.txt` in your Git repository with the following content in the common ancestor (the original version):

```text
This is an example file.
```

Now, you create a new branch (`branch1`) and make a change:

```text
This is an updated example file.
```

Meanwhile, someone else creates another branch (`branch2`) from the same original version and makes a different change:

```text
This is an edited example file.
```

Now, you want to merge `branch1` into `branch2`. The three-way merge algorithm works as follows:

1. **Identify Common Ancestor**

   - The common ancestor is the original version of `example.txt`.

2. **Compare Changes**

   - Git compares the changes made in branch1 (updated) and branch2 (edited) with respect to the common ancestor.

3. **Automated Merge**

   - Git automatically merges changes that don't conflict with a **fast-forward merge**.

4. **Conflict Resolution**

   - Since both branches have modified the same line, a conflict has arisen, and Git will mark that line for manual resolution by the user with some conflict markers.
   - The conflicted `example.txt` might look like this:
```text
<<<<<<< HEAD
This is an updated example file.
=======
This is an edited example file.
>>>>>>> branch1
```
5. **Merge Commit**

   - Git creates a new commit that includes the changes from both branches.

## Visual representation

![](img/what-is-a-merge.gif) 