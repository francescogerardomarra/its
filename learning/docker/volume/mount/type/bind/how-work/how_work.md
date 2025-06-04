# How It Works (With Example)

When you use a bind mount, you specify:
- **host path**: the exact path on your machine (e.g., `/home/user/data`);
- **container path**: where you want that directory to appear inside the container (e.g., `/app/data`).

**Example:**

- command:

    ```commandline
    docker run -v /home/user/data:/app/data my-image
    ```

- `/home/user/data` is the host directory;
- `/app/data` is the container directory;
- any changes in `/home/user/data` immediately appear inside the container at `/app/data` (and vice versa).
