# Maven `go-offline` Tip

- `mvn dependency:go-offline` is a Maven command used to download all project dependencies and plugins required for building the project;
- it ensures that all necessary artifacts are stored in the local repository so that future builds can run without an internet connection;
- it does not change the Maven configuration to offline mode but rather prefetches all necessary resources;
 

- it is useful when you plan to build a project in an environment without internet access;
- it resolves transitive dependencies as well, ensuring that even indirectly required artifacts are available locally.

**Do I have to come back online?**

- `mvn dependency:go-offline` itself does not change any settings related to online or offline mode;
- Maven works online by default, unless explicitly set to offline mode using the `-o` flag (e.g., `mvn -o clean install`);
- after running `mvn dependency:go-offline`, you don't need to run any specific command to go back online;


- simply run your Maven commands without the `-o` flag, and Maven will automatically download any missing or updated dependencies.
