# Case 1 (fast-forward)

Let's go through an example where local and remote branches have diverged, and you want to rebase your local changes on top of the remote changes to integrate them seamlessly.

This scenario is common when you want to incorporate the latest changes from the remote repository into your local feature branch before pushing your changes. 

In this example, we'll assume that your local feature branch is not up-to-date with the remote branch.

1. **Clone a remote repository**
```shell
# Clone a remote repository (replace 'repository-url' with the actual URL)
git clone repository-url
cd repository-name
```
2. **Create diverged changes locally and remotely**
```shell
# Create and switch to a new feature branch locally
git checkout -b feature-branch

# Make some changes and commit locally
echo "Local feature branch changes - Part 1" >> hello.txt
git add hello.txt
git commit -m "Local feature branch: Commit 1"

# Push the local feature branch to the remote repository
git push origin feature-branch

# Switch to the master branch locally
git checkout master

# Make some changes and commit locally
echo "Local master branch changes" >> hello.txt
git add hello.txt
git commit -m "Local master branch: Commit 1"

# Push the local master branch to the remote repository
git push origin master
```
3. **Diverge changes on the remote branch**
```shell
# Switch back to the feature branch locally
git checkout feature-branch

# Make additional changes and commit locally
echo "Local feature branch changes - Part 2" >> hello.txt
git add hello.txt
git commit -m "Local feature branch: Commit 2"

# Push the additional local changes to the remote feature branch
git push origin feature-branch
```
4. **Fetch changes from the remote repository**
```shell
# Fetch the latest changes from the remote repository
git fetch origin
```
5. **Rebase the feature branch against origin**
```shell
# Rebase the local feature branch onto the latest changes from origin
git rebase origin/feature-branch
```
If there are conflicts during the rebase, Git will pause and ask you to resolve them manually. 

**After resolving conflicts, continue with the rebase with this command:**
```shell
# Continue with the rebase
git rebase --continue
```

After completing the rebase, your local feature branch will now incorporate both your local changes and the
changes made on the remote feature branch.

The commit history will be linear, providing a cleaner integration of the changes.
