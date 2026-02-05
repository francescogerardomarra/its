# Real Life Analogy - Analogy 2 - Login screen

Think of a typical **login screen** in an app, it has two **text fields** (for username and password) and a **login button**.

At first, you might design it so that the **login button** directly reads the values from the text fields when clicked. That works, but now the button *depends* on those specific fields. If you want to reuse the same button elsewhere in the app (say on a signup page), you can’t, because it’s tightly tied to those text boxes.

To fix that, you introduce a **mediator**, in this case, a **dialog** object.
Now, the button doesn’t directly talk to the text fields anymore.
Instead:

* when the button is clicked, it just tells the **dialog**: "Hey, I was clicked";
* the **dialog (mediator)** then gathers the username and password from the text fields, runs the validation, and decides what to do next.

So the **dialog** acts as the **central coordinator** for all the UI elements, the button, the text fields, and even the labels. Each element knows *only* the mediator, not each other.

This is exactly what the **mediator pattern** does in real-world UI design:
it removes the need for individual components to know about or directly communicate with one another, making them simpler, more reusable, and easier to maintain.

---

**In short:**

> The **login page** itself is the real-world analogy, the **dialog** in the UI plays the same role as a **mediator**, coordinating interactions between independent elements (button, fields, labels) so they don’t depend on one another.

---

<img src="img/analogy_two.png">
