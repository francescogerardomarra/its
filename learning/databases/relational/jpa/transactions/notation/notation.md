# Notation
In database theory, transactions are often represented using a specialized notation that allows us to express the sequence of operations performed within a transaction. One common way to represent these operations is using the **operation-predicate-element** notation.

This notation helps in analyzing and understanding the **read (R)** and **write (W)** operations on data elements (usually records or tuples) within transactions. The notation is composed of:

- **Operation**: The type of operation performed (read or write).
- **Predicate**: The transaction identifier (e.g., `T1`, `T2`) to indicate which transaction is performing the operation.
- **Element**: The data element or attribute being accessed or modified (e.g., `A`, `B`, etc.).


The general format for representing an operation is:

Op(Ti, X)


Where:
- **Op** is the operation (either `R` for read or `W` for write),
- **Ti** is the transaction identifier (e.g., `T1`, `T2`, etc.),
- **X** is the data element being operated on (e.g., `A`, `B`, etc.).

Examples:
- `R1(A)` → Transaction `T1` reads element `A`.
- `W2(B)` → Transaction `T2` writes to element `B`.


Consider two transactions, **T1** and **T2**, operating on two data elements `A` and `B`.

1. **T1** reads element `A`.
2. **T1** writes to element `A`.
3. **T2** reads element `B`.
4. **T2** writes to element `B`.

The sequence of operations for these transactions can be represented as:

R1(A) -- T1 reads A
W1(A) -- T1 writes to A
R2(B) -- T2 reads B
W2(B) -- T2 writes to B

Explanation of the Sequence

- `R1(A)` → **T1** reads element `A`. This operation captures the current value of `A` for use in **T1**.
- `W1(A)` → **T1** writes a new value to element `A`. This action updates `A` in the database.
- `R2(B)` → **T2** reads element `B`.
- `W2(B)` → **T2** writes to element `B`, modifying its value in the database.


1. **Read (R) Operation**: Indicates a transaction is accessing data to **read** its current value. It doesn't modify the value of the element.
2. **Write (W) Operation**: Indicates a transaction is modifying the value of a data element.
3. **Transaction Identifier (Ti)**: Identifies the specific transaction performing the operation. This is crucial for tracking which transaction is interacting with which data element.
4. **Data Element (X)**: Represents the data item being read or written in the operation.
