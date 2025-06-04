# `uid` and `gid`

- `uid` and `gid` stand for "User ID" and "Group ID" respectively;
- they are numerical identifiers assigned to a user and a group on a Linux system;
- when using the [o](../example/tmpfs/tmpfs.md) option within `driver_opt` in Docker Compose, they specify the ownership of mounted volumes or files created inside the container;


- `uid=1000` assigns ownership of the mounted files or directories to the user with the ID 1000 on the host system;
- `gid=1001` assigns ownership to the group with the ID 1001 on the host system;
- this configuration is especially useful when the containerized application needs to write files that should be accessible by a specific user or group on the host;


- if the container is running as a non-root user, that user must match the `uid` and `gid` set on the volume to have the intended access rights;
- to map these numeric IDs to usernames inside the container, you would need to create a user and group inside the container with the same IDs;
- the root user within the container has full access to all files and directories, regardless of their `uid` and `gid` ownership;


- the command has some limitations, since it can not be used for `bind` volumes.

**Example:**

See an example [here](../example/tmpfs/tmpfs.md).
