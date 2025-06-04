# Connect the Maven Project with Gitlab

1. open a terminal within the Maven project root folder and digit `git init`;
2. connect it with your GitLab repo in SSH:

    ```commandline
    git remote add origin <YOUR_SSH_REPOSITORY_URL>
    ```
   **you can find your remote SSH repository URL in the `SSH` section on the main page of the "Docker-course-test" repository on GitLab;**

3. in our case:

   ```commandline
   git remote add origin git@gitlab.com:avolpe3/docker-course-test.git
   ```
