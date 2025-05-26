# Reword

Let's go through an example where we use the interactive rebase option to reword a commit message. This can be useful when you want to clarify or improve the wording of a specific commit message. In this example, we'll create a feature branch, make multiple commits, and then use interactive rebase to reword one of the commit messages.


1. **Create a new repository, if you don't have one ready for this exercise**

```shell
# Create a new directory for the repository
mkdir rebase-example

# Change into the repository directory
cd rebase-example

# Initialize a new Git repository
git init
```

2. **Create a simple file on your target branch**

```shell
# Create a new file and add some content
echo "Hello, World!" > hello.txt

# Add and commit the file
git add hello.txt
git commit -m "Initial commit"
```

3. **Create a feature branch and make commits**

```shell
# Create and switch to a new feature branch
git checkout -b feature-branch

# Make some changes and commit multiple times
echo "Feature branch content - Part 1" >> hello.txt
git add hello.txt
git commit -m "Feature branch: Commit 1"

echo "Feature branch content - Part 2" >> hello.txt
git add hello.txt
git commit -m "Feature branch: Commit 2"

echo "Feature branch content - Part 3" >> hello.txt
git add hello.txt
git commit -m "Feature branch: Commit 3"
```

4. **Reword a commit message using interactive rebase**

```shell
# Start an interactive rebase to reword a commit message
git rebase -i HEAD~3
```

An editor will open with a list of commits. Change the word "pick" to "reword" (or simply "r") for the commit whose message you want to reword. Save and close the editor.

5. **Edit the commit message**

Git will open another editor for you to edit the commit message. Change the commit message to the desired wording. Save and close the editor.
