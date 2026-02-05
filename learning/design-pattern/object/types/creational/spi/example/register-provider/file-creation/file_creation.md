# `PaymentProcessor` File Creation

- in this chapter, we show how to create the file to register the service providers;
- the `ServiceLoader` Java class, will use this file to load the service providers at runtime;
- remember that is procedure is valid before Java 9 or without using JPMS;


- using JPMS, this file creation is not needed anymore, but provider registration is achieved using [provides](../../../../../../../../java/chapter-x/versions/java9/features/jpms/after/module/how-work/module-info/directive/use-provide/provide/definition/definition.md) directive.

**Registration file creation:**

1. to build the previous [project structure](../../project-structure/project_structure.md) we used the Intellij IDE;
2. if you want to build it manually use the `javac -d out/` command to place the compiled classes within the `out` folder;
3. replicate the structure of the `out` file of the [project structure](../../project-structure/project_structure.md);


4. add the following registration file `main.service.PaymentProcessor` file within the folder `out/test/production/my-test-4/META-INF` as [here](../../project-structure/project_structure.md);
5. add the following content to the `main.service.PaymentProcessor` registration file.

**`main.service.PaymentProcessor` registration file overview:**

- within the `main.service.PaymentProcessor` file, we provide the fully qualified names of the service provider classes;
- the `ServiceLoader` identifies the service interface by matching the file name: `main.service.PaymentProcessor`, which should be the fully qualified name of the service interface;
- it's important to place this file inside the `META-INF/services` folder, since `ServiceLoader` will search for provider configurations there.

**`main.service.PaymentProcessor` registration file content:**

```plaintext
main.provider.CreditCardProcessor
main.provider.PayPalProcessor
```

