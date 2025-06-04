# Quorum

- **quorum = (total managers / 2) + 1**;
- so, to **tolerate 1 failure**, you need **at least 3** managers;
- to **tolerate 2 failures**, you need **5** managers;


- the **quorum** assure that the system remains consistent and fault-tolerant, even if some nodes are unavailable, failed, or partitioned (these nodes will not respond to the quorum).

**Table:**


| Total Managers | Quorum Required | Max Failures Tolerated |
|----------------|-----------------|------------------------|
| 1              | 1               | 0                      |
| 3              | 2               | 1                      |
| 5              | 3               | 2                      |
| 7              | 4               | 3                      |
