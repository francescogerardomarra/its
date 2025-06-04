# Container OS Different From Host OS Definition

- in this chapter we reply to the question: **how is that possible that a container runs a different OS from the host OS, since it doesn't use a VM but the host processes?**
- this is possible because containers don't actually run a full operating system;
- instead, they use the host OS kernel but can have a different **user space** environment, which makes it seem like they're running a different OS.
