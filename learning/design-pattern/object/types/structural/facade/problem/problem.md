# Problem
- as systems grow, different parts start depending on each other too much;
- imagine a large application where different modules (e.g., database, authentication, logging, and UI) interact directly—changes in one module could cause unexpected issues in others;
- this makes it harder to maintain and extend the system when adding new features or modifying existing ones.

**This creates:**
- **too many dependencies** between components, making the system harder to understand and manage;
- **a maintenance nightmare**, since updating one part might require changes in multiple places, increasing the risk of bugs;
- **less flexibility**, as adding new functionality without breaking existing code becomes increasingly difficult.  
