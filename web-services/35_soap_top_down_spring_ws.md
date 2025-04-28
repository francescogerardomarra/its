## SOAP Web Services: Top-Down Approach in Spring WS

### Introduction to the Top-Down Approach
The **Top-Down** approach in SOAP web services starts with defining a **WSDL (Web Services Description Language)** file. The server then generates the necessary Java code from this contract. This approach is useful when integrating with a predefined WSDL.

#### **Difference from JAX-WS**
Unlike **JAX-WS**, where a WSDL can be generated automatically from Java code, in **Spring WS**, we must explicitly define an **XSD schema** for the service.

---

## 1. Creating a SOAP Web Service in Spring WS
### **Step 1: Setup a Spring Boot Project**

#### **Required Dependencies**
Add the following dependencies to your `pom.xml`:

```xml
<dependencies>
    <!-- Spring Boot Starter for Web Services -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web-services</artifactId>
    </dependency>

    <!-- JAXB API for marshalling/unmarshalling -->
    <dependency>
        <groupId>javax.xml.bind</groupId>
        <artifactId>jaxb-api</artifactId>
    </dependency>
</dependencies>
```

### **Step 2: Define the WSDL Schema (XSD File)**
We use an **XSD file** to define the structure of our WSDL. This step was **not required in JAX-WS**, where the WSDL was generated automatically from Java classes.

#### **`calculator.xsd`** (placed in `src/main/resources`):
```xml
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           xmlns:tns="http://example.com/calculator"
           targetNamespace="http://example.com/calculator"
           elementFormDefault="qualified">

    <xs:element name="addRequest">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="a" type="xs:int"/>
                <xs:element name="b" type="xs:int"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>

    <xs:element name="addResponse">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="result" type="xs:int"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>
</xs:schema>
```

### **Step 3: Configure Spring WS**
Create a configuration class to define the endpoint mappings.

#### **`WebServiceConfig.java`**
```java
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet() {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean
    public XsdSchema calculatorSchema() {
        return new SimpleXsdSchema(new org.springframework.core.io.ClassPathResource("calculator.xsd"));
    }
}
```

### **Step 4: Implement the Service**
#### **`CalculatorService.java`**
```java
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public int add(int a, int b) {
        return a + b;
    }
}
```

### **Step 5: Create the SOAP Endpoint**
#### **`CalculatorEndpoint.java`**
```java
import com.example.calculator.AddRequest;
import com.example.calculator.AddResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CalculatorEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/calculator";

    private final CalculatorService calculatorService;

    public CalculatorEndpoint(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addRequest")
    @ResponsePayload
    public AddResponse add(@RequestPayload AddRequest request) {
        AddResponse response = new AddResponse();
        response.setResult(calculatorService.add(request.getA(), request.getB()));
        return response;
    }
}
```

---

## 2. Obtaining the WSDL
Run the Spring Boot application in **IntelliJ**.

Once running, access the WSDL at:

```
http://localhost:8080/ws/calculator.wsdl
```

### **Example WSDL Output**
```xml
<definitions xmlns="http://schemas.xmlsoap.org/wsdl/" xmlns:tns="http://example.com/calculator"
             xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
             targetNamespace="http://example.com/calculator">
    <message name="addRequest">
        <part name="a" type="xsd:int"/>
        <part name="b" type="xsd:int"/>
    </message>
    <message name="addResponse">
        <part name="result" type="xsd:int"/>
    </message>
    <portType name="CalculatorPort">
        <operation name="add">
            <input message="tns:addRequest"/>
            <output message="tns:addResponse"/>
        </operation>
    </portType>
</definitions>
```

---

## 3. Generating a SOAP Client
This time you need to use the **`wsimport` command-line tool** to generate client classes.

Run this command:
```sh
wsimport -keep -p com.example.calculator -d src/main/java http://localhost:8080/ws/calculator.wsdl
```

### **Using the Generated Client Code**
The client will call the SOAP web service at:
```
http://localhost:8080/ws
```

#### **`CalculatorClient.java`**
```java
import com.example.calculator.CalculatorService;
import com.example.calculator.CalculatorPortType;

public class CalculatorClient {
    public static void main(String[] args) {
        CalculatorService service = new CalculatorService();
        CalculatorPortType port = service.getCalculatorPort();
        
        int result = port.add(5, 3);
        System.out.println("Result: " + result);
    }
}
```

This code uses the generated stub to **call the SOAP web service** and print the result.

---

## Conclusion
Key differences from **JAX-WS**:
- **Spring WS requires an XSD schema**, while JAX-WS auto-generates WSDL.
- **Maven plugin cannot be used for generating client code**; instead, `wsimport` is required.

This guide demonstrated the **top-down** approach in Spring WS, highlighting the key distinctions from JAX-WS.
