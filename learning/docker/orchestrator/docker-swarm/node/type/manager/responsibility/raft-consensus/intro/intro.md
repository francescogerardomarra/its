# Raft Consensus Introduction

- manager nodes use the Raft consensus algorithm to maintain consistency;
- only one manager is the **leader** at any time, others are followers;
- changes (like deploying a service) are only made if a majority agrees (quorum).
