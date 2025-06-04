# `ADD`

- **purpose:**
  - copies files or directories from the source (host) to the destination (container);
  - can handle local files, directories, and remote URLs;
  - automatically extracts compressed files (like `.tar.gz`) if they are local (unlike [COPY](../add/add.md) command); 
  - for example `ADD app.tar.gz /app/` will automatically extract the content of `app.tar.gz` within `/app` folder;
  - unlike [COPY](../copy/copy.md), `ADD` supports remote URLs and automatic extraction of compressed files.
- **syntax:**

  ```dockerfile
  ADD <source> <destination>
  ```
- **example:**
  - copy a file from the host to the container:

    ```dockerfile
    ADD myfile.txt /app/
    ```
  - copy a directory (including its contents):

    ```dockerfile
    ADD mydir/ /app/
    ```
  - download a remote file and store it in the container:

    ```dockerfile
    ADD https://example.com/file.tar.gz /app/
    ```
