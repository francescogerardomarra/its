# Storage and Security

- secrets are **stored encrypted** at rest on manager nodes using the Raft consensus algorithm;
- they are **never written to disk in plaintext**;
- only manager nodes have access to the full list of secrets (but even they do not expose them unless required).
