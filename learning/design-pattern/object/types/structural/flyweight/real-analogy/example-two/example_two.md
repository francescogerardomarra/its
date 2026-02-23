# Real Life Analogy - Example Two

Imagine you are building a video game that needs to render a forest with **1,000,000 trees**.

**The problem (without flyweight):**

If you create a standard `Tree` object for every single tree, each object might contain:

* a mesh (geometry model): roughly 5MB;
* a texture (bark image): roughly 2MB;
* coordinates (X, Y): negligible.

**Math:**  

(7 Terabytes of RAM).

**Result:** 

your game crashes instantly.

---

**The solution (with flyweight):**

You realize that while the *position* of every tree is different, the *texture and mesh* are exactly the same for every "oak tree".

1. **create a flyweight (`TreeType`):** 
   * this creates a single object that stores the heavy mesh and texture;
   * you only make **one** of these for "oak", **one** for "pine", etc.
2. **create the context (`Tree`):** this lightweight object only stores the coordinates `(x, y)` and a reference to the `TreeType`.

**Math:**

* 1 Shared oak Object: 7 MB;
* 1,000,000 lightweight references: 20 MB total;

**Result:** 

the game runs smoothly using roughly 27 MB of RAM.
