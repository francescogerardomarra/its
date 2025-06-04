# Who Keeps the Secret?

- the secret is **stored securely** in the **Raft logs** of the **Swarm manager nodes**;
- the Raft logs are **encrypted at rest** using **AES-256** encryption;
- the secret **is not stored on worker nodes** unless a service running on a worker node explicitly requires it.

**Who manages and has access to the secret?**

- **Docker Swarm managers** handle the encryption, storage, and distribution of secrets;
- **only services that are explicitly granted access** to the secret (via `docker service create` or in `docker-compose.yml`) can access it;
- **worker nodes** only receive the secret when a container running on them needs it;
 
 
- the secret is **kept in-memory** and is **not written to disk**;
- once the service using the secret stops, the secret is **automatically removed** from the worker node.
