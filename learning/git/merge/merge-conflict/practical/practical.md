# Practical example of merge conflict

1. **Ensure you're on the target branch**

    - Before creating a new branch, make sure you are on the branch from which you want to branch off. For example, if you want to create a branch starting from the `master` branch, use the following command:

```shell
git checkout master
```

2. **Create a new branch**

    - Use the `git branch` command to create a new branch. Let's name it `feature-branch`:

```shell
git branch feature-branch
```

3. **Switch to the new branch**

   - Use the `git checkout` command to switch to the newly created branch:

```shell
git checkout feature-branch
```

4. **Make changes on the new branch**

   - Make some changes to the files in your project on the feature-branch. This can be done by editing existing files, adding new files, or both.

5. **Stage and commit your changes**
   
   - Use the following commands to stage and commit your changes:

```shell
git add .
git commit -m "Test changes on feature branch"
```

6. **Switch back to the master branch**
   
   - After making changes on the feature-branch, switch back to the master branch:

```shell
git checkout master
```

7. **Make conflicting changes on the master branch**
   
   - Make some changes to the same files on the master branch that conflict with the changes you made on the feature-branch.

8. **Attempt to merge the feature branch**

   - Now, try to merge the `feature-branch` into the `master` branch:
   - If there are conflicting changes, Git will pause the merging process and inform you of the conflicts.
   
```shell
git merge feature-branch
```

9. **Resolve merge conflicts**

   - Use a text editor to open the conflicted files, look for lines with conflict markers, and manually resolve the conflicts. After resolving conflicts, add the files and complete the merge:

```shell
git add .
git merge --continue
```

10. **Complete the merge**

    - If everything goes well, you can complete the merge:

```shell
git commit -m "Merge feature-branch into master"
```