Rolling updates in **Docker Swarm** allow you to update services **gradually**, minimizing downtime and ensuring application availability during deployment. Here's a clear breakdown of how it works:

---

### 🚀 What Is a Rolling Update?

A **rolling update** updates your service **one task (container)** at a time (or in small batches), instead of stopping all instances and starting new ones simultaneously. This helps maintain uptime and service continuity.

---

### 🧩 Key Components

When performing a rolling update in Docker Swarm, these parameters control the process:

* `--update-parallelism`: Number of containers to update at once.
* `--update-delay`: Delay between updating groups of containers.
* `--update-failure-action`: What to do if an update fails (`pause`, `continue`, or `rollback`).
* `--update-monitor`: Duration to monitor a task for failure.
* `--rollback`: Automatically revert to the previous version if something goes wrong.

---

### 🛠️ Example

Suppose you have a service with 5 replicas, and you want to update the image.

```bash
docker service update \
  --image myapp:v2 \
  --update-parallelism 2 \
  --update-delay 10s \
  my_service
```

This will:

1. Update 2 containers to `myapp:v2`.
2. Wait 10 seconds.
3. Update 2 more.
4. Finally, update the last one.

If something fails and `--update-failure-action` is set to `rollback`, Docker will revert all changes.

---

### ✅ Benefits

* **High availability**: Keeps part of your app running while updating.
* **Controlled risk**: Problems surface early in the update.
* **Automation-friendly**: Swarm handles updates automatically based on defined rules.

---

### 🔁 Rollback Example

If the new version causes issues:

```bash
docker service update --rollback my_service
```

Swarm will revert to the last known working version.

---

Let me know if you’d like a visual diagram or a more complex example with `docker-compose.yml`.
