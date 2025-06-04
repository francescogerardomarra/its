# Example

Run the following commands on a manager node:

1. check the node ids:

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

2. label the `node 1` as `type=manager`

   ```commandline
   docker node update --label-add type=manager wbkfxctwut6tuvtiru0s2r5uu
   ```

3. check if the label is applied to `node 1`:

   ```commandline
   docker node inspect wbkfxctwut6tuvtiru0s2r5uu --format '{{ .Spec.Labels }}'
   ```

   output:
   
   ```commandline
   map[type:manager]
   ```

4. deploy a service with constraint:

   ```commandline
   docker service create \
   --name nginx \
   --constraint 'node.labels.type==manager' \
   nginx
   ```

   - this ensures that the nginx service runs only on nodes that have a label `type=manager`.
