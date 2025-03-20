# Understanding Annotations in Java

## What are Annotations in Java?
Annotations in Java are a form of metadata that provide data about a program but are not part of the program itself. They are used to give instructions to the compiler, provide configuration information, and assist frameworks and tools in processing code efficiently. Introduced in Java 5, annotations have since become a cornerstone of Java development, allowing developers to simplify and standardize code.

## Role of Annotations
Annotations play a significant role in enhancing the developer's productivity by:

1. **Reducing Boilerplate Code**: By eliminating repetitive tasks and providing default implementations, annotations can significantly cut down the amount of code required.
2. **Improving Readability**: They make code more concise and clear, which simplifies understanding and maintenance.
3. **Providing Metadata**: Annotations supply extra information to tools and frameworks, aiding in processing code during compilation or runtime.
4. **Optimizing Configuration**: They replace complex configuration files, making application setup easier and less error-prone.

## Why Were Annotations Introduced?
Annotations were introduced to:

- Reduce verbosity by eliminating the need for extensive XML configuration files.
- Allow developers to focus on business logic rather than boilerplate code.
- Enable frameworks to integrate seamlessly with Java code using declarative programming.

By integrating metadata directly into the code, annotations improve efficiency and reduce the chances of configuration mismatches.

---

## Practical Examples

### Example 1: Transaction Management with and without `@Transactional`

The `@Transactional` annotation simplifies transaction management in Spring applications. Without it, developers need to handle transactions programmatically, resulting in verbose and error-prone code.

#### Without `@Transactional`
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class AccountService {

    @Autowired
    private PlatformTransactionManager transactionManager;

    public void transferMoney(String fromAccount, String toAccount, double amount) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            // Perform database operations
            debitAccount(fromAccount, amount);
            creditAccount(toAccount, amount);

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    private void debitAccount(String account, double amount) {
        // Debit logic
    }

    private void creditAccount(String account, double amount) {
        // Credit logic
    }
}
```

#### With `@Transactional`
```java
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Transactional
    public void transferMoney(String fromAccount, String toAccount, double amount) {
        // Perform database operations
        debitAccount(fromAccount, amount);
        creditAccount(toAccount, amount);
    }

    private void debitAccount(String account, double amount) {
        // Debit logic
    }

    private void creditAccount(String account, double amount) {
        // Credit logic
    }
}
```
##### Burden Relieved:
- No need to manually manage transaction lifecycles.
- Reduced risk of errors such as forgetting to commit or rollback.
##### Optimization Achieved:
- Improved readability and maintainability.
- Declarative transaction management.

---

### Example 2: Getters and Setters with Lombok

Lombok annotations like `@Getter` and `@Setter` reduce the boilerplate code required for simple getter and setter methods.

#### Without Lombok
```java
public class Person {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

#### With Lombok
```java
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private String name;
    private int age;
}
```
##### Burden Relieved:
- Eliminates the need to write and maintain repetitive getter and setter methods.
##### Optimization Achieved:
- Cleaner and shorter code, enhancing readability and reducing potential errors.

---

### Example 3: Using `@Override`
The `@Override` annotation is used to indicate that a method overrides a method in a superclass. While it doesn't replace code, it serves as a hint to the compiler and developer.

#### Without `@Override`
```java
class Parent {
    void display() {
        System.out.println("Parent display");
    }
}

class Child extends Parent {
    void display() { // No indication of overriding
        System.out.println("Child display");
    }
}
```

#### With `@Override`
```java
class Parent {
    void display() {
        System.out.println("Parent display");
    }
}

class Child extends Parent {
    @Override
    void display() { // Clear indication of overriding
        System.out.println("Child display");
    }
}
```
##### Highlight:
- Prevents errors where the method signature doesn't match the superclass method.

---

## Distinction Between Annotations

### Replacing Boilerplate Code
1. **`@Getter` and `@Setter`**: Simplifies getter and setter generation.
2. **`@Transactional`**: Automates transaction management.

### Serving as Hints
1. **`@Override`**: Indicates method overriding and ensures correctness.
2. **`@Deprecated`**: Marks methods or classes as obsolete.
3. **`@SuppressWarnings`**: Suppresses specific compiler warnings.

---

## Setting Up a Project in IntelliJ

### Steps to Create the Project
1. Install **IntelliJ IDEA** and **OpenJDK 11**.
2. Create a new Maven project in IntelliJ.
3. Use the following `pom.xml`:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>annotations-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.3.10</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>5.3.10</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.24</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
```

4. Add the required Java files based on the following examples.
5. Build and run the project in IntelliJ.

---

Annotations in Java are a powerful feature that enhances productivity and simplifies development. By understanding and utilizing annotations effectively, developers can write cleaner, more efficient code while reducing complexity.

## Setting Up the Examples in IntelliJ

To run the examples in this document, follow these steps:

### Steps to Add Example Classes
1. Open the Maven project created in IntelliJ following the initial setup instructions.
2. Create a package (e.g., `com.example.annotations`) under `src/main/java`.
3. Add the example classes to this package.
   - For Example 1 (Getters and Setters):
     - Create two Java files: `Person.java` and `Employee.java`.
     - Copy the provided code for `Person` and `Employee` into these files.
   - For Example 2 (Absence of `@Override`):
     - Create three Java files: `Parent.java`, `Child.java`, and `Main.java`.
     - Copy the provided code for each class into the respective file.
   - For Example 3 (Suppressing Warnings):
     - Create one Java file: `SuppressWarningExample.java`.
     - Copy the provided code into this file.
4. Add a test class `Main.java` in the package for the test code under Example 1.
5. Ensure the project compiles successfully.

### Running the Examples
1. Right-click on the `Main` class (for Example 1 or Example 2) or `SuppressWarningExample` class (for Example 3).
2. Select **Run 'Main'** or **Run 'SuppressWarningExample'** from the context menu.
3. Observe the output in the Run window to verify the functionality of the examples.

---

## Example 1: Getters and Setters (With and Without Lombok)

### Without Lombok
The `Person` class below does not use Lombok. Instead, explicit getter and setter methods are written for each field.

```java
public class Person {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

### With Lombok
Using Lombok annotations, the `Employee` class is much more concise.

```java
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    private String name;
    private int age;
}
```

### Test Code
The following test class exercises both `Person` and `Employee` objects to demonstrate their functionality.

```java
public class Main {
    public static void main(String[] args) {
        // Without Lombok
        Person person = new Person();
        person.setName("Alice");
        person.setAge(30);
        System.out.println("Person Name: " + person.getName());
        System.out.println("Person Age: " + person.getAge());

        // With Lombok
        Employee employee = new Employee();
        employee.setName("Bob");
        employee.setAge(40);
        System.out.println("Employee Name: " + employee.getName());
        System.out.println("Employee Age: " + employee.getAge());
    }
}
```

---

## Example 2: Absence of `@Override`
The `@Override` annotation ensures that methods correctly override a superclass method. If omitted, errors like method name misspellings might go unnoticed.

### Without `@Override`
Here, the `display` method in `Child` is misspelled, leading to a separate method rather than overriding `Parent`'s method.

```java
class Parent {
    void display() {
        System.out.println("Parent display");
    }
}

class Child extends Parent {
    void diaplay() { // Typo: Method does not override Parent's display()
        System.out.println("Child display");
    }
}

public class Main {
    public static void main(String[] args) {
        Parent obj = new Child();
        obj.display(); // Calls Parent's display(), not Child's
    }
}
```

### With `@Override`
The `@Override` annotation ensures the method correctly overrides the parent class method.

```java
class Parent {
    void display() {
        System.out.println("Parent display");
    }
}

class Child extends Parent {
    @Override
    void display() {
        System.out.println("Child display");
    }
}

public class Main {
    public static void main(String[] args) {
        Parent obj = new Child();
        obj.display(); // Correctly calls Child's display()
    }
}
```

---

## Example 3: Suppressing Compiler Warnings
The `@SuppressWarnings` annotation is used to prevent the compiler from generating specific warnings.

### Code Example
Here, an unused variable warning is suppressed.

```java
public class SuppressWarningExample {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        int unusedVariable = 42; // Normally raises a warning

        System.out.println("Suppressing warnings example");
    }
}
```

### Explanation
The `@SuppressWarnings("unused")` annotation tells the compiler to ignore warnings about unused variables. This can be useful in scenarios such as temporary debugging or code snippets in development.

---

## Summary

- **Getters and Setters**: Lombok annotations significantly reduce boilerplate code.
- **`@Override`**: Helps prevent method name mismatches and ensures correct overriding.
- **`@SuppressWarnings`**: Allows targeted suppression of unnecessary compiler warnings.

These annotations enhance code quality, reduce verbosity, and make debugging and maintenance easier.

# Custom Annotations in Java

## Definition of Custom Annotations
Custom annotations in Java are user-defined metadata that provide additional information or instructions for a program. They allow developers to define reusable and specialized annotations that can be processed at compile-time or runtime.

### Key Features of Custom Annotations:
- **Customization**: Tailored for specific use cases in a project.
- **Integration**: Used by frameworks and tools for processing custom behavior.
- **Reusability**: Enhance modularity by abstracting repetitive logic into annotations.

## Role of Custom Annotations
Custom annotations simplify code by abstracting logic into declarative constructs. They enable developers to:
1. **Improve Code Readability**: Annotate behaviors or configurations directly in the code.
2. **Reduce Boilerplate**: Automate repetitive tasks by processing annotations.
3. **Enable Meta-programming**: Define metadata for frameworks or tools to enhance functionality.

## Usage of Custom Annotations
Custom annotations are:
- **Defined** using `@interface`.
- **Processed** using reflection or tools like annotation processors.
- **Used** to mark classes, fields, or methods for specific behavior.

---

## Examples of Custom Annotations

### Example 1: Class-Level Annotation (`@Documentation`)
This annotation documents metadata about a class, such as author and version.

#### Annotation Code
```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Documentation {
    String author();
    String version();
}
```

#### Usage
```java
@Documentation(author = "Alice", version = "1.0")
public class MyClass {
    public void execute() {
        System.out.println("Executing MyClass");
    }
}
```

#### Processor Code
```java
public class AnnotationProcessor {
    public static void main(String[] args) {
        Class<MyClass> clazz = MyClass.class;

        if (clazz.isAnnotationPresent(Documentation.class)) {
            Documentation doc = clazz.getAnnotation(Documentation.class);
            System.out.println("Author: " + doc.author());
            System.out.println("Version: " + doc.version());
        }
    }
}
```

---

### Example 2: Field-Level Annotation (`@DefaultValue`)
This annotation specifies a default value for a field.

#### Annotation Code
```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DefaultValue {
    String value();
}
```

#### Usage
```java
import java.lang.reflect.Field;

public class Configuration {

    @DefaultValue("42")
    private String timeout;

    public void loadDefaults() throws IllegalAccessException {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DefaultValue.class)) {
                DefaultValue annotation = field.getAnnotation(DefaultValue.class);
                field.setAccessible(true);
                field.set(this, annotation.value());
            }
        }
    }

    public String getTimeout() {
        return timeout;
    }

    public static void main(String[] args) throws IllegalAccessException {
        Configuration config = new Configuration();
        config.loadDefaults();
        System.out.println("Timeout: " + config.getTimeout());
    }
}
```

---

### Example 3: Method-Level Annotation (`@LogExecutionTime`)
This annotation logs the execution time of a method.

#### Annotation Code
```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecutionTime {
}
```

#### Usage
```java
import java.lang.reflect.Method;

public class PerformanceTester {

    @LogExecutionTime
    public void performTask() throws InterruptedException {
        Thread.sleep(1000); // Simulate task
        System.out.println("Task performed");
    }

    public static void main(String[] args) throws Exception {
        PerformanceTester tester = new PerformanceTester();
        for (Method method : tester.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(LogExecutionTime.class)) {
                long start = System.currentTimeMillis();
                method.invoke(tester);
                long end = System.currentTimeMillis();
                System.out.println("Execution time: " + (end - start) + "ms");
            }
        }
    }
}
```

---

