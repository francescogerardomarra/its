# What's Inside a Layer? 

- each layer contains a snapshot of filesystem changes made during that instruction;
- metadata like file permissions, timestamps;
- it does not contain a full OS or full file tree, just what changed since the last layer.
