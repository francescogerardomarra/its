# Real Life Analogy

Imagine you own a **smart home** with a central **remote control** that can manage all kinds of devices: lights, curtains, doors, fans, etc.

When you press the “turn on lights” button, the remote doesn’t directly know **how** the lights work or **where** they are installed. Instead, it sends a **command object** that knows exactly what to do, it contains all the details:

* **what device to act on (the receiver)**: e.g., the light object in the living room;
* **what actions to perform**: e.g., `switchOn()` or `switchOff()`.

The remote (the **invoker**) just says:

```text
Execute this command
```

The **command** then forwards the request to the correct **receiver** (like the Light), which performs the real work, turning on or off.

If later you add a new device (say, automatic curtains or a coffee machine), you don’t need to change the remote’s logic. You simply create a new command, like `OpenCurtainsCommand` or `MakeCoffeeCommand`, and plug it in.

You can even queue up commands (“turn on lights at 7 AM”, “brew coffee at 7:05”), store them, undo them, or send them over the network. The remote doesn’t care, it just executes whatever command it’s given.

**Summary:**

The **command pattern** works just like your **smart home remote**, it separates *what you want to do* (pressing a button) from *how it’s actually done* (the device logic).

This decoupling makes it easy to extend, customize, and schedule operations without rewriting existing code.
