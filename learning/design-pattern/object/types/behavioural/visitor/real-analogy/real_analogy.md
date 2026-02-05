# Real Life Analogy

Imagine you work at an **insurance company** that has **different types of clients**:

* resident customers;
* bank customers;
* corporate customers;
* etc.

Originally, each client type is just a simple data object describing the client, nothing more.

**The problem before using visitor:**

Your manager suddenly asks for **a new behavior**:

```text
Send each client a marketing email based on their client type.
```

Your first instinct is:

```text
Easy: I’ll add a `sendMail()` method inside every client class and override it.
```

But this leads to real-world problems:

* those client classes were supposed to be **simple**; now they must contain real business logic like connecting to mail servers;
* if marketing wants a new feature (e.g., **send SMS**, **compute discounts**, **generate invoices**), you must **open and modify every client class again**;
* you violate single responsibility, clients become bloated;
* you violate open/closed, adding new operations forces code changes everywhere.

**Using specialist teams instead of overloading clients:**

Instead of forcing every client type to know how to handle marketing messages, imagine the company hires a **specialized department** for each behavior:

* a **marketing team** that knows how to talk to residents, banks, and corporations differently;
* a **discount team** that calculates discounts depending on the client type;
* a **retention team** that creates customized offers.

Clients do **not** perform the work themselves.
Instead, when a specialist team comes to them, each client tells the team:

```text
Here’s what I am (resident/bank/corporate).

Run the version of your behavior meant for my type.
```

This is **double dispatch** in real-world terms:

1. the team (visitor) arrives;
2. the client says `I accept your visit, use the method designed for my type.`

Now you can add **new teams** (new behaviors) anytime: marketing team, discount team, risk evaluation team, *without touching the client classes at all*.

**How this matches the visitor pattern:**

* client = [Element](../description/description.md) (resident, bank, etc.);
* specialist team = [Visitor](../description/description.md) ([InsuranceMessagingVisitor](../code/code.md) within the code chapter);
* “accept visitor” = [ConcreteElement](../description/description.md) telling the visitor what to do;
* adding new behaviors = adding new visitors;
* client classes stay untouched.
