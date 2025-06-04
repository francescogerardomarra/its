# Promote a Worker to Manager

1. list nodes (on a manager node):

    ```commandline
    docker node ls
    ```

    output:
    
    ```commandline
    ID                            HOSTNAME        STATUS    AVAILABILITY   MANAGER STATUS   ENGINE VERSION
    wbkfxctwut6tuvtiru0s2r5uu *   avolpe-pc       Ready     Active         Leader           28.1.1
    x8shk3jwe0dtwkwz5i9s91pgc     manager-2       Ready     Active         Reachable        28.1.1
    n2b7t9k3uw3h9wql1s6j8z0yr     worker-1        Ready     Active                          28.1.1
    u9a8j3h5ndq5zy6c84q4k7kzv     worker-2        Ready     Active                          28.1.1
    ```

2. identify the node ID or name of the worker you want to promote (`n2b7t9k3uw3h9wql1s6j8z0yr` in our case);
3. promote the worker node to manager:

    ```commandline
    docker node promote n2b7t9k3uw3h9wql1s6j8z0yr
    ```

4. verify the promotion:

    ```commandline
    ID                            HOSTNAME        STATUS    AVAILABILITY   MANAGER STATUS   ENGINE VERSION
    wbkfxctwut6tuvtiru0s2r5uu *   avolpe-pc       Ready     Active         Leader           28.1.1
    x8shk3jwe0dtwkwz5i9s91pgc     manager-2       Ready     Active         Reachable        28.1.1
    n2b7t9k3uw3h9wql1s6j8z0yr     worker-1        Ready     Active         Reachable        28.1.1
    u9a8j3h5ndq5zy6c84q4k7kzv     worker-2        Ready     Active                          28.1.1
    ```
