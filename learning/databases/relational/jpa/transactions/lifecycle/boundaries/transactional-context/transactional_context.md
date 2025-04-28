# Adding a transactional context to a method
To add a transactional context to a method means selecting and assigning
the appropriate ACID properties to it.

This implies that when you design or execute a method with transactional
properties, you are ensuring that it behaves according to the ACID principles,
providing consistency and reliability in the face of failures or concurrency challenges.

In practical terms, for example, if a method involves multiple steps
(such as updating multiple databases or services), you would make sure that
if one part of the process fails, the entire operation is rolled back, preserving
the system's integrity. Additionally, if multiple users are accessing the same
resources simultaneously, the method should behave as if it’s running in isolation
from the others to avoid inconsistencies.


