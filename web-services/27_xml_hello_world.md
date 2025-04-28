# **Lesson: Deserializing XML in Java 21 - Part 1**

## **1. Introduction to Deserialization**
Deserialization is the process of converting an XML file into a Java object. This is useful when working with data stored in XML format and needing to process it in Java. The process is performed using **JAXB (Java Architecture for XML Binding)**, which provides annotations and utilities for mapping XML elements to Java objects.

### **What is JAXB?**
JAXB allows Java developers to map XML structures to Java classes using annotations. It provides:
- **Marshaller**: Converts Java objects into XML (serialization).
- **Unmarshaller**: Converts XML into Java objects (deserialization).

### **What is an Unmarshaller?**
An **Unmarshaller** is an object in JAXB that is responsible for converting (or **deserializing**) XML data into Java objects. It reads an XML file and maps its elements to corresponding Java class fields, based on JAXB annotations.

### **Understanding the `Person` Class**
The `Person` class represents an entity with two attributes: `name` (a string) and `age` (an integer). These attributes are mapped to XML elements so they can be easily serialized and deserialized. This class is crucial as it defines the structure into which the XML data will be converted.

### **Classes Needed for XML Deserialization**
To successfully deserialize XML files in Java, we need:
1. **Model Class (`Person.java`)**: Represents the XML structure in Java.
2. **XML Handler (`XMLHandler.java`)**: Contains logic for deserialization using JAXB.
3. **Main Class (`Main.java`)**: Runs the deserialization process.
4. **XML Files (`person.xml`, `invalid_person.xml`)**: Contain XML data to be loaded into Java objects.

### **Visual Representation**
#### **Deserialization (XML → Java Object)**
```
XML Document (File, String, Stream) -> JAXB Unmarshaller -> Java Object
```

## **2. Setting Up a Mavenized Java Project in IntelliJ (JDK 21)**

### **Step 1: Create a New Java Project**
1. Open **IntelliJ IDEA**.
2. Click on **New Project**.
3. Select **Java** (not a Maven archetype).
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
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>xmldeserialization1</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- JAXB API -->
        <dependency>
            <groupId>jakarta.xml.bind</groupId>
            <artifactId>jakarta.xml.bind-api</artifactId>
            <version>4.0.0</version>
            <!--
            The jakarta.xml.bind-api dependency provides the Jakarta XML Binding (JAXB) API. 
            JAXB allows for mapping between XML representations and Java objects (serialization and deserialization).
            This API provides classes and annotations to support the marshalling (serialization) and unmarshalling (deserialization) 
            of Java objects into XML and vice versa.
            -->
        </dependency>

        <!-- JAXB Runtime -->
        <dependency>
            <groupId>org.glassfish.jaxb</groupId>
            <artifactId>jaxb-runtime</artifactId>
            <version>4.0.0</version>
            <!--
            The jaxb-runtime dependency is the runtime implementation of the JAXB API. 
            It contains the necessary libraries for performing the actual serialization (marshalling) 
            and deserialization (unmarshalling) processes of Java objects to XML and back. 
            Without the runtime, the API cannot function properly.
            -->
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <!-- Configures the Maven Compiler Plugin to use Java 21 for source and target compatibility. -->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>21</source>  <!-- Sets the source (Java version used for compiling) to Java 21 -->
                    <target>21</target>  <!-- Sets the target (bytecode version) to Java 21 -->
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

## **3. Project Directory Structure**
```
xmldeserialization1/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/
│   │   │   │   ├── Main.java
│   │   │   │   ├── Person.java
│   │   │   │   ├── XMLHandler.java
│   │   ├── resources/
│   │   │   ├── person.xml
│   │   │   ├── invalid_person.xml
│── pom.xml
```

## **4. Java Classes**

### **Person.java**
```java
package com.example;

import jakarta.xml.bind.annotation.XmlElement;  // Import JAXB annotation to map XML elements
import jakarta.xml.bind.annotation.XmlRootElement;  // Import JAXB annotation to mark this class as a root element in XML

// The @XmlRootElement annotation indicates that this class corresponds to the root element of an XML document
@XmlRootElement
public class Person {
    private String name;  // Field to store the name of the person
    private int age;  // Field to store the age of the person

    // Default constructor required by JAXB (Java Architecture for XML Binding)
    // JAXB requires a no-argument constructor for creating instances of this class during deserialization
    public Person() {}

    // Getter and setter methods for the 'name' field
    // The @XmlElement annotation tells JAXB to map the 'name' field to an XML element with the same name
    @XmlElement
    public String getName() { 
        return name;  // Return the name of the person
    }
    public void setName(String name) { 
        this.name = name;  // Set the name of the person
    }

    // Getter and setter methods for the 'age' field
    // The @XmlElement annotation tells JAXB to map the 'age' field to an XML element with the same name
    @XmlElement
    public int getAge() { 
        return age;  // Return the age of the person
    }
    public void setAge(int age) { 
        this.age = age;  // Set the age of the person
    }
}
```

### **XMLHandler.java**
```java
package com.example;

import jakarta.xml.bind.JAXBContext;  // Import JAXBContext for creating JAXB context for binding
import jakarta.xml.bind.JAXBException;  // Import JAXBException for handling errors during marshalling/unmarshalling
import jakarta.xml.bind.Unmarshaller;  // Import Unmarshaller to convert XML back to Java objects
import java.io.File;  // Import File class to handle file input/output operations

public class XMLHandler {

    // This method deserializes (converts) an XML file into a Person object
    // It takes the file path of the XML file as a parameter
    public static Person deserializeFromXML(String filePath) throws JAXBException {

        // Create a JAXBContext instance for the Person class
        // JAXBContext is responsible for managing the XML binding process
        JAXBContext context = JAXBContext.newInstance(Person.class);

        // Create an Unmarshaller object using the JAXBContext
        // The Unmarshaller is responsible for converting XML data back into Java objects
        Unmarshaller unmarshaller = context.createUnmarshaller();

        // Unmarshal the XML file into a Person object and return it
        // The unmarshal method converts the XML data from the given file into a Java object
        return (Person) unmarshaller.unmarshal(new File(filePath));
    }
}
```

### **Main.java**
```java
package com.example;

public class Main {
    public static void main(String[] args) {
        try {
            // Define the paths to well-formed and malformed XML files
            String wellFormedXML = "src/main/resources/person.xml";  // Path to a valid XML file
            String malformedXML = "src/main/resources/invalid_person.xml";  // Path to an invalid XML file

            // Deserialize well-formed XML using XMLHandler
            // This will try to convert the XML into a Person object
            Person person = XMLHandler.deserializeFromXML(wellFormedXML);
            
            // If deserialization is successful, print the name and age of the Person object
            System.out.println("SUCCESS - Deserialized Person: " + person.getName() + ", Age: " + person.getAge());

            // Attempt to deserialize malformed XML, which is expected to fail
            try {
                // Try deserializing the invalid XML file
                Person invalidPerson = XMLHandler.deserializeFromXML(malformedXML);
                
                // If deserialization succeeds (which shouldn't happen), print the details
                System.out.println("SUCCESS - Deserialized Invalid Person: " + invalidPerson.getName() + ", Age: " + invalidPerson.getAge());
            } catch (Exception e) {
                // Catch any exception that occurs during deserialization of malformed XML
                // If it fails, print an error message with the exception details
                System.err.println("FAILURE - Failed to deserialize malformed XML: " + e.getMessage());
            }
        } catch (Exception e) {
            // Catch any unexpected errors that occur during the entire process
            // This handles general deserialization failures
            System.err.println("FAILURE - Error during deserialization: " + e.getMessage());
        }
    }
}
```

## **5. XML Files**

### **Valid XML File (`person.xml`)**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<person>
    <name>John Doe</name>
    <age>30</age>
</person>
```

### **Invalid XML File (`invalid_person.xml`)**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<person>
    <name>Jane Doe</name>
    <age>19  <!-- Missing closing tag -->
</person>
```

## **6. Running the Application in IntelliJ**
1. **Ensure Maven is configured** inside IntelliJ.
2. **Open `Main.java`** in IntelliJ.
3. Click the **Run** button (▶) at the top of IntelliJ.
4. The console should print the deserialized `Person` object from `person.xml`.
5. An error message should appear when attempting to deserialize `invalid_person.xml` due to the incorrect data type.


# **Lesson: Deserializing XML in Java 21 - Part 2**

## **1. Introduction to Deserialization with XSD**
Deserialization is the process of converting an XML file into a Java object. This lesson extends basic deserialization by including **an internal XSD (XML Schema Definition)** to define the structure of valid XML data. We explore:

- Deserializing a valid XML with an internal XSD **without validation**.
- Deserializing a valid XML with an internal XSD **with validation**.
- Attempting to deserialize an **invalid XML with an internal XSD** with validation.

### **What is JAXB with XSD?**
JAXB (Java Architecture for XML Binding) allows Java developers to:
- **Serialize** Java objects to XML.
- **Deserialize** XML back into Java objects.
- **Validate** XML using an XSD schema.

## **2. Setting Up a Mavenized Java Project in IntelliJ (JDK 21)**

### **Step 1: Create a Java Project**
1. Open **IntelliJ IDEA**.
2. Click on **New Project**.
3. Select **Java**, then choose **JDK 21** and Maven.
4. Click **Create**.

```
xmldeserialization2/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/
│   │   │   │   ├── Main.java
│   │   │   │   ├── Person.java
│   │   │   │   ├── XMLHandler.java
│   │   ├── resources/
│   │   │   ├── person_valid.xml
│   │   │   ├── person_invalid.xml
│   │   │   ├── person.xsd
│── pom.xml
```

### **Step 2: Configure `pom.xml`**
Modify `pom.xml` to include JAXB and validation dependencies:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>xmldeserialization2</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- JAXB API -->
        <dependency>
            <groupId>jakarta.xml.bind</groupId>
            <artifactId>jakarta.xml.bind-api</artifactId>
            <version>4.0.0</version>
            <!--
            The jakarta.xml.bind-api dependency provides the Jakarta XML Binding (JAXB) API. 
            JAXB allows for mapping between XML representations and Java objects (serialization and deserialization).
            This API provides classes and annotations to support the marshalling (serialization) and unmarshalling (deserialization) 
            of Java objects into XML and vice versa.
            -->
        </dependency>

        <!-- JAXB Runtime -->
        <dependency>
            <groupId>org.glassfish.jaxb</groupId>
            <artifactId>jaxb-runtime</artifactId>
            <version>4.0.0</version>
            <!--
            The jaxb-runtime dependency is the runtime implementation of the JAXB API. 
            It contains the necessary libraries for performing the actual serialization (marshalling) 
            and deserialization (unmarshalling) processes of Java objects to XML and back. 
            Without the runtime, the API cannot function properly.
            -->
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <!-- Configures the Maven Compiler Plugin to use Java 21 for source and target compatibility. -->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>21</source>  <!-- Sets the source (Java version used for compiling) to Java 21 -->
                    <target>21</target>  <!-- Sets the target (bytecode version) to Java 21 -->
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

## **3. Java Classes**

### **Person.java**
```java
package com.example;

import jakarta.xml.bind.annotation.*;  // Import JAXB annotations for XML binding

// Annotates the class as a root element when converted to XML
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)  // Specifies that JAXB should bind the fields directly, not the getter/setter methods
public class Person {

    // Private fields to hold the person's data
    private String name;  // Name of the person
    private int age;  // Age of the person
    private String email;  // Email of the person (new field added)

    // Default constructor required by JAXB for deserialization (must be public or protected)
    public Person() {}

    // Getter method for the 'name' field
    public String getName() {
        return name;  // Returns the name of the person
    }

    // Setter method for the 'name' field
    public void setName(String name) {
        this.name = name;  // Sets the name of the person
    }

    // Getter method for the 'age' field
    public int getAge() {
        return age;  // Returns the age of the person
    }

    // Setter method for the 'age' field
    public void setAge(int age) {
        this.age = age;  // Sets the age of the person
    }

    // Getter method for the 'email' field
    public String getEmail() {
        return email;  // Returns the email of the person
    }

    // Setter method for the 'email' field
    public void setEmail(String email) {
        this.email = email;  // Sets the email of the person
    }
}
```

### **XMLHandler.java**
```java
package com.example;

import jakarta.xml.bind.*;  // Import JAXB classes for marshalling and unmarshalling
import javax.xml.validation.Schema;  // Import Schema for XML validation
import javax.xml.validation.SchemaFactory;  // Import SchemaFactory to create Schema objects
import javax.xml.XMLConstants;  // Import XMLConstants for schema constants
import java.io.File;  // Import File class for file handling

public class XMLHandler {

    // Method to deserialize XML without validation
    public static Person deserializeWithoutValidation(String filePath) throws JAXBException {
        // Create a JAXB context for the Person class, which tells JAXB how to process the XML
        JAXBContext context = JAXBContext.newInstance(Person.class);

        // Create an unmarshaller object which will be used to convert the XML back to a Java object
        Unmarshaller unmarshaller = context.createUnmarshaller();

        // Unmarshal the XML file at the given filePath and cast the result to a Person object
        return (Person) unmarshaller.unmarshal(new File(filePath));
    }

    // Method to deserialize XML with validation using a given schema
    public static Person deserializeWithValidation(String filePath, String schemaPath) throws Exception {
        // Create a JAXB context for the Person class to handle the XML binding
        JAXBContext context = JAXBContext.newInstance(Person.class);

        // Create an unmarshaller object which will be used to convert XML into a Java object
        Unmarshaller unmarshaller = context.createUnmarshaller();

        // Create a SchemaFactory object to parse and create a Schema object for XML validation
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // Create a Schema object using the schema file provided by the schemaPath
        Schema schema = sf.newSchema(new File(schemaPath));

        // Set the schema for the unmarshaller to enforce XML validation
        unmarshaller.setSchema(schema);

        // Unmarshal the XML file at the given filePath, validate it against the schema,
        // and return the resulting Person object
        return (Person) unmarshaller.unmarshal(new File(filePath));
    }
}
```

### **Main.java**
```java
package com.example;

public class Main {
    public static void main(String[] args) {
        try {
            // Define the paths to the valid and invalid XML files, and the schema
            String validXML = "src/main/resources/person_valid.xml";
            String invalidXML = "src/main/resources/person_invalid.xml";
            String schemaPath = "src/main/resources/person.xsd";

            // Without validation - Deserialize the valid XML file without any validation
            Person person1 = XMLHandler.deserializeWithoutValidation(validXML);
            // Print the details of the person object after deserialization (including email)
            System.out.println("SUCCESS - validXML deserialized without validation: " +
                    person1.getName() + ", Age: " + person1.getAge() + ", Email: " + person1.getEmail());

            // With validation (valid XML) - Deserialize the valid XML file with validation using the schema
            Person person2 = XMLHandler.deserializeWithValidation(validXML, schemaPath);
            // Print the details of the person object after deserialization (including email)
            System.out.println("SUCCESS - validXML deserialized with validation: " +
                    person2.getName() + ", Age: " + person2.getAge() + ", Email: " + person2.getEmail());

            // With validation (invalid XML) - Attempt to deserialize the invalid XML with validation
            try {
                Person person3 = XMLHandler.deserializeWithValidation(invalidXML, schemaPath);
            } catch (Exception e) {
                // Print error message when deserialization fails due to invalid XML
                System.err.println("FAILURE - Failed to deserialize invalid XML: " + e.getMessage());
            }
        } catch (Exception e) {
            // Handle and print any general exceptions during execution
            e.printStackTrace();
        }
    }
}
```

## **4. XML & XSD Files**

### **Valid XML (`person_valid.xml`)**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<person>
    <name>John Doe</name>
    <age>30</age>
    <email>john.doe@example.com</email>  <!-- Only one email, passes XSD validation -->
</person>
```

### **Invalid XML (`person_invalid.xml`)**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<person>
    <name>Jane Doe</name>
    <age>30</age>
    <!-- Missing <email> element, which is required by the XSD because minOccurs="1" -->
</person>
```

### **XSD Schema (`person.xsd`)**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
    <xs:element name="person">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="name" type="xs:string" minOccurs="1"/>
                <xs:element name="age" type="xs:int" minOccurs="1"/>
                <xs:element name="email" type="xs:string" minOccurs="1"/> <!-- Required field not in Java class -->
            </xs:sequence>
        </xs:complexType>
    </xs:element>
</xs:schema>
```

## **5. Running the Application**
1. **Ensure Maven is configured**.
2. **Run `Main.java`**.
3. The output should show:
   - Successful deserialization without validation.
   - Successful deserialization with validation.
   - A failure message for invalid XML due to schema violation.

This lesson demonstrates XML deserialization with and without XSD validation, highlighting the importance of schema compliance.

## XML Validation with XSD in Java

When deserializing XML data into Java objects, it's important to distinguish between two types of validation: **class-level validation** and **XSD (schema)-level validation**. The `person_invalid.xml` file demonstrates how the **XSD validation** enforces constraints like `minOccurs="1"`, which requires the presence of the `email` field. 

In the example below, `person_invalid.xml` will fail validation because the `email` element is missing, while `person_valid.xml` passes the validation. This behavior is driven by the XSD schema, not the Java class itself.

### Validation Breakdown

- **Class-level validation** occurs when fields in the XML file do not match the Java class structure. If the XML contains fields not represented in the Java class (e.g. missing `email`), it can result in deserialization errors at the mapping level.
- **XSD validation** is enforced using the XML Schema Definition (`person.xsd`) which specifies constraints such as the minimum occurrences (`minOccurs`) of elements. In the case of `person_invalid.xml`, the absence of the `email` field leads to a validation failure because the XSD requires it to be present with `minOccurs="1"`.

### Example of XSD Validation Failure

The following table summarizes the validation behavior:

| **XML File**              | **Description**                                          | **Validation Type**   | **Outcome**            |
|---------------------------|----------------------------------------------------------|-----------------------|------------------------|
| `person_valid.xml`         | XML file with all required elements (`name`, `age`, `email`). | XSD validation         | Passes validation      |
| `person_invalid.xml`       | XML file missing the required `email` element.            | XSD validation         | Fails due to missing `email` (minOccurs="1") |
| **Java Class Mapping**     | **Class-Level Mapping**: The `Person` class includes `name`, `age`, and `email`. | **Mapping level**      | N/A (since XSD validation fails before mapping level validation) |

### Key Points:
- The **XSD schema** enforces that the `email` field is required in the XML (`minOccurs="1"`).
- Even though the Java class (`Person.java`) does not require the `email` field explicitly, the XSD enforces its presence in the XML file.
- **XSD validation** ensures that the XML data conforms to the structure and constraints defined in the schema, such as required fields and data types.
- **Java deserialization** proceeds only if the XML is valid according to both the class and the schema. If the schema fails (as in the case of `person_invalid.xml`), the deserialization is aborted, and an exception is thrown.

In summary, the validation enforced by the XSD ensures that the XML file follows a strict structure, which in turn affects whether it can be successfully deserialized into a Java object. If the schema is violated, such as in the case of missing required fields like `email`, the process will fail before the data can be mapped to the `Person` class.



# **Lesson: Serializing Java Objects to XML**

## **1. Introduction to Serialization**
Serialization is the process of converting a Java object into a format that can be easily stored or transmitted. In this lesson, we focus on **XML Serialization**, which converts Java objects into XML format using **JAXB (Java Architecture for XML Binding)**.

### **What is JAXB?**
JAXB allows Java developers to map Java objects to XML structures using annotations. It provides:
- **Marshaller**: Converts Java objects into XML (Serialization).
- **Unmarshaller**: Converts XML into Java objects (Deserialization).

### **What is a Marshaller?**
A **Marshaller** in JAXB is responsible for converting (or **serializing**) Java objects into XML format. It uses annotations in Java classes to determine how objects should be represented in XML.

### **Understanding the `Person` Class**
The `Person` class represents an entity with two attributes: `name` (a string) and `age` (an integer). These attributes are mapped to XML elements so they can be serialized into XML format.

### **Classes Needed for XML Serialization**
To successfully serialize Java objects into XML, we need:
1. **Model Class (`Person.java`)**: Defines the structure of the Java object.
2. **XML Handler (`XMLHandler.java`)**: Contains logic for serialization using JAXB.
3. **Main Class (`Main.java`)**: Runs the serialization process.
4. **Generated XML Files (`person.xml`)**: Contain the serialized XML data.

### **Visual Representation**
#### **Serialization (Java Object → XML)**
```
Java Object -> JAXB Marshaller -> XML Document (File, String, Stream)
```

## **2. Setting Up a Mavenized Java Project in IntelliJ (JDK 21)**

### **Step 1: Create a New Java Project**
1. Open **IntelliJ IDEA**.
2. Click on **New Project**.
3. Select **Java** (not a Maven archetype).
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
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>xmlserialization</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- JAXB API -->
        <dependency>
            <groupId>jakarta.xml.bind</groupId>
            <artifactId>jakarta.xml.bind-api</artifactId>
            <version>4.0.0</version>
            <!--
            The jakarta.xml.bind-api dependency provides the Jakarta XML Binding (JAXB) API. 
            JAXB allows for mapping between XML representations and Java objects (serialization and deserialization).
            This API provides classes and annotations to support the marshalling (serialization) and unmarshalling (deserialization) 
            of Java objects into XML and vice versa.
            -->
        </dependency>

        <!-- JAXB Runtime -->
        <dependency>
            <groupId>org.glassfish.jaxb</groupId>
            <artifactId>jaxb-runtime</artifactId>
            <version>4.0.0</version>
            <!--
            The jaxb-runtime dependency is the runtime implementation of the JAXB API. 
            It contains the necessary libraries for performing the actual serialization (marshalling) 
            and deserialization (unmarshalling) processes of Java objects to XML and back. 
            Without the runtime, the API cannot function properly.
            -->
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <!-- Configures the Maven Compiler Plugin to use Java 21 for source and target compatibility. -->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>21</source>  <!-- Sets the source (Java version used for compiling) to Java 21 -->
                    <target>21</target>  <!-- Sets the target (bytecode version) to Java 21 -->
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

## **3. Project Directory Structure**
```
xmlserialization/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/
│   │   │   │   ├── Main.java
│   │   │   │   ├── Person.java
│   │   │   │   ├── XMLHandler.java
│   │   ├── resources/
│── pom.xml
```

## **4. Java Classes**

### **Person.java**
```java
package com.example;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement  // Marks this class as an XML root element
public class Person {
    private String name;
    private int age;

    public Person() {}  // Default constructor required by JAXB
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @XmlElement
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
```

### **XMLHandler.java**
```java
package com.example;

import jakarta.xml.bind.JAXBContext; // Import JAXBContext to work with the JAXB API for marshalling and unmarshalling
import jakarta.xml.bind.JAXBException; // Import JAXBException to handle errors related to XML processing
import jakarta.xml.bind.Marshaller; // Import Marshaller for converting Java objects to XML (serialization)
import java.io.File; // Import File to specify where to store the serialized XML file

public class XMLHandler {

    // This method serializes a Person object into an XML file at the specified file path
    public static void serializeToXML(Person person, String filePath) throws JAXBException {
        // Create a JAXBContext for the Person class to enable marshalling (serialization) operations
        JAXBContext context = JAXBContext.newInstance(Person.class);
        
        // Create a Marshaller object from the JAXBContext, which will handle the actual serialization
        Marshaller marshaller = context.createMarshaller();
        
        // Set a property to format the output XML (to make it more human-readable)
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        // Marshal the Person object into an XML file at the specified file path
        marshaller.marshal(person, new File(filePath));
    }
}
```

### **Main.java**
```java
package com.example;

public class Main {
    public static void main(String[] args) {
        try {
            // Create a new Person object with name "John Doe" and age 30
            Person person = new Person("John Doe", 30);
            
            // Define the file path where the XML will be saved
            String filePath = "src/main/resources/person.xml";

            // Call the XMLHandler's serializeToXML method to serialize the Person object to the specified file
            XMLHandler.serializeToXML(person, filePath);
            
            // Print a success message indicating that the Person object was successfully serialized to XML
            System.out.println("SUCCESS - Serialized Person object to XML: " + filePath);
        } catch (Exception e) {
            // If an error occurs during the serialization process, print the error message
            System.err.println("FAILURE - Error during serialization: " + e.getMessage());
        }
    }
}
```

## **5. Managing `person.xml`**
Before running the application, ensure that `person.xml` does not already exist in `src/main/resources/`. If it exists, delete it to observe the file creation process. The program will generate and populate the file with the serialized data.

## **6. Running the Application in IntelliJ**
1. **Ensure Maven is configured** inside IntelliJ.
2. **Open `Main.java`** in IntelliJ.
3. Click the **Run** button (▶) at the top of IntelliJ.
4. The `person.xml` file should be generated in `src/main/resources/`.
5. Open `person.xml` to view the serialized output.

## **7. Summary**
- We used **JAXB** to serialize a `Person` object into XML.
- The **`Marshaller`** converted the Java object into XML format.
- Running `Main.java` generated an XML file containing the serialized object.

This lesson provided a hands-on approach to **serialization using JAXB**, a crucial concept for handling XML data in Java.

