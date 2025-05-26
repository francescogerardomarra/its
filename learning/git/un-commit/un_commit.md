# Un-committing

In Git, you can "un-commit" changes using the `git reset` command. The `git reset` command allows you to move the `HEAD` (the pointer to the latest commit) to a different commit, effectively "un-committing" the changes. There are several ways to use `git reset` based on what you want to achieve:
####
1. **Un-commit and Keep Changes Locally (Soft Reset)**

   - To un-commit changes but keep the changes in your working directory, you can use the "soft" reset. This moves the `HEAD` to a previous commit, but the changes are staged for the next commit.

    ```shell
    git reset --soft HEAD~1
    ```

   - Replace `1` with the number of commits you want to go back. This command moves the `HEAD` one commit back.
####
2. **Un-commit and Un-stage Changes (Mixed Reset)**
   
   - If you want to un-commit and un-stage the changes (i.e., keep the changes in your working directory but not staged for the next commit), you can use the "mixed" reset.

    ```shell
    git reset --mixed HEAD~1
    ```

    - This command moves the `HEAD` back one commit and un-stages the changes. You can then modify the files and stage them as needed.
####
3. **Completely Remove the Commit and Changes (Hard Reset)**

   - If you want to completely remove the last commit and all changes, you can use the "hard" reset. Be very careful with this option because it discards all changes made in the last commit.

    ```shell
    git reset --hard HEAD~1
    ```

   - This will move the `HEAD` back one commit and discard all changes. You cannot recover these changes after performing a hard reset.

After you've used one of these reset options, you can make new commits as needed. Remember to use `git push` to update your remote repository if you've already pushed the changes you want to un-commit.

Please be cautious when using `git reset --hard`, as it can result in permanent data loss. It's recommended to create a backup or use the `--dry-run` option to preview the changes that will be made before running a hard reset.
