# Verify

1. check that the service is running on multiple nodes:

   ```bash
   docker service ls
   docker service ps mystack_web
   ```

2. confirm the volume is mounted:

   ```bash
   docker volume ls
   docker volume inspect mystack_shared-volume
   ```

3. test writing to `/mnt/shared_data` and confirm all replicas see the same content.
