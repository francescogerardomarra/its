# High Availability

- with multiple manager nodes, Swarm maintains cluster state even if one manager fails;
- it uses the [Raft consensus algorithm](../../type/manager/responsibility/raft-consensus/intro/intro.md), so 3+ managers ensure reliable coordination;

**Example:**

In a 3-manager setup, 1 can fail and the cluster still functions correctly.
