# Is It Possible to Have Multiple Nodes On One Machine?

**Question:**

- if I already have a manager node on my machine (via `docker swarm init`), can I run the `docker swarm join` command on the *same machine* to add a worker node? 
- what happens then?

**Answer:**

> - **no, you cannot join the same Docker daemon as both a manager and a worker**;
> - it's **not possible** to have a single Docker Engine be both at the same time;
> - it's **not possible** to have multiple Docker Engine on the same machine.

**Why?**

- in Docker Swarm, each **node = one Docker daemon**;
- when you run `docker swarm init`, the current Docker daemon becomes a **manager node**;
- if you then try to run `docker swarm join` on the *same daemon*, you'll get an error like:

    ```
    Error response from daemon: This node is already part of a swarm.
    ```


- that’s because a Docker Engine can only be in **one role at a time**: manager or worker and not both and only once per Swarm.
