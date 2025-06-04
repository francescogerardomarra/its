# Interstage Communication

- stages can be identified by:
  - their index (e.g., the first stage is `stage 0`);
  - a custom name assigned using `AS` (e.g., `FROM base AS builder`).
- transferring artifacts:  
  - files or directories can be transferred from one stage to another using:
  
    ```dockerfile
    COPY --from=<stage_name_or_index> <source_path> <destination_path>
    ```
    
    this allows you to share compiled binaries, configuration files, or other resources without including unnecessary dependencies in the final stage.
