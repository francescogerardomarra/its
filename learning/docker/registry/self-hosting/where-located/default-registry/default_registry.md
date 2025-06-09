# By Default, Do I Have a Registry on my Machine?

> No, by default, when you pull or build images with Docker, they are stored locally on your machine in Docker's internal storage (at [/var/lib/docker](../../../../image/where-stored/where_stored.md) on Linux).

**What is a local registry:**

- a local Docker registry lets you push and pull images within your own environment, keeping everything fast, private, and offline;
- it’s useful for **testing**, internal **CI/CD pipelines**, or working in **environments without internet access**;
- unlike local image storage (which stays on one machine), a local registry acts like a central image server;
 

- you can **pull images from it on any machine in your network**, or even **copy the registry’s storage (e.g., a volume or directory) to another system** to move images around.
