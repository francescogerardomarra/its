# How to Create

**Command:**

- the following command creates a new service in the swarm cluster;
- it must be executed on a manager node:

    ```commandline
    docker service create --name <service-name> <image>
    ```

**Example:**

Run these commands on a manager node (see next chapters for more options):

- this command creates a service running one instance of nginx:

    ```commandline
    docker service create --name my-webservice nginx
    ```

- this command creates a service running 3 instances of nginx:

    ```commandline
    docker service create --name my-webservice --replicas 3 nginx
    ```
