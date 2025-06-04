#

To create a **shared volume** with Docker Compose in **Swarm mode**, you need to use **external storage**, such as **NFS**, because Docker volumes are **local by default** and not shared across nodes in a swarm.

---

### ✅ Example: Shared Volume with NFS and Docker Compose (Swarm Mode)

Here's how to set it up:

---

### 📁 Step 1: Prepare the NFS Server (on any machine or cloud)

Assume:

* NFS server IP: `192.168.1.100`
* Exported path: `/mnt/shared_data`


---

### 🧾 Step 2: Docker Compose File (`docker-compose.yml`)

```yaml
version: "3.9"

services:
  web:
    image: nginx
    volumes:
      - shared-volume:/usr/share/nginx/html
    deploy:
      replicas: 3

volumes:
  shared-volume:
    driver: local
    driver_opts:
      type: "nfs"
      o: "addr=192.168.1.100,nolock,soft,rw"
      device: ":/mnt/shared_data"
```

> 🔐 Replace `192.168.1.100` and `:/mnt/shared_data` with your actual NFS server and path.

---

### 🚀 Step 3: Deploy to Docker Swarm

Make sure you're in **Swarm mode**:

```bash
docker swarm init  # if not already done
```

Then deploy:

```bash
docker stack deploy -c docker-compose.yml mystack
```

---

### 🧪 Step 4: Verify

1. Check that the service is running on multiple nodes:

   ```bash
   docker service ls
   docker service ps mystack_web
   ```

2. Confirm the volume is mounted:

   ```bash
   docker volume ls
   docker volume inspect mystack_shared-volume
   ```

3. Test writing to `/mnt/shared_data` and confirm all replicas see the same content.

---

Would you like an example using another storage backend (like GlusterFS or CIFS)?


