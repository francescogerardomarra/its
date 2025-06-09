# Manual Rollbacks Definition

- the following command is used in Docker Swarm to roll back a service (`my_service`) to its previous version:

    ```commandline
    docker service update --rollback my_service
    ```

- if you've previously updated `my_service` using `docker service update` and something went wrong, for example, containers are failing or not behaving as expected, this command will revert the service to the **last known good configuration**;
- that means it will roll back to the last update that **completed successfully**.

**Example:**

In this scenario:

1. update A: **successful**;
2. update B: **failed**;
3. update C: **successful**, but the code has a bug;
4. `docker service update --rollback my_service`: **rolls back to update A**.
