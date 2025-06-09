# Format Rules

- **line by line entries**:
    - each line in the `.dockerignore` file specifies a pattern;
    - patterns can match files or directories.
- **comments**:
    - lines starting with `#` are treated as comments and ignored;
    - example:
  
      ```dockerignore
      # This is a comment
      ```

- **blank lines**:
    - blank lines are ignored;
    - example:
      ```dockerignore
      # Ignore temp files
      *.tmp
 
      # Ignore logs
      *.log
      ```

- **glob patterns**:
    - you can use glob patterns to match files or directories;
    - examples:
        - `*.log` matches all `.log` files;
        - `**/temp/*` matches all `temp` directories at any depth and their contents.

- **directory matching**:
    - to exclude a directory and its contents, use a trailing slash (`/`);
    - example:
  
      ```dockerignore
      /build/
      ```

- **negation**:
    - use `!` to include files or directories that would otherwise be excluded;
    - example:
  
      ```dockerignore
      *.log
      !important.log
      ```
      
      this excludes all `.log` files except `important.log`.

- **relative to build context**:
    - patterns are evaluated relative to the root of the build context;
    - example:
  
      ```dockerignore
      src/*.tmp
      ```
      
      - matches `.tmp` files inside the `src` directory in the build context;
      - `.tmp` files within a subdirectory of `src` are **not** matched.

- **escaping**:
    - use a backslash (`\`) to escape special characters (`*`, `!`, `#`, `\`).
    - example:
  
      ```dockerignore
      \#not_a_comment
      ```
      
      this matches the file named `#not_a_comment`.
