# High Availability

- with multiple manager nodes, Swarm maintains cluster state even if one manager fails;
- it uses the [Raft consensus algorithm](), so 3+ managers ensure reliable coordination; <!--todo: add link to quorum section -->

**Example:**

In a 3-manager setup, 1 can fail and the cluster still functions correctly.
