# Interactive rebase

Interactive rebase (`git rebase -i`) is a powerful Git feature that allows you to manipulate and edit commit history during the rebase process. It provides an interactive text editor interface where you can reorder, squash (combine), edit, and drop commits. This gives you fine-grained control over the commit history, helping you create a cleaner and more logical sequence of commits.

Here's an overview of the main actions you can perform during an interactive rebase:

1. **Reordering Commits**

   - You can change the order of commits by simply rearranging the lines in the interactive rebase editor. This is useful for organizing commits chronologically or grouping related changes together. For example, if you have two commits A and B, you can switch their order in the interactive rebase editor.

2. **Squashing Commits**

   - Squashing allows you to combine multiple commits into a single commit. This is helpful for condensing related changes into a more meaningful and atomic commit. To squash commits, change the word "pick" to "squash" (or simply "s") for the commits you want to combine. During the rebase, Git will prompt you to edit the commit message for the new squashed commit.

3. **Editing Commits**

   - You can edit the content of a commit during an interactive rebase. Change the word "pick" to "edit" for the commit you want to modify. After Git pauses at that commit, you can make changes (add, modify, or remove files), amend the commit, and continue the rebase. This is useful for fixing mistakes or refining commit content.

4. **Dropping Commits**

   - If you want to eliminate a commit from the history entirely, you can drop it. Change the word "pick" to "drop" for the commit you want to remove. Git will skip that commit during the rebase, effectively discarding it from the history.

To perform an interactive rebase, run the following command:

```shell
git rebase -i <base-commit>
```

Replace `<base-commit>` with the `commit ID` or `branch name` where you want the rebase to start.

The interactive rebase editor will open, displaying a list of commits and actions. You can then make the desired changes in the editor, save, and exit. Git will apply your specified actions, and you may need to resolve conflicts if they arise.

It's essential to use interactive rebase with caution, especially when rewriting history that has been shared with others. If you've already pushed the commits to a remote repository, force-pushing the changes after an interactive rebase can disrupt collaborators. Therefore, it's generally recommended to avoid interactive rebase on commits that have been shared publicly.
