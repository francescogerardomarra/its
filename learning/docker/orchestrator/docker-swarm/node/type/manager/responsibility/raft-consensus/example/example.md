# Example

Consider a scenario with these 3 manager nodes (plus other worker nodes):
- `node1`: leader;
- `node2`: follower;
- `node3`: follower.

**A step-by-step possible scenario:**

1. `node1` is the **leader**;
2. `node2` goes offline;
3. `node1` makes changes (adds services, etc.);
4. changes still go through because:
   - `node1` + `node3` = **2 out of 3 = quorum**;
5. `node1` commits and apply the changes to the cluster;
6. `node1` then crashes;
7. `node2` comes back online;
8. now what happens?:
   - `node2` and `node3` are both online;
   - a new **leader is elected** (likely `node3`);
   - `node2` will **sync up** with the leader to get the **latest state**.

**Why this works**:

- here’s always a **majority of nodes (2 out of 3)** to:
  - approve changes;
  - elect a new leader;
  - keep the cluster consistent.
- even if **1 node goes down at any point**, the remaining **2 still form a majority**, so the system keeps working safely.
