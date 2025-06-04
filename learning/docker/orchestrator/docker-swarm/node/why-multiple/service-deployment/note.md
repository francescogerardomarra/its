# Rolling Updates and Zero Downtime

Sure! Let's break down the concepts of **Rolling Updates** and **Zero Downtime** in the context of **Docker Swarm**:

---

### 🔁 Rolling Updates


---

### 🟢 Zero Downtime



---

### 🚀 Example

---

Would you like a diagram or visual walkthrough to help illustrate this?



## question: so a container that is managing a client request can be suddenly shutted down?

answer:

Yes — **exactly**.

In Docker Swarm, during a rolling update, a container that is currently handling a **client request** can be **suddenly shut down** if you don’t take specific steps to prevent disruption.

---

### 🧨 What Actually Happens

When Swarm replaces an old container:

1. It **sends a `SIGTERM` signal** to tell the app to shut down.
2. If the app **doesn’t handle that gracefully**, the container is forcefully stopped after a short period (`10s` by default).
3. This can **kill active connections or requests**, leading to:

    * Dropped HTTP requests
    * Lost messages
    * Partially completed operations

---

### 🛡️ How to Prevent Sudden Shutdowns (Graceful Handling)

1. **Handle SIGTERM in your app**:

    * Wait for active connections to finish.
    * Clean up resources (e.g., close DB connections).
    * Exit cleanly.

   Example in Node.js:

   ```js
   process.on('SIGTERM', () => {
     console.log('Shutting down...');
     server.close(() => {
       console.log('Connections closed');
       process.exit(0);
     });
   });
   ```

2. **Use `--stop-grace-period`**:

    * Give your app more time to finish up before it's killed.

   ```bash
   docker service update \
     --stop-grace-period 30s \
     my_service
   ```

3. **Use health checks**:

    * Ensure new containers are **ready** before traffic is routed to them.

4. **Design for resilience**:

    * Use retries, queues, or failover strategies so that even if one request fails, the system recovers.

---

### 🧠 Bottom Line

> **Yes, without proper handling, Swarm can kill a container mid-request.**
> To avoid user-facing issues, you need to **prepare your app to shut down cleanly** and give Swarm enough time to do it.

Would you like help adding graceful shutdown to your specific tech stack (e.g., Python, Java, Node)?
