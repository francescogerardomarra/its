#

| Feature    | Environment Variables       | Docker Secrets                       |
|------------|-----------------------------|--------------------------------------|
| Storage    | In container’s environment  | Encrypted storage                    |
| Visibility | Exposed in `docker inspect` | Not visible in `docker inspect`      |
| Security   | Can be leaked via logs      | Mounted as files, access-controlled  |
| Best for   | Non-sensitive configs       | Passwords, API keys, encryption keys |



## **3. Comparing Security: Environment Variable vs. File Mounting**
| Feature                            | Environment Variable (❌ Insecure)  | Secret as a File (✅ Secure)    |
|------------------------------------|------------------------------------|--------------------------------|
| **Stored in `docker inspect`?**    | 🔴 Yes                             | ✅ No                           |
| **Accessible via `printenv`?**     | 🔴 Yes                             | ✅ No                           |
| **Exposed in logs/crash reports?** | 🔴 Yes                             | ✅ No                           |
| **Permission-controlled?**         | 🔴 No (available to all processes) | ✅ Yes (file access restricted) |
| **Recommended for secrets?**       | ❌ No                               | ✅ Yes                          |

---

## **4. When to Use Each Method?**
| Use Case                                        | Recommended Method                  |
|-------------------------------------------------|-------------------------------------|
| Configurations (e.g., ports, app mode)          | ✅ Environment Variables             |
| **Secrets (passwords, API keys, certificates)** | ✅ Docker Secrets (Mounted as Files) |
| Non-Swarm Setup (Standalone Docker)             | ✅ Manually Mount Secrets as Files   |
| Cloud/Enterprise Deployment                     | ✅ External Secrets Manager          |
