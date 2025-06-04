# How It Works

1. a client sends a command to a manager node (e.g., `docker service update`);
2. if the manager node is not the current leader, it forwards the command to the leader node;
3. the leader proposes the command and replicates it to the follower nodes;
4. the follower nodes log the proposed update and send acknowledgments back to the leader;
5. once the number of acknowledgments reaches quorum (i.e., `(total number of manager nodes / 2) + 1`), the leader commits the change (the leader marks a log entry as durably agreed upon by a quorum) and applies it to the cluster; then it responds to the client;
6. if in the future the leader node fails (due to hardware or software issues), more than half of the manager nodes will still have the latest state of the Swarm cluster; one of them will be elected as the new leader;
7. if the leader fails **after sending the command to the followers** but **before quorum is reached**, the proposed command is not committed; although some followers may have logged the uncommitted command, it will be discarded during log reconciliation (i.e., when the new leader ensures that all manager nodes have a consistent and up-to-date log by removing any entries that were not committed); this prevents inconsistent or partial updates to the cluster.
