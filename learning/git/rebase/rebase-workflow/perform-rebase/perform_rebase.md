# How to perform a rebase

1. **Checkout the branch you want to rebase**

   - For example, if you're working on a `feature-branch` and you want to rebase it onto the latest changes in the `master` branch, you would first `checkout` your feature branch.

```shell
git checkout feature-branch
```

2. **Fetch the latest changes**

   - Ensure you have the latest changes from the remote repository.

```shell
git fetch origin
```

3. **Rebase your branch onto the desired branch**

   - For example, if you want to rebase your `feature-branch` onto `master`, you would run:
   - This command effectively takes each commit in your `feature-branch` and reapplies it on top of the `master` branch.

```shell
git rebase master
```

4. **Resolve conflicts (if any)**

   - During the rebase process, Git might encounter conflicts that need to be resolved manually. Git will pause at each conflict, allowing you to edit the files and resolve the conflicts. After resolving conflicts, you continue the rebase with:

```shell
git rebase --continue
```

5. **Complete the rebase**

   - Once the rebase is complete, your branch is now up to date with the latest changes from the branch you rebased onto.

6. **Push the rebased branch**

   - If the branch has been pushed to a remote repository, you may need to force-push the branch to update the remote repository with the rebased history.

```shell
git push origin feature-branch --force
```

It's important to note that force-pushing should be used with caution, especially if the branch is shared with others. If you force-push a branch that others are also working on, you may overwrite their changes.

Rebasing is a useful tool, but it's important to use it judiciously, especially in collaborative environments. It's generally considered good practice to rebase feature branches before merging them into the main branch to maintain a clean and linear history.
