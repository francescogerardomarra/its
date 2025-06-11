# Timeline
A **timeline** is a graphical representation of a schedule. It provides a way to visualize how transaction operations are ordered relative to each other and how they are executed in parallel or sequentially.

We have already seen a schedule represented in a **horizontal** timeline, where operations flow from left to right, and time is represented horizontally across the timeline. In this horizontal timeline, each operation from different transactions is depicted in the order they occur, allowing us to clearly visualize the sequence of operations.

Here is an example of a **timeline** for two transactions (T1 and T2) represented in a horizontal fashion:

Time --> | 0 | 1 | 2 | 3 | 4 | 5 |
|---------|---------|---------|---------|---------|---------|
Op(T1) | R1*(A) | | W1*(A) | | | |
Op(T2) | | R2*(B) | | W2*(B) | | |


In this example:
- **T1** starts by reading `A` at time `0` (`R1*(A)`).
- **T2** then starts reading `B` at time `1` (`R2*(B)`).
- **T1** writes to `A` at time `2` (`W1*(A)`), while **T2** has already completed its read operation on `B`.
- **T2** writes to `B` at time `3` (`W2*(B)`).

This horizontal timeline effectively shows how the operations from both transactions interleave over time.

In a **vertical timeline**, time flows from top to bottom, with each row representing a single operation. Each transaction is represented by a column, and the operations from each transaction are placed in the corresponding column. The timeline shows the interleaving of operations across transactions over time.

Here is the same schedule represented in a **vertical fashion**:

| Time --> |    T1    |    T2    |
|----------|----------|----------|
|    0     |  R1*(A)  |          |
|    1     |          |  R2*(B)  |
|    2     |  W1*(A)  |          |
|    3     |          |  W2*(B)  |


- **T1** starts at time `0` with `R1*(A)` (reading from `A`), and at time `2`, it writes to `A` with `W1*(A)`.
- **T2** starts at time `1` with `R2*(B)` (reading from `B`), and at time `3`, it writes to `B` with `W2*(B)`.

In this vertical timeline:
- **T1**'s operations (`R1*(A)` and `W1*(A)`) are placed in the left column, one per row.
- **T2**'s operations (`R2*(B)` and `W2*(B)`) are placed in the right column, one per row.
- Time progresses downwards, with one operation happening per row, showing the interleaving of **T1** and **T2**'s operations over time.

This representation helps to clearly visualize how the operations of two transactions are interleaved in time.