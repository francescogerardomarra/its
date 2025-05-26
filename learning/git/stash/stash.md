# How to stash your changes

**Stashing is a feature that allows you to save changes that you've made to your working directory but don't want to commit yet.** 

This can be useful in several situations, such as when you need to switch to a different branch, but you don't want to commit your changes or when you want to pull in changes from a remote repository without committing your local changes first. Stashing essentially allows you to temporarily save your changes and then apply them later when needed.

Here's how you can stash changes in Git:

## Stashing Changes

1. **Check the Status of Your Working Directory**:

    Before stashing your changes, it's a good practice to check the status of your working directory using the `git status` command. This will show you the files with changes that you're about to stash.

2. **Stash Your Changes**:

    To stash your changes, you can use the `git stash` command. You can optionally provide a message to describe the stash:

```shell
git stash save "Your stash message"
```
If you don't provide a message, Git will create a default message.

Remember that stashes are specific to your local repository, and they are not shared with remote repositories. Stashing is a handy way to temporarily set aside changes in your working directory when you need to perform other operations and then reapply those changes later.

## How to Un-stash your changes

1. **Apply the Most Recent Stash**:

   To un-stash the most recent stash, you can use the `git stash apply` command followed by the stash reference, which is typically `stash@{0}` for the most recent stash:

    ```shell
    git stash apply stash@{0}
    ```

   If you want to remove the stash after applying it, you can use `git stash pop` instead of `git stash apply`.

2. **Applying a Specific Stash**:

   If you have multiple stashes and want to apply a specific stash, you can reference it using its index in the stash list (e.g., `stash@{1}` for the second stash):

    ```shell
    git stash apply stash@{1}
    ```
3. **Deleting a Stash**:

   If you want to delete a stash without applying it, you can use the git stash drop command:

    ```shell
    git stash drop stash@{0}
    ```
   Replace `stash@{0}` with the appropriate stash reference.

4. **Clearing All Stashes**:

   To remove all stashes, you can use the git stash clear command:
    ```java
    git stash clear
    ```

## Advantages and disadvantages

In Git, "stashing" is a feature that allows you to temporarily save changes in your working directory without committing them. This can be useful in various scenarios.

- **Advantages**

  1. **Temporary Storage**

     - Stashing allows you to save your current changes without committing them. This is helpful when you need to switch to a different branch or deal with an urgent bug fix.

  2. **Switching Branches**

     - Stashing is particularly useful when you want to switch branches but have uncommitted changes. It allows you to clean your working directory before switching, preventing conflicts.

  3. **Conflict Avoidance**

     - Stashing helps avoid conflicts by providing a way to save your changes, switch branches, and then reapply your changes. This reduces the chances of conflicts during branch switching.

  4. **Temporary Work Interruptions**

     - If you're in the middle of some work but need to address a critical issue, stashing allows you to save your changes temporarily and come back to them later.

- **Disadvantages**

  1. **Loss of Context**

     - Stashing doesn't save commit history or context about your changes. If you have multiple stashes, it might be challenging to remember the purpose of each stash.

  2. **Potential for Stash Conflicts**

     - In rare cases, conflicts can arise when applying stashes, especially if changes in the stash conflict with changes in the branch where you are applying the stash.

  3. **Risk of Forgetting Stashes**

     - Stashes are not visible in commit history. There is a risk of forgetting about stashes, and if they are not applied or dropped, they can lead to confusion later on.

  4. **Overuse**

     - Depending too heavily on stashing might indicate that your branching and workflow strategy could be improved. Overusing stashing may make the history less straightforward.

  5. **Incomplete Stashing**

     - Stashing may not include untracked files by default. If you have new, untracked files in your working directory, you may need to use additional options (git stash -u or git stash -a) to include them in the stash.
