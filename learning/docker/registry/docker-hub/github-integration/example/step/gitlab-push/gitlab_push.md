# Push the Code to GitLab

1. ensure that all changes within the Maven project have been committed:

   ```commandline
   git add .
   ```
   
   ```commandline
   git commit -m "first commit"
   ```

2. push the `master` branch to GitLab:

    ```commandline
    git push --set-upstream origin master
    ```
3. from now on, simply type `git push` while on the `master` branch to push changes to the `master` branch.
