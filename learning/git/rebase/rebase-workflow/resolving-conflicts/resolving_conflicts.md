# Resolving conflicts during a rebase operation

Resolving conflicts during a rebase is a common scenario in Git, especially when changes made in one branch conflict with changes made in another branch.

Here's a step-by-step demonstration of resolving conflicts during a rebase operation:

Let's consider a scenario where we have a `feature-branch` and want to rebase it onto the `master branch`. Assume that both branches have changes that affect the same lines in a file, resulting in a conflict.

1. **Start with a Clean Working Directory**

   - Ensure that your working directory is clean. Commit or stash any changes if necessary.

2. **Checkout the feature-branch**

   - Switch to the branch you want to rebase. In this case, it's the feature-branch:

```shell
git checkout feature-branch
```

3. **Fetch the Latest Changes from the Master Branch**

```shell
git fetch origin master
```

4. **Rebase the feature-branch**

   - Initiate the rebase, specifying the branch you want to rebase onto:

```shell
git rebase master
```

At this point, Git will attempt to apply the changes from `feature-branch` onto `master`. If conflicts arise, the rebase will be paused.

5. **Resolve Conflicts**
   
   - Git will notify you about conflicts. You can check which files have conflicts by using:

```shell
git status
```

Open the conflicted files in your code editor. Git will mark the conflicting sections in the file, and you need to manually resolve the conflicts.

Manually edit the file to resolve the conflict. Remove the conflict markers and keep the desired changes. Save the file.

6. Mark Conflicts as Resolved

   - After resolving conflicts, mark the files as resolved:

```shell
git add example.txt
```

7. Continue the Rebase

8. Complete the Rebase

   - After resolving all conflicts, Git will complete the rebase. If everything goes well, you might need to force-push the branch to update the remote repository:

```shell
git push origin feature-branch --force
```

`NOTE: Force-pushing can overwrite history, and it should be used with caution, especially in shared repositories.`

Resolving conflicts during a rebase requires careful attention to ensure that the changes are integrated correctly. Regular communication with your team is essential to coordinate and avoid disruptions when force-pushing changes to a shared branch.
