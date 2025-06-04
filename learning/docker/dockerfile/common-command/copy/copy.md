# COPY

- **purpose:**
  - copies files or directories from the host machine to the container image;
  - supports only local files and directories (cannot fetch remote URLs like [ADD](../add/add.md) command);
  - does not automatically extract compressed files (unlike [ADD](../add/add.md) command);
  - maintains file permissions, if a file has specific permissions (e.g., executable), they remain unchanged inside the container;
  - the file ownership changes, and it's assigned to the user who is executing the **COPY** or **ADD** command inside the `Dockerfile`.

- **syntax:**

    ```dockerfile
    COPY <source> <destination>
    ```

- **example:**
  - copy a single file into the container:
  
    ```dockerfile
    COPY myfile.txt /app/
    ```
  - copy a directory and its contents:
  
    ```dockerfile
    COPY mydir/ /app/
    ```
  - copy multiple files into a target directory:
  
    ```dockerfile
    COPY file1.txt file2.txt /app/
    ```
