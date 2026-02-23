# Real Life Analogy - Example One

**The scenario:**

Imagine you are an engineer at Amazon tasked with revamping the UI for the books section. You need to render, fetch, and display **millions of books** on the website.

---

**The naive approach (the problem):**

Initially, you create a standard `Book` class. Every time a book is displayed, you create a new object containing all of its data:

* **unique data:** title, price, ISBN;
* **shared data:** book type (e.g., "action"), distributor info, and other metadata associated with that type.

When you try to load millions of books, your manager's computer crashes. Why? Because you created millions of separate objects, each storing the exact same heavy "shared data" over and over again. You ran out of RAM.

---

**The flyweight solution:**

To fix this, you apply the flyweight pattern by splitting the book's data into two categories:

1. **intrinsic state (the flyweight):** 
   * this is the heavy data that is **shared** across many books and rarely changes (e.g., the `BookType`, `Distributor`, and `metadata`); 
   * instead of storing this in every single book object, you store it in a separate `BookType` class;
   * you only create **one** instance of this class for "action" books, one for "fantasy" books, etc.
2. **extrinsic state (the context):** 
   * this is the unique data that varies per book (e.g., `Title`, `Price`);
   * the `Book` class keeps these unique fields but removes the heavy shared data.

---

**How they connect:**

Instead of holding all the data itself, the lightweight `Book` object simply holds a *reference* (a link) to the specific `BookType` object it belongs to.

---

**The result:**

* **before:** 1 million books = 1 million copies of the "distributor" data in RAM;
* **after:** 1 million books = 1 million lightweight links pointing to just *one* shared "distributor" object in RAM.
