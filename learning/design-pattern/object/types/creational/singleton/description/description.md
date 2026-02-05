# Description

The Singleton design pattern is a creational design pattern that ensures a class has only one instance throughout the lifetime of an application and provides a global point of access to that instance.

**Structure:**

- a private constructor to prevent direct instantiation;
- a static instance variable that holds the single object;
- a public static method (like getInstance()) that returns the single instance, creating it if it doesn’t exist yet.
