# Create a Secret Definition

- run this command to create a secret:

    ```sh
    echo "SuperSecurePassword12345" | docker secret create test-secret -
    ```

- the command `echo "SuperSecurePassword12345"` outputs the string `"SuperSecurePassword12345"` to the standard output;
- the pipe `|` redirects this output to the next command in the pipeline;
- `docker secret create test-secret -` creates a new Docker secret named `test-secret`, where `-` indicates that the secret's content is read from the standard input;


- as a result, Docker Swarm securely stores the secret `"SuperSecurePassword12345"`, making it available to services that explicitly request access to it;

- verify that the secret is created:

    ```sh
    docker secret ls
    ```

    **output**:
    
    ```commandline
    ID                          NAME          DRIVER    CREATED         UPDATED
    q7h92j8ql18thzylcp74rnp03   my_secret               3 weeks ago     3 weeks ago
    h9vg2iul7qig7gdtvtcd15b99   test-secret             6 seconds ago   6 seconds ago
    ```
