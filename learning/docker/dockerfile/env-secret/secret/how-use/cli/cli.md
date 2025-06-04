# CLI

- it's possible to retrieve a secret from AWS using the terminal and store it within an environment variable;
- open a terminal and run this command:

    ```commandline
    export DB_PASS=$(aws secretsmanager get-secret-value --secret-id my-db-password)
    ```

**Above command explanation:**

- `export DB_PASS=...;` sets an environment variable named `DB_PASS` with a value retrieved from AWS Secrets Manager;
- `$(...)` is command substitution, meaning the output of the command inside the parentheses is assigned to `DB_PASS`;
- `aws secretsmanager get-secret-value --secret-id my-db-password` retrieves the secret value associated with the ID `my-db-password` from AWS Secrets Manager;


- if the command executes successfully, `DB_PASS` will contain the retrieved secret value, which is typically a database password or other sensitive information;
- **how the command login to AWS:**
  - if you have set up credentials using aws configure, the CLI will use the default profile automatically;
  - if you have multiple profiles, you can specify within the command which one to use with `--profile <profile-name>`, or set `AWS_PROFILE=<profile-name>` as an environment variable.
