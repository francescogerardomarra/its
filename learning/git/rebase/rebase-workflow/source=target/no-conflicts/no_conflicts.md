# Case 2 (no fast-forward, no-conflicts)

**Rebasing against the origin with no fast-forward and no conflicts** typically involves 
rewriting the commit history of your local branch to incorporate changes from the remote branch. 

This can provide a cleaner and more linear history.

**Here's a step-by-step example:**

Suppose you have a `local branch` named `feature-branch` and you want to rebase it against 
the `origin/master branch`.

1. **Fetch the latest changes from the remote repository**
```shell
git fetch origin
```
2. **Switch to your `feature-branch`**
```shell
git checkout feature-branch
```
3. **Rebase your `feature-branch` against the `origin/main branch`**
```shell
git rebase --no-ff origin/main
```
The `--no-ff flag` ensures that Git creates a new commit for the rebase, even if it could perform a fast-forward.

This helps maintain a clearer history.

If there are no conflicts, the rebase will proceed smoothly. 

However, if conflicts arise, Git will pause the rebase process and ask you to resolve the conflicts.

4. **If conflicts occur:**
   - Git will stop at the first commit with a conflict.
   - Use `git status` to see which files have conflicts.
   - Open the conflicting files, resolve the conflicts, and save the changes.
   - After resolving conflicts, continue the rebase with:`git rebase --continue`
5. **If you want to abort the rebase at any point use the command below:**
```shell
git rebase --abort
```
6. **After a successful rebase, push your changes to the remote repository**
```shell
git push origin feature-branch
```
Since you've rewritten the commit history, you'll need to force-push if the branch has already been pushed 
to the remote. 

Use with caution, especially in a collaborative environment.
