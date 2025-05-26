# How to perform a merge

`Performing a merge in Git involves combining changes from one branch into another. `

1. **Fetch Changes**

Simply perform a `git pull` command to make sure both of the branches are up-to-date with the remote repository.

Use the `git checkout` command  to fetch any changes from both the incoming and current branch.

```shell
git pull

git checkout branch-name
```

2. **Navigate to the Target Branch**

Make sure you are on the branch where you want to merge changes.

Use the following command to switch to the target branch:

```shell
git checkout target-branch
```

3. **Initiate the Merge**
   
Use the `git merge` command to initiate the merge. 

For example, to merge changes from a source branch named "feature-branch" into the current branch:

```shell
git merge feature-branch
```

3. **Resolve Conflicts (If Any)**

4. **Mark the files as resolved (If found conflicts)**

5. **Complete the merge**

If there are no conflicts or after resolving conflicts, complete the merge:

```shell
git merge --continue
```

6. **Commit the Merge**

After resolving conflicts, Git may automatically create a merge commit. 

If not, use the following command to create a merge commit:

```shell
git commit -m "Merge changes from feature-branch"
```

7. **Push Changes (if necessary)**

If you want to share the merged changes with others, push the changes to the remote repository:

```shell
git push origin target-branch
```

This process outlines the basic steps for performing a merge in Git. Keep in mind that the exact steps may vary based on the branching strategy and the specific Git workflow used by your team.
