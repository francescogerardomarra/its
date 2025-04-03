# **Lesson: Deserializing JSON in Java 21 - Part 1**

## **1. Introduction to JSON Deserialization**
Deserialization is the process of converting a JSON file into a Java object. This is useful when working with data stored in JSON format and needing to process it in Java. The process is performed using **Jackson**, a powerful library for handling JSON in Java.

### **What is Jackson?**
Jackson is a widely used library for JSON processing in Java. It provides:
- **ObjectMapper**: Converts JSON data to Java objects (deserialization) and vice versa (serialization).
- **Annotations**: Allows customization of JSON-to-Java mappings.

### **Understanding the `Person` Class**
The `Person` class represents an entity with two attributes: `name` (a string) and `age` (an integer). These attributes are mapped to JSON properties so they can be easily serialized and deserialized.

### **Classes Needed for JSON Deserialization**
To successfully deserialize JSON files in Java, we need:
1. **Model Class (`Person.java`)**: Represents the JSON structure in Java.
2. **JSON Handler (`JsonHandler.java`)**: Contains logic for deserialization using Jackson.
3. **Main Class (`Main.java`)**: Runs the deserialization process.
4. **JSON Files (`person.json`, `invalid_person.json`)**: Contain JSON data to be loaded into Java objects.

### **Visual Representation**
#### **Deserialization (JSON → Java Object)**
```
JSON Document (File, String) -> Jackson ObjectMapper -> Java Object
```

## **2. Setting Up a Mavenized Java Project in IntelliJ (JDK 21)**

### **Step 1: Create a New Java Project**
1. Open **IntelliJ IDEA**.
2. Click on **New Project**.
3. Select **Java**.
4. Choose **JDK 21** as the SDK.

### **Step 2: Add Maven Support**
1. Ensure **Maven** is selected as the build system.
2. Click **Create** and set up the project.

### **Step 3: Configure `pom.xml`**
Modify the `pom.xml` file to include necessary dependencies:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    
    <!-- Specifies the Maven POM model version -->
    <modelVersion>4.0.0</modelVersion>
    
    <!-- Group ID uniquely identifies the project across all Maven projects -->
    <groupId>com.example</groupId>
    
    <!-- Artifact ID is the name of the jar/war file produced -->
    <artifactId>jsondeserialization1</artifactId>
    
    <!-- Project version: "SNAPSHOT" indicates it's a development version -->
    <version>1.0-SNAPSHOT</version>

    <properties>
        <!-- Defines the source file encoding to ensure consistent builds across different platforms -->
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- Dependency for Jackson Databind: used for JSON serialization and deserialization -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.15.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <!-- Specifies the Maven Compiler Plugin, which controls the Java compilation process -->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <!-- Specifies the Java version to be used for compiling source code -->
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## **3. Project Directory Structure**
```
jsondeserialization1/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/
│   │   │   │   ├── Main.java
│   │   │   │   ├── Person.java
│   │   │   │   ├── JsonHandler.java
│   │   ├── resources/
│   │   │   ├── person.json
│   │   │   ├── invalid_person.json
│   │   │   ├── malformed_person.json
│── pom.xml
```

## **4. Java Classes**

### **Person.java**
```java
package com.example;

import java.util.List;

public class Person {
    private String name;
    private int age;
    private List<String> hobbies;

    public Person() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public List<String> getHobbies() { return hobbies; }
    public void setHobbies(List<String> hobbies) { this.hobbies = hobbies; }
}
```

### **JsonHandler.java**
```java
package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonHandler {
    
    public static Person deserializeFromJson(String filePath) throws IOException {
        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Deserialize JSON file into Person object
        return objectMapper.readValue(new File(filePath), Person.class);
    }
}
```

### **Main.java**
```java
package com.example;

public class Main {
    public static void main(String[] args) {
        try {
            // Paths to JSON files
            String validJson = "src/main/resources/person.json";
            String invalidJson = "src/main/resources/invalid_person.json";
            String malformedJson = "src/main/resources/malformed_person.json";
            
            // Deserialize valid JSON
            try {
                Person person = JsonHandler.deserializeFromJson(validJson);
                System.out.println("SUCCESS - Deserialized Person: " + person.getName() + ", Age: " + person.getAge());
            } catch (Exception e) {
                System.out.println("FAILURE - Error deserializing valid JSON: " + e.getMessage());
            }
            
            // Attempt to deserialize invalid JSON (wrong data type)
            try {
                Person invalidPerson = JsonHandler.deserializeFromJson(invalidJson);
                System.out.println("SUCCESS - Deserialized Invalid Person: " + invalidPerson.getName() + ", Age: " + invalidPerson.getAge());
            } catch (Exception e) {
                System.out.println("FAILURE - Failed to deserialize invalid JSON: " + e.getMessage());
            }
            
            // Attempt to deserialize malformed JSON (syntax error)
            try {
                Person malformedPerson = JsonHandler.deserializeFromJson(malformedJson);
                System.out.println("SUCCESS - Deserialized Malformed Person: " + malformedPerson.getName() + ", Age: " + malformedPerson.getAge());
            } catch (Exception e) {
                System.out.println("FAILURE - Failed to deserialize malformed JSON: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("FAILURE - Error during deserialization: " + e.getMessage());
        }
    }
}
```

## **5. JSON Files**

### **Valid JSON File (`person.json`)**
```json
{
    "name": "John Doe",
    "age": 30,
    "hobbies": ["Reading", "Gaming", "Cycling"]
}
```

### **Invalid JSON File (`invalid_person.json`)**
```json
{
    "name": "Jane Doe",
    "age": "twenty",  // Incorrect data type
    "hobbies": ["Dancing", "Singing"]
}
```

### **Malformed JSON File (`malformed_person.json`)**
```json
{
    "name": "Alice Smith",
    "age": 25,
    "hobbies": ["Painting", "Cooking"  // Missing closing bracket and comma
```

## **6. Running the Application in IntelliJ**
1. **Ensure Maven is configured** inside IntelliJ.
2. **Open `Main.java`** in IntelliJ.
3. Click the **Run** button (▶) at the top of IntelliJ.
4. The console should print the deserialized `Person` object from `person.json`.
5. An error message should appear when attempting to deserialize `invalid_person.json` due to the incorrect data type.


# **Lesson: JSON Schema Validation in Java 21 - Part 2**

## **1. Introduction to JSON Schema Validation**
In the previous lesson, we explored basic JSON deserialization using Jackson. However, Jackson does not enforce **schema validation**—meaning it cannot check if a JSON document conforms to a predefined structure.

To validate JSON against a **JSON Schema**, we need an additional library: **Everit JSON Schema**.

### **Why Use JSON Schema?**
JSON Schema defines the structure and constraints of JSON data, ensuring:
- Correct data types
- Required fields are present
- Additional constraints (e.g. regex patterns, value ranges) are enforced

### **Key Differences from Basic Validation**
- **Jackson** can parse JSON but does not enforce structure constraints.
- **Everit JSON Schema** enables full schema validation before deserialization.

### **Classes Needed for JSON Schema Validation**
1. **Model Class (`Person.java`)**: Represents the structured data.
2. **JSON Handler (`JsonHandler.java`)**: Handles schema validation and deserialization.
3. **Main Class (`Main.java`)**: Runs the validation and deserialization process.
4. **JSON Schema (`person_schema.json`)**: Defines the expected JSON structure.
5. **JSON Files (`valid_person.json`, `invalid_person1.json`, `invalid_person2.json`, `invalid_person3.json`)**: Various JSON documents to test validation.

### **Project Directory Structure**
```
jsondeserialization2/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/
│   │   │   │   ├── Main.java
│   │   │   │   ├── Person.java
│   │   │   │   ├── JsonHandler.java
│   ├── resources/
│   │   ├── person_schema.json
│   │   ├── valid_person.json
│   │   ├── invalid_person1.json
│   │   ├── invalid_person2.json
│   │   ├── invalid_person3.json
│── pom.xml
```

## **2. Updating `pom.xml` for JSON Schema Validation**
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <!-- Specifies the Maven POM model version -->
    <modelVersion>4.0.0</modelVersion>

    <!-- Group ID uniquely identifies the project across all Maven projects -->
    <groupId>com.example</groupId>

    <!-- Artifact ID is the name of the jar/war file produced -->
    <artifactId>jsondeserialization2</artifactId>

    <!-- Project version: "SNAPSHOT" indicates it's a development version -->
    <version>1.0-SNAPSHOT</version>

    <properties>
        <!-- Defines the source file encoding to ensure consistent builds across different platforms -->
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- Jackson Databind for JSON Processing -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.15.0</version>
        </dependency>

        <!-- Everit JSON Schema Validator -->
        <dependency>
            <groupId>org.everit.json</groupId>
            <artifactId>org.everit.json.schema</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <!-- Specifies the Maven Compiler Plugin, which controls the Java compilation process -->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <!-- Specifies the Java version to be used for compiling source code -->
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## **3. JSON Schema (`person_schema.json`)**
```json
{
    "$schema": "https://json-schema.org/draft/2020-12/schema",
    "type": "object",
    "properties": {
        "name": {
            "type": "string",
            "minLength": 3
        },
        "age": {
            "type": "integer",
            "minimum": 18,
            "maximum": 100
        },
        "email": {
            "type": "string",
            "format": "email"
        }
    },
    "required": ["name", "age", "email"]
}
```

## **4. Java Classes**
### **Person.java**
```java
package com.example;

public class Person {
    private String name;
    private int age;
    private String email;

    public Person() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
```

### **JsonHandler.java**
```java
package com.example;  // Specifies the package in which this class belongs

import com.fasterxml.jackson.databind.ObjectMapper;  // Imports ObjectMapper for deserializing JSON into Java objects
import org.everit.json.schema.Schema;  // Imports the Schema class for validating JSON schema
import org.everit.json.schema.loader.SchemaLoader;  // Imports SchemaLoader for loading a JSON schema
import org.json.JSONObject;  // Imports JSONObject to work with JSON objects
import org.json.JSONTokener;  // Imports JSONTokener for parsing JSON input
import java.io.File;  // Imports File class for file handling (although not used here)
import java.io.FileInputStream;  // Imports FileInputStream for reading files as input streams
import java.io.IOException;  // Imports IOException for handling input/output exceptions

public class JsonHandler {  // Declares a class named JsonHandler
    // A static method to validate and deserialize a JSON file based on a schema
    public static Person validateAndDeserialize(String jsonFilePath, String schemaFilePath) throws IOException {

        // Open both the schema file and the JSON file as input streams using try-with-resources
        try (FileInputStream schemaStream = new FileInputStream(schemaFilePath);
             FileInputStream jsonStream = new FileInputStream(jsonFilePath)) {

            // Create a JSONObject from the schema file using JSONTokener to parse the input stream
            JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));

            // Load the schema using SchemaLoader
            Schema schema = SchemaLoader.load(rawSchema);

            // Create a JSONObject from the JSON data file using JSONTokener to parse the input stream
            JSONObject jsonData = new JSONObject(new JSONTokener(jsonStream));

            // Validate the JSON data against the loaded schema
            schema.validate(jsonData);

            // Create an ObjectMapper to handle JSON deserialization
            ObjectMapper objectMapper = new ObjectMapper();

            // Deserialize the JSON data into a Person object
            Person person = objectMapper.readValue(jsonData.toString(), Person.class);

            // Output the result of deserialization, showing the details of the Person object
            System.out.println("SUCCESS - Deserialized Person: " + person.getName() + ", Age: " + person.getAge() + ", Email: " + person.getEmail());

            return person;
        }
    }
}
```

### **Main.java**
```java
package com.example;  // Specifies the package in which this class belongs

public class Main {  // Declares the Main class, which contains the main method
    public static void main(String[] args) {  // Main method where the program execution starts

        // Define the path to the JSON schema file
        String schemaPath = "src/main/resources/person_schema.json";
        
        // List of JSON files to be validated and deserialized
        String[] jsonFiles = {
            "src/main/resources/valid_person.json",  // A valid JSON file
            "src/main/resources/invalid_person1.json",  // An invalid JSON file (1)
            "src/main/resources/invalid_person2.json",  // An invalid JSON file (2)
            "src/main/resources/invalid_person3.json"  // An invalid JSON file (3)
        };

        // Iterate over each JSON file for validation and deserialization
        for (String jsonFile : jsonFiles) {  
            try {
                // Call the validateAndDeserialize method from JsonHandler
                Person person = JsonHandler.validateAndDeserialize(jsonFile, schemaPath);
                
                // If successful, this message is printed, showing person details and file path
                System.out.println("SUCCESS - Deserialized Person from " + jsonFile + ": ");
                System.out.println("Name: " + person.getName() + ", Age: " + person.getAge() + ", Email: " + person.getEmail());
                
            } catch (Exception e) {  // If an error occurs during validation or deserialization
                // Print an error message indicating failure, including the file name and the error message
                System.out.println("FAILURE - Validation failed for " + jsonFile + ": " + e.getMessage());
            }
        }
    }
}
```

## **5. JSON Test Cases**
### **Valid JSON (`valid_person.json`)**
```json
{
    "name": "Alice",
    "age": 25,
    "email": "alice@example.com"
}
```

### **Invalid JSON 1 (`invalid_person1.json`) - Missing Required Field**
```json
{
    "name": "Bob",
    "age": 30
}
```

### **Invalid JSON 2 (`invalid_person2.json`) - Incorrect Data Type**
```json
{
    "name": "Charlie",
    "age": "twenty-five",
    "email": "charlie@example.com"
}
```

### **Invalid JSON 3 (`invalid_person3.json`) - Violating Constraints**
```json
{
    "name": "D",
    "age": 15,
    "email": "invalid-email"
}
```

## **6. Running the Application in IntelliJ**
1. **Ensure Maven is configured** in IntelliJ.
2. **Open `Main.java`** and run the application.
3. The console should show:
    - Success for `valid_person.json`
    - Failure messages for the invalid JSON files, detailing validation errors.

This lesson demonstrates how **JSON Schema validation** ensures structured and reliable JSON data before deserialization. 🚀

# **Lesson: JSON Schema and Java Bean Validation in Java 21 - Part 3**

## **1. JSON Schema vs. Java Bean Validation (JSR 303/JSR 380)**

### **Overview**
While JSON Schema and Java Bean Validation (JSR 303/JSR 380) both serve to enforce data constraints, they operate at different stages:
- **JSON Schema** validation happens **before** deserialization, ensuring that incoming JSON data adheres to a defined structure.
- **Java Bean Validation** occurs **after** deserialization, enforcing additional constraints on Java objects.

### **Comparison Table**

| Feature                | JSON Schema Validation | Java Bean Validation (JSR 303/380) |
|------------------------|------------------------|------------------------------------|
| When it applies       | Before deserialization | After deserialization             |
| Scope of validation   | JSON structure, types, required fields | Object-level constraints (annotations on Java fields) |
| Example constraints   | `type`, `minimum`, `maxLength`, `pattern` | `@NotNull`, `@Size`, `@Pattern`, `@Max`, `@Min` |
| Enforced by           | Everit JSON Schema     | Hibernate Validator (or similar)  |
| Error handling        | Prevents invalid JSON from being parsed | Prevents invalid objects from being processed |

### **Complementary Usage**
Although JSON Schema ensures that incoming JSON follows a valid structure, it does not enforce Java-specific rules. Java Bean Validation serves as an additional safeguard to verify constraints on Java objects before they are processed in an application.

For instance, both validation methods can enforce a maximum age constraint:
- **JSON Schema:** `{ "age": { "type": "integer", "maximum": 100 } }`
- **Java Bean Validation:** `@Max(100) private int age;`

This redundancy ensures robustness in applications, catching errors at different stages.

**Important note:** The validation annotations do not trigger automatically when you create the object. The validation only occurs when explicitly triggered by a validation framework like Hibernate Validator or Jakarta Bean Validation. However, in the Spring Framework, if you want validation to be triggered automatically, you can use the `@Valid` annotation on method parameters, such as in controller methods or service methods, where Spring will automatically validate the object before the method execution. This is typically used in Spring MVC for request bodies or form data.

---

## **2. Adding Java Bean Validation Annotations**

```
jsondeserialization3/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/
│   │   │   │   ├── Main.java
│   │   │   │   ├── Person.java
│   │   │   │   ├── JsonHandler.java
│   ├── resources/
│   │   ├── person_schema.json
│   │   ├── valid_person.json
│   │   ├── invalid_person1.json
│   │   ├── invalid_person2.json
│   │   ├── invalid_person3.json
│── pom.xml
```

We will modify our `Person` class by adding Java Bean Validation annotations.

### **Updated `Person.java`**
```java
package com.example;

import jakarta.validation.constraints.*;  // Importing validation annotations from Jakarta Validation API

// This class represents a Person entity that is used in JSON deserialization
// It includes validation constraints to ensure that the data is correct before processing.
public class Person {

    // The name field is validated with two annotations:
    // - @NotNull: Ensures that the 'name' field is not null.
    // - @Size(min = 3): Ensures that the 'name' field is at least 3 characters long.
    // If these conditions are not met, a validation message will be provided.
    @NotNull(message = "Name cannot be null")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    // The age field is validated with two annotations:
    // - @Min(value = 18): Ensures that the 'age' field is at least 18.
    // - @Max(value = 100): Ensures that the 'age' field is no greater than 100.
    // If the age does not meet these constraints, a validation message will be provided.
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than or equal to 100")
    private int age;

    // The email field is validated with two annotations:
    // - @NotNull: Ensures that the 'email' field is not null.
    // - @Pattern(regexp = ...): Ensures that the 'email' follows a valid email format.
    // The regular expression defines a simple email pattern that allows alphanumeric characters and common symbols.
    // If these conditions are not met, a validation message will be provided.
    @NotNull(message = "Email cannot be null")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    private String email;

    // Default constructor for deserialization (necessary for some deserialization frameworks like Jackson)
    public Person() {}

    // Getter for 'name' field
    public String getName() {
        return name;
    }

    // Setter for 'name' field
    public void setName(String name) {
        this.name = name;
    }

    // Getter for 'age' field
    public int getAge() {
        return age;
    }

    // Setter for 'age' field
    public void setAge(int age) {
        this.age = age;
    }

    // Getter for 'email' field
    public String getEmail() {
        return email;
    }

    // Setter for 'email' field
    public void setEmail(String email) {
        this.email = email;
    }
}
```

### **Updating `pom.xml` for Java Bean Validation**
To enable Java Bean Validation, we need to add the Hibernate Validator dependency to `pom.xml`:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <!-- Specifies the Maven POM model version -->
    <modelVersion>4.0.0</modelVersion>

    <!-- Group ID uniquely identifies the project across all Maven projects -->
    <groupId>com.example</groupId>

    <!-- Artifact ID is the name of the jar/war file produced -->
    <artifactId>jsondeserialization3</artifactId>

    <!-- Project version: "SNAPSHOT" indicates it's a development version -->
    <version>1.0-SNAPSHOT</version>

    <properties>
        <!-- Defines the source file encoding to ensure consistent builds across different platforms -->
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
	<!-- Jackson Databind: Used for converting JSON data into Java objects and vice versa -->
	<dependency>
	    <groupId>com.fasterxml.jackson.core</groupId>
	    <artifactId>jackson-databind</artifactId>
	    <version>2.15.0</version>
	    <!-- Jackson is used for JSON deserialization (mapping JSON to Person objects) -->
	</dependency>

	<!-- Everit JSON Schema Validator: Provides functionality to validate JSON data against JSON schema -->
	<dependency>
	    <groupId>org.everit.json</groupId>
	    <artifactId>org.everit.json.schema</artifactId>
	    <version>1.0.0</version>
	    <!-- Everit JSON Schema Validator validates JSON data against a predefined JSON schema (e.g. person_schema.json) -->
	</dependency>

	<!-- Hibernate Validator: An implementation of the Bean Validation API (JSR 303/JSR 380) used to perform Java Bean validation -->
	<dependency>
	    <groupId>org.hibernate.validator</groupId>  <!-- Hibernate Validator is the reference implementation of Bean Validation -->
	    <artifactId>hibernate-validator</artifactId>  <!-- This dependency provides the core validation logic for annotations like @NotNull, @Size, etc. -->
	    <version>8.0.0.Final</version>  <!-- Specifies the version of Hibernate Validator to use -->
	</dependency>

	<!-- Jakarta Expression Language (EL): Required for certain Bean Validation features like using expressions in constraints -->
	<dependency>
	    <groupId>org.glassfish</groupId>
	    <artifactId>jakarta.el</artifactId>
	    <version>4.0.2</version>  <!-- EL is used for handling expressions in constraints (e.g. for custom validation logic) -->
	</dependency>

	<!-- Jakarta Validation API: Defines the API for Java Bean validation annotations like @NotNull, @Size, etc. -->
	<dependency>
	    <groupId>jakarta.validation</groupId>  <!-- The Jakarta Validation API provides the annotations and interfaces used for Bean Validation -->
	    <artifactId>jakarta.validation-api</artifactId>  <!-- This API is the basis for validation annotations such as @Min, @NotNull, etc. -->
	    <version>3.0.0</version>  <!-- This version is compatible with the Jakarta EE 9+ ecosystem -->
	</dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <!-- Specifies the Maven Compiler Plugin, which controls the Java compilation process -->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <!-- Specifies the Java version to be used for compiling source code -->
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```
---

## **3. Triggering Java Bean Validation**

We will update `JsonHandler.java` to validate the deserialized `Person` object using Java Bean Validation.

### **Updated `JsonHandler.java`**
```java
package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;  // Jackson library to handle JSON deserialization
import org.everit.json.schema.Schema;  // Everit library for JSON schema validation
import org.everit.json.schema.loader.SchemaLoader;  // Everit library for loading the JSON schema
import org.json.JSONObject;  // JSON object representation used for schema and data
import org.json.JSONTokener;  // JSON tokener for reading and parsing JSON
import jakarta.validation.*;  // Jakarta Bean Validation API for validating Java beans

import java.io.FileInputStream;  // File stream to read JSON and schema files
import java.io.IOException;  // Exception handling
import java.util.Set;  // Set collection for constraint violations

public class JsonHandler {

    // This method handles both JSON schema validation and Java Bean Validation (JSR 303/JSR 380) for the Person object.
    public static Person validateAndDeserialize(String jsonFilePath, String schemaFilePath) throws IOException {
        // Open the JSON schema file and JSON data file using FileInputStream
        try (FileInputStream schemaStream = new FileInputStream(schemaFilePath);
             FileInputStream jsonStream = new FileInputStream(jsonFilePath)) {

            // Load the JSON schema from the schema file using Everit SchemaLoader
            JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));  // Read schema file
            Schema schema = SchemaLoader.load(rawSchema);  // Load the schema using the loader
            JSONObject jsonData = new JSONObject(new JSONTokener(jsonStream));  // Read JSON data

            // Validate the JSON data against the loaded schema
            try {
                schema.validate(jsonData);  // Throws an exception if the JSON doesn't match the schema
            } catch (Exception e) {
                // If JSON schema validation fails, output the failure reason
                System.out.println("JSON Schema Validation failed: " + e.getMessage());
                throw new RuntimeException("JSON Schema Validation failed", e);  // Re-throw to stop further processing
            }

            // Use Jackson ObjectMapper to deserialize JSON data into the Person object
            ObjectMapper objectMapper = new ObjectMapper();  // Create an ObjectMapper instance
            Person person = objectMapper.readValue(jsonData.toString(), Person.class);  // Deserialize JSON to Person object

            // Manually trigger Bean Validation for the Person object
            try {
                validatePerson(person);  // Trigger Bean Validation, this is where the annotations will be checked
            } catch (RuntimeException e) {
                // If Java Bean Validation fails, output the failure reason
                System.out.println("Java Bean Validation failed: " + e.getMessage());
                throw e;  // Re-throw the exception to propagate the error
            }

            // Return the deserialized Person object if it is valid
            return person;  // Return the successfully validated and deserialized Person object
        }
    }

    // This method performs manual validation of the Person object using Jakarta Bean Validation (JSR 303/JSR 380)
    private static void validatePerson(Person person) {
        // Create a ValidatorFactory and Validator instance to perform the validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();  // Build the default ValidatorFactory
        Validator validator = factory.getValidator();  // Obtain a Validator instance from the factory

        // Perform the actual validation of the Person object
        Set<ConstraintViolation<Person>> violations = validator.validate(person);  // Validate the Person object and collect violations

        // If there are any validation violations, handle them by throwing an exception
        if (!violations.isEmpty()) {  // If there are any validation issues
            StringBuilder errorMessage = new StringBuilder("Java Bean Validation failed:");  // Initialize an error message
            for (ConstraintViolation<Person> violation : violations) {  // Loop through each violation
                errorMessage.append("\n").append(violation.getMessage());  // Append the violation message to the error message
            }
            // Throw a RuntimeException with the full list of validation error messages
            throw new RuntimeException(errorMessage.toString());  // Exception is thrown if validation fails
        }
    }
}
```

### **Main.java**
```java
package com.example;  // Specifies the package in which this class belongs

public class Main {  // Declares the Main class, which contains the main method
    public static void main(String[] args) {  // Main method where the program execution starts

        // Define the path to the JSON schema file
        String schemaPath = "src/main/resources/person_schema.json";

        // List of JSON files to be validated and deserialized
        String[] jsonFiles = {
                "src/main/resources/valid_person.json",       // ✅ Valid JSON
                "src/main/resources/invalid_person1.json",    // ❌ Name too short
                "src/main/resources/invalid_person2.json",    // ❌ Age too low
                "src/main/resources/invalid_person3.json"    // ❌ Invalid email format
        };

        // Iterate over each JSON file for validation and deserialization
        for (String jsonFile : jsonFiles) {
            System.out.println("\n** ATTEMPT TO DESERIALISE JSON FROM PATH " + jsonFile + " **");

            try {
                // Call the validateAndDeserialize method from JsonHandler
                Person person = JsonHandler.validateAndDeserialize(jsonFile, schemaPath);

                // If successful, print success message with person details
                System.out.println("SUCCESS - Deserialized Person from " + jsonFile + ": ");
                System.out.println("Name: " + person.getName() + ", Age: " + person.getAge() + ", Email: " + person.getEmail());

            } catch (Exception e) {  // Catch validation or deserialization errors
                // Print an error message indicating failure, including file name and error details
                System.out.println("FAILURE - Validation failed for " + jsonFile + ": " + e.getMessage());
            }
        }
    }
}
```

---

## **4. Testing with JSON Examples**

We will now test Java Bean Validation and JSON Schema validation with different JSON inputs.

### JSON Schema (`person_schema.json`)**
```json
{
    "$schema": "https://json-schema.org/draft/2020-12/schema",
    "type": "object",
    "properties": {
        "name": {
            "type": "string",
            "minLength": 3
        },
        "age": {
            "type": "integer",
            "minimum": 18,
            "maximum": 100
        },
        "email": {
            "type": "string",
            "format": "email"
        }
    },
    "required": ["name", "age", "email"]
}
```

### **Valid JSON (valid_person.json)** ✅
```json
{
    "name": "Alice",
    "age": 25,
    "email": "alice@example.com"
}
```
- **Passes JSON Schema validation** ✅
- **Passes Java Bean validation** ✅

### **Invalid JSON 1 (invalid_person1.json) - Name Too Short** ❌
```json
{
    "name": "Al",
    "age": 25,
    "email": "alice@example.com"
}
```
- **Fails JSON Schema validation** (Name must be at least 3 characters) ❌
- **Not validated by Java Bean validation due to schema failure** 🚫

### **Invalid JSON 2 (invalid_person2.json) - Age Too Low** ❌
```json
{
    "name": "Bob",
    "age": 16,
    "email": "bob@example.com"
}
```
- **Fails JSON Schema validation** (Age must be at least 18) ❌
- **Not validated by Java Bean validation due to schema failure** 🚫

### **Invalid JSON 3 (invalid_person3.json) - Invalid Email Format** ❌
```json
{
    "name": "Charlie",
    "age": 30,
    "email": "charlie.com"
}
```
- **Passes JSON Schema validation** ✅
- **Fails Java Bean validation** (Invalid email format) ❌

## **5. Running the Application**
When executed, the application should:
- **Pass validation for `valid_person.json`** ✅
- **Fail JSON Schema validation for `invalid_person1.json`, `invalid_person2.json`** ❌
- **Fail Java Bean Validation for `invalid_person3.json`** ❌

```bash
** ATTEMPT TO DESERIALISE JSON FROM PATH src/main/resources/valid_person.json **
Feb 12, 2025 3:33:08 PM org.hibernate.validator.internal.util.Version <clinit>
INFO: HV000001: Hibernate Validator 8.0.0.Final
SUCCESS - Deserialized Person from src/main/resources/valid_person.json: 
Name: Alice, Age: 25, Email: alice@example.com

** ATTEMPT TO DESERIALISE JSON FROM PATH src/main/resources/invalid_person1.json **
JSON Schema Validation failed: expected minLength: 3, actual: 2
FAILURE - Validation failed for src/main/resources/invalid_person1.json: JSON Schema Validation failed

** ATTEMPT TO DESERIALISE JSON FROM PATH src/main/resources/invalid_person2.json **
JSON Schema Validation failed: 16.0 is not higher or equal to 18
FAILURE - Validation failed for src/main/resources/invalid_person2.json: JSON Schema Validation failed

** ATTEMPT TO DESERIALISE JSON FROM PATH src/main/resources/invalid_person3.json **
Java Bean Validation failed: Java Bean Validation failed:
Invalid email format
FAILURE - Validation failed for src/main/resources/invalid_person3.json: Java Bean Validation failed:
Invalid email format

Process finished with exit code 0
```

# **Lesson: Serializing/Deserializing JSON in Java 21 with Jackson Annotations**

## **1. Introduction to JSON Serialization and Deserialization**
Serialization is the process of converting a Java object into a JSON format, while deserialization is the reverse—converting a JSON file into a Java object. This is useful when working with JSON-based data in Java applications. The **Jackson** library provides a simple and efficient way to handle JSON in Java.

### **What is Jackson?**
Jackson is a powerful JSON processing library in Java that provides:
- **ObjectMapper**: A class for converting Java objects to JSON (serialization) and vice versa (deserialization).
- **Annotations**: Used to customize JSON-to-Java mappings.

### **Understanding Jackson Annotations**
Jackson provides various annotations that control how JSON data is serialized and deserialized. However, it is important to note that these annotations **do not perform validation**; they only provide hints to the serializer and deserializer.

### **Five Key Jackson Annotations**
1. **@JsonProperty** - Specifies the JSON property name for a field.
2. **@JsonIgnore** - Excludes a field from serialization and deserialization.
3. **@JsonInclude** - Controls the inclusion of fields based on criteria (e.g. non-null values).
4. **@JsonCreator** - Marks a constructor or factory method for deserialization.
5. **@JsonSetter** - Defines a custom setter for deserialization.

### **Annotation Summary Table**
| Annotation     | Serialization | Deserialization |
|--------------|--------------|--------------|
| @JsonProperty | Yes | Yes |
| @JsonIgnore | Yes | Yes |
| @JsonInclude | Yes | No |
| @JsonCreator | No | Yes |
| @JsonSetter | No | Yes |

---

## **2. Setting Up a Mavenized Java Project in IntelliJ (JDK 21)**

### **Step 1: Create a New Java Project**
1. Open **IntelliJ IDEA**.
2. Click on **New Project**.
3. Select **Java**.
4. Choose **JDK 21** as the SDK.

### **Step 2: Add Maven Support**
1. Ensure **Maven** is selected as the build system.
2. Click **Create** and set up the project.

### **Step 3: Configure `pom.xml`**
Modify the `pom.xml` file to include necessary dependencies:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

   <!-- Specifies the Maven POM model version -->
   <modelVersion>4.0.0</modelVersion>

   <!-- Group ID uniquely identifies the project across all Maven projects -->
   <groupId>com.example</groupId>

   <!-- Artifact ID is the name of the jar/war file produced -->
   <artifactId>jsonserialization</artifactId>

   <!-- Project version: "SNAPSHOT" indicates it's a development version -->
   <version>1.0-SNAPSHOT</version>

   <properties>
      <!-- Defines the source file encoding to ensure consistent builds across different platforms -->
      <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
   </properties>

   <dependencies>
      <!-- Dependency for Jackson Databind: used for JSON serialization and deserialization -->
      <dependency>
         <groupId>com.fasterxml.jackson.core</groupId>
         <artifactId>jackson-databind</artifactId>
         <version>2.15.0</version>
      </dependency>

      <!-- Dependency for Lombok: reduces boilerplate code by auto-generating getters, setters, constructors, and more at compile time -->
      <dependency>
         <groupId>org.projectlombok</groupId>
         <artifactId>lombok</artifactId>
         <version>1.18.36</version>
         <scope>provided</scope>
      </dependency>
   </dependencies>

   <build>
      <plugins>
         <plugin>
            <!-- Specifies the Maven Compiler Plugin, which controls the Java compilation process -->
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
               <!-- Specifies the Java version to be used for compiling source code -->
               <source>21</source>
               <target>21</target>
            </configuration>
         </plugin>
      </plugins>
   </build>
</project>
```

---

## **3. Implementing the `Person` Class with Annotations**

```
jsonserialization/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/
│   │   │   │   ├── Main.java
│   │   │   │   ├── Person.java
│   │   │   │   ├── JsonHandler.java
│   ├── resources/
│   │   ├── person.json
│── pom.xml
```

```java
package com.example;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Person {

   // @JsonProperty: This annotation is used to specify the name of the property during serialization and deserialization.
   // It maps the "full_name" JSON field to the "name" Java field.
   @JsonProperty("full_name")
   private String name;

   private int age;

   // @JsonIgnore: This annotation prevents the "password" field from being serialized or deserialized.
   // It tells Jackson to ignore this property during the JSON processing.
   @JsonIgnore
   private String password;

   // @JsonInclude: This annotation specifies that the field should only be included in serialization if its value is not null.
   // If the email field is null, it will be omitted from the JSON output.
   @JsonInclude(JsonInclude.Include.NON_NULL)
   private String email;

   // @JsonCreator: This annotation marks the constructor to be used for deserialization.
   // It allows the Jackson library to create a Person object using the provided properties during deserialization.
   @JsonCreator
   public Person(
           // @JsonProperty: Specifies that the constructor parameter "full_name" should be mapped from the "full_name" field in the JSON data.
           @JsonProperty("full_name") String name,
           @JsonProperty("age") int age) {
      this.name = name;
      this.age = age;
   }
}
```

---

## **4. Main Class for Serialization and Deserialization**

```java
package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Main {
   public static void main(String[] args) {
      ObjectMapper objectMapper = new ObjectMapper();

      try {
         // Deserialize JSON from file
         Person person = objectMapper.readValue(new File("src/main/resources/person.json"), Person.class);
         System.out.println("Deserialized Person: " + person.getName());

         // Serialize different Person objects to demonstrate annotations

         // 1. Basic serialization with @JsonProperty
         Person person1 = new Person("John Doe", 30);
         objectMapper.writeValue(new File("person1.json"), person1);
         // This will rename 'name' to 'full_name' in JSON output

         // 2. Ignoring a field with @JsonIgnore
         Person person2 = new Person("Jane Doe", 25);
         person2.setPassword("secret123");
         objectMapper.writeValue(new File("person2.json"), person2);
         // The 'password' field should not appear in the JSON file

         // 3. Using @JsonInclude to omit null fields
         Person person3 = new Person("Mike Ross", 40);
         objectMapper.writeValue(new File("person3.json"), person3);
         // 'email' will not be present in the JSON output because is null

         System.out.println("\nSerialized JSON files created successfully.");
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
```

---

## **5. JSON File (`person.json`)**

```json
{
   "full_name": "Alice Johnson",
   "age": 28,
   "email": "alice@example.com"
}
```

---

## **6. Running the Application in IntelliJ**
1. Ensure Maven is configured.
2. Place `person.json` and `person4.json` in the project's root directory.
3. Open `Main.java` and run the program.
4. The console should print deserialized person details and create multiple serialized JSON files.

---
