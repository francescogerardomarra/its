# Purpose

- Docker Swarm is a distributed system, which means that any node, including manager nodes, can fail at any time;
- to ensure the reliability and consistency of the system, Swarm uses the Raft consensus algorithm;
- in a Swarm cluster, the **leader** node maintains the current state of the cluster (e.g., services, replicas, available nodes, etc.);


- if the **leader** fails, this state must not be lost;
- therefore, Raft replicates the cluster state to multiple **follower** (manager) nodes;
- the purpose of the Raft algorithm is to ensure that the cluster state is consistently replicated and backed up across manager nodes;


- if the **leader** fails, one of the **followers** can be elected as the new **leader** and continue managing the cluster without data loss;
- without a consensus algorithm like Raft, if the **leader** node fails, the other nodes would not have a consistent or reliable view of the cluster state;
- this could lead to split-brain scenarios, inconsistent cluster behavior, or complete management failure, as no other node could safely take over leadership with confidence in the current state.
