# Error recovery
If a system fails (e.g., power outage), transactions ensure that the DB can recover to a consistent
state using logs and backups.

**Uncommitted changes are rolled back.**
