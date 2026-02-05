# Real Life Analogy

Imagine you’re typing in a **text editor app**, something like Notepad or Word.
Every time you add a new word or sentence, the app takes a **snapshot** of your text and saves it in a hidden **history list**.

Each snapshot captures exactly what your text looked like at that moment.
Later, when you press **Ctrl + Z (Undo)**, the app doesn’t try to “reverse” your typing manually.

Instead, it simply **loads the most recent snapshot** from that history list and restores your text to that saved state.

Here’s what happens behind the scenes:

* the **text area** (where you type) is the **Originator**; it owns the data, your text, and knows how to create a snapshot of its current state;
* each **snapshot** is a **Memento**, a small object that stores the text’s content at a specific time;
* the **editor** (the overall app) is the **Caretaker**; it doesn’t look inside the snapshots or modify them; it just stores them in order and asks the text area to restore a specific one when needed.
