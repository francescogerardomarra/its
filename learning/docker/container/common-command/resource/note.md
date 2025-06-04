Setting resource usage in a Docker container helps limit how much CPU, memory, and other system resources a container can use. This is useful for ensuring that no single container can starve others or overwhelm the host system.

### 1. **Setting Memory Limits**

Use the `--memory` (or `-m`) option to limit memory usage:

```bash
docker run -m 512m nginx
```

* This limits the container to 512 megabytes of memory.
* Add `--memory-swap` to control swap usage (optional).

Example:

```bash
docker run -m 512m --memory-swap 1g nginx
```

* Max memory (RAM + swap) is 1 GB.

---

### 2. **Setting CPU Limits**

#### a. **Limit CPU Cores**

```bash
docker run --cpus=1.5 nginx
```

* Limits the container to 1.5 CPU cores.

#### b. **Pin to Specific Cores (CPU affinity)**

```bash
docker run --cpuset-cpus="0,2" nginx
```

* Container will only use cores 0 and 2.

#### c. **Set CPU Shares (Relative Weight)**

```bash
docker run --cpu-shares=512 nginx
```

* Default is 1024. Lower means lower priority.

---

### 3. **Block IO Limits**

Control disk I/O using `--device-read-bps`, `--device-write-bps`, etc.:

```bash
docker run --device-read-bps /dev/sda:1mb nginx
```

---

### 4. **Combined Example**

```bash
docker run \
  -m 1g \
  --memory-swap 2g \
  --cpus=2 \
  --cpuset-cpus="0,1" \
  --cpu-shares=512 \
  nginx
```

This runs the container with:

* 1 GB RAM
* 2 GB max including swap
* Max 2 CPUs, only cores 0 and 1
* Low CPU scheduling priority

---

Let me know if you want to apply these in a Docker Compose file too.
