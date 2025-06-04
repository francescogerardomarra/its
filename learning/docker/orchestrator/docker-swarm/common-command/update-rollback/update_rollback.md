Great question — here's why **rolling updates** make rollback easier:

Because Docker Swarm updates containers in **small batches**, not all tasks are updated at once. If something goes wrong (e.g., the new image causes errors or fails health checks), Swarm can **pause or stop the update mid-process**, and you still have **some running containers with the previous version**. This means:

1. **You can detect issues early**, before all services are affected.
2. You can **rollback** using a single command:

   ```bash
   docker service update --rollback <service_name>
   ```

   Swarm will redeploy the previous version using the same controlled, rolling mechanism.

So the update is safer and more reversible because Swarm keeps track of the previous state and doesn't replace everything all at once.

Would you like a short summary sentence you can add to your section to explain this clearly?


## question 1: 

when this command can be used:

docker service update --rollback <service_name>?

immediately after a new deploy or whenever I want after the new deploy?

(I mean I do an update, I don't touch the service for 3 months, can I do rollback after 3 months?)

## answer 1:

You're right to look for a more concrete rule — let me clarify it as precisely as possible based on Docker Swarm’s actual behavior:

---

### 🔁 `docker service update --rollback` — When it Works

* **It only rolls back to the *last deployed version*** of the service.
* **You can only roll back once** — it doesn't support multiple rollback steps or a rollback history.
* The rollback **remains possible indefinitely**, *as long as no other update has occurred* and the Swarm manager has retained the necessary task history.

---

### 🧠 So here's the real rule:

> **You can roll back at any time — even months later — *as long as you haven't done another update in the meantime*** and the Swarm manager still has the previous state.

---

### ❗But:

* If you update the service again (new image, env vars, etc.), rollback will now point to *that* version — **not the one from 3 months ago**.
* Swarm stores limited task history; a manager node failure or service pruning may silently remove the rollback metadata.

---

### ✅ Best Practice:

> **Don’t rely on rollback as a long-term strategy.** Instead, tag and store known-good images (e.g., `myapp:v1.2.3`) so you can explicitly redeploy them when needed.

---

Would you like this summarized as a callout box or warning note in your doc section?
