# What Happens If You Don't Have a Quorum?

When you execute a management command like `docker service update` on a manager node, and you don't have a quorum (half or more nodes are unresponsive):
   - Swarm will still run **existing services**;
   - but you **can’t make any changes** (no deploys, updates, scaling, etc.);
   - you’ll see errors like:

     ```
     Error response from daemon: rpc error: code = Unknown desc = raft: not enough members to form a quorum
     ```
