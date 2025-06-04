it's possible to see the exposed ports in the `Dockerfile` with `EXPOSED` command, with docker ps:

Yes, exactly! The `27017/tcp` port being displayed in the `docker ps` output likely corresponds to a port that was **exposed in the `Dockerfile`** of the `mongo` image using the `EXPOSE` instruction. Here's how it works:

---

### **The `EXPOSE` Command in a `Dockerfile`**
- The `EXPOSE` instruction in a `Dockerfile` specifies that the application running inside the container listens on the specified port(s).
- For example, the `mongo` Docker image probably has this in its `Dockerfile`:
  ```dockerfile
  EXPOSE 27017
  ```
  This informs Docker (and anyone inspecting the image) that the application expects to communicate over port `27017/tcp` inside the container.

---

### **What `EXPOSE` Does:**
1. **Documentation Purpose**:
    - `EXPOSE` acts as documentation for developers and Docker tools to indicate which ports are intended for communication.
    - It doesn't make the port accessible to the host or external world on its own.

2. **Internal Availability**:
    - The port (`27017/tcp` in this case) becomes available to other containers in the same Docker network.
    - Without mapping, it's still **isolated from the host**.

---

### **How to Make It Accessible from the Host**
To make an `EXPOSE`d port accessible from the host, you must explicitly map it using the `-p` or `--publish` flag when running the container.

For example:
```bash
docker run -p 27017:27017 mongo
```

- This maps the container's exposed `27017/tcp` port to port `27017` on the host, making it accessible externally via `localhost:27017`.

---

### **Conclusion**
- The `EXPOSE` instruction in the `Dockerfile` exposes the port **internally** within the container or Docker network.
- If you see a port like `27017/tcp` in the `docker ps` output without a host mapping, it likely corresponds to a port declared with `EXPOSE` in the image's `Dockerfile`.
- To allow host access, you must explicitly map the port during container runtime with `-p` or `--publish`.