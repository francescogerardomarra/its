# Object-relational mismatch
The **Object-Relational Impedance Mismatch** happens when trying to store object-oriented data in a relational database. This mismatch exists because object-oriented programming (OOP) and relational databases work in very different ways.

In OOP, data is stored as **objects** that have properties and methods. But relational databases store data in **tables** with rows and columns. Since objects don’t naturally fit into tables, converting them can be tricky.

Some key differences that cause this mismatch:
- **Objects vs. Tables** – Objects store data with behaviors (methods), while tables only store raw data in rows and columns.
- **Inheritance vs. Joins** – In OOP, objects can inherit properties from other objects, but databases don’t support inheritance. Instead, they use joins and foreign keys to link tables.
- **Complex Relationships** – OOP allows for complex structures like polymorphism and encapsulation, while relational databases rely on simple key-based relationships.
- **Data vs. Logic** – In OOP, objects contain both data and behavior, but in databases, data is kept separate from logic, and integrity is enforced with constraints like primary and foreign keys.

Because of these differences, mapping objects to a database can be challenging. This is why tools like **Hibernate** and **JPA** exist—they help translate between objects and relational tables, making it easier to store and retrieve data without losing the benefits of OOP.  
