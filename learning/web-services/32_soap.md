# SOAP Web Services

## 1. A Non-RPC XML-Based Web Service Protocol
SOAP (Simple Object Access Protocol) is a protocol used for exchanging structured information in the implementation of web services. Unlike RESTful services, which use lightweight JSON, SOAP is XML-based and follows a strict messaging format. 

SOAP is mostly intended to be non-RPC.

Key Characteristics:

- Uses XML as its message format
- Can be transported over multiple protocols (HTTP, SMTP, etc.)
- Highly extensible with security and reliability features
- Commonly used in enterprise environments

### XML Structure Considerations
XML is known for being verbose and large compared to other formats like JSON. This is because XML requires opening and closing tags for each element, leading to increased data size. Additionally, XML has a strictly unflexible structure, meaning every element must conform to a predefined schema, which ensures strong data validation but can make modifications more complex.

## 2. SOAP Message

SOAP messages:

- Play a crucial role in the operation of SOAP web services.
- Define a standard protocol for exchanging structured information between web services and clients over the internet.
- Facilitate communication regardless of the underlying platform or programming language.
- Are always formatted in XML, providing a standardized structure.
- This consistency allows for easy parsing and understanding of the messages by different systems.

SOAP messages are XML documents that follow a specific format. A basic SOAP message consists of:

- **Envelope**: The root element that defines the start and end of the message. This element is part of the SOAP namespace and is not custom.
- **Header** (Optional): Contains metadata about the message.
- **Body**: Contains the actual request or response data. The XML within the body does not come from a predefined schema (namespace) but follows a strict structure agreed upon in a WSDL (Web Service Descriptor) file. The request specifically conveys the operation to be performed.
- **Fault** (Optional): Provides error handling information.

By using XML and adhering to a strict message format, SOAP messages enable **interoperability** among disparate systems and technologies, making it easier to integrate services.

### Example SOAP Request Message:
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    <soap:Header>
        <auth>API_KEY</auth>
    </soap:Header>
    <soap:Body>
        <GetUserRequest>
            <UserID>123</UserID>
        </GetUserRequest>
    </soap:Body>
</soap:Envelope>
```

The provided SOAP message is a **request** message:

1. **SOAP Envelope**:
    - The `<soap:Envelope>` element, along with its namespace, is a standard part of SOAP and is not custom.

2. **SOAP Body**:
    - The `<soap:Body>` contains application-specific XML that follows the structure defined by the WSDL.
    - The `<GetUserRequest>` element indicates that this message is requesting user information, and its structure is strictly defined by the web service.
    - This request conveys the operation to be performed, in this case, retrieving user details.
    - Although SOAP messages are typically sent via an HTTP POST request, the operation being requested is specified within the SOAP body, not the HTTP method.

3. **Structure**:
    - It includes a header for authentication (`<auth>API_KEY</auth>`), which is typical for requests requiring API keys or tokens.

### Example SOAP Response Message:
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    <soap:Header>
        <auth>API_KEY</auth>
    </soap:Header>
    <soap:Body>
        <GetUserResponse>
            <User>
                <UserID>123</UserID>
                <UserName>JohnDoe</UserName>
                <Email>johndoe@example.com</Email>
                <Status>Active</Status>
            </User>
        </GetUserResponse>
    </soap:Body>
</soap:Envelope>
```

The provided SOAP message is a **response** message:

1. **SOAP Envelope**:
    - As with the request, the `<soap:Envelope>` is part of the SOAP standard and not custom.

2. **SOAP Body**:
    - The `<soap:Body>` contains a `<GetUserResponse>` element, which is structured based on the web service definition in the WSDL.
    - This response conveys the results of the requested operation (`GetUserRequest`) and the data returned (`User` details).

3. **Content Structure**:
    - The `<User>` element contains various details about the user, such as:
        - **UserID**: Confirms the ID of the user that was requested.
        - **UserName**: Provides the name of the user.
        - **Email**: Contains the email address of the user.
        - **Status**: Indicates the current status of the user (e.g. Active).
    - This information is typically returned after processing a request, making it clear that this message serves to fulfill the request.

4. **Consistency with Request**:
    - The header includes the same authentication information (`<auth>API_KEY</auth>`), maintaining context and security across the interaction.

## 3. wsdl (Web Services Description Language)
A .wsdl file is an XML-based document that describes a SOAP web service. 

**It acts as the schema for SOAP messages and the operations behind them.**

It defines:

- the available operations (methods)
- the messages exchanged
- the binding to transport protocols
- the service location (URL)

Besides defining services and bindings, a WSDL file contains:

- **types**: Defines the data types used by the service (often using XML Schema Definition - XSD).
- **message**: Defines the structure of input and output messages for operations.
- **portType**: Defines a set of operations supported by the web service.

SOAP messages need to be bound to a transport protocol with a **binding** defined within a .wsdl file.

The most common binding is over HTTP using SOAP 1.1 or 1.2, but SOAP can also be bound to SMTP or other protocols.

A SOAP binding defines:

- the transport protocol
- encoding rules
- how messages are mapped to operations

### .wsdl VS .xsd
A .wsdl (Web Services Description Language) document is similar to an .xsd (XML Schema Definition) in several ways, particularly in defining the structure of XML messages:

1. **Structure Definition**:
    - **WSDL**: Describes the operations of a web service, including the input and output messages and their formats. It often utilizes XML Schema to define the data types used in these messages.
    - **XSD**: Specifically defines the structure and data types of XML documents, ensuring that the XML adheres to specified rules.

2. **Target Namespace**:
    - Both WSDL and XSD can include a `targetNamespace` attribute. This helps uniquely identify the elements defined within the document, avoiding naming conflicts in larger systems.

3. **Purpose**:
    - **WSDL**: Focuses on describing web services, detailing service endpoints, protocols, and the message formats involved. In this sense, **it acts as the schema for SOAP messages and the operations behind them**, providing a comprehensive definition of how the web service operates.
    - **XSD**: Primarily used to define the schema for XML documents, specifying how elements and attributes can be structured and what data types they can contain.

### WSDL example
````xml
<?xml version="1.0" encoding="UTF-8"?>

<!-- WSDL (Web Services Description Language) -->
<!-- WSDL is an XML-based document that describes a SOAP web service. It defines: -->
<!-- - The available operations (methods) -->
<!-- - The messages exchanged -->
<!-- - The binding to transport protocols -->
<!-- - The service location (URL) -->

<!-- Root element: <definitions> -->
<!-- This is the main container for the WSDL document. It defines the namespace and contains all service definitions. -->
<definitions xmlns="http://schemas.xmlsoap.org/wsdl/"
             xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
             xmlns:xs="http://www.w3.org/2001/XMLSchema"
             xmlns:tns="http://example.com/UserService"
             targetNamespace="http://example.com/UserService">

    <!-- Types -->
    <!-- Defines the data types used by the service (often using XML Schema Definition - XSD). -->
    <types>
        <xs:schema targetNamespace="http://example.com/UserService">
            <!-- Define a simple integer element named UserID -->
            <xs:element name="UserID" type="xs:int"/>

            <!-- Define the structure for the GetUserRequest message -->
            <xs:element name="GetUserRequest">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="UserID" type="xs:int"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>

            <!-- Define the structure for the GetUserResponse message -->
            <xs:element name="GetUserResponse">
                <xs:complexType>
                    <xs:sequence>
                        <xs:element name="UserName" type="xs:string"/>
                        <xs:element name="UserEmail" type="xs:string"/>
                    </xs:sequence>
                </xs:complexType>
            </xs:element>
        </xs:schema>
    </types>

    <!-- Messages -->
    <!-- Defines the structure of input and output messages for operations. -->
    <message name="GetUserRequest">
        <!-- The message references the GetUserRequest element defined in the schema -->
        <part name="parameters" element="tns:GetUserRequest"/>
    </message>
    <message name="GetUserResponse">
        <!-- The message references the GetUserResponse element defined in the schema -->
        <part name="parameters" element="tns:GetUserResponse"/>
    </message>

    <!-- PortType -->
    <!-- Defines a set of operations supported by the web service. -->
    <portType name="UserPortType">
        <operation name="GetUser">
            <!-- Input and output messages for the operation -->
            <input message="tns:GetUserRequest"/>
            <output message="tns:GetUserResponse"/>
        </operation>
    </portType>

    <!-- Binding -->
    <!-- Defines how SOAP messages are bound to transport protocols (e.g. HTTP). -->
    <binding name="UserBinding" type="tns:UserPortType">
        <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
        <operation name="GetUser">
            <!-- Defines the SOAP action for this operation -->
            <soap:operation soapAction="http://example.com/UserService/GetUser"/>
            <input>
                <!-- Specifies that the SOAP body should use literal encoding -->
                <soap:body use="literal"/>
            </input>
            <output>
                <!-- Specifies that the SOAP body should use literal encoding -->
                <soap:body use="literal"/>
            </output>
        </operation>
    </binding>

    <!-- Service -->
    <!-- Specifies the service location (URL) and the binding to use. -->
    <service name="UserService">
        <port name="UserPort" binding="tns:UserBinding">
            <!-- Specifies the actual endpoint where the service is hosted -->
            <soap:address location="http://example.com/UserService"/>
        </port>
    </service>
</definitions>
````

## 4. SOAP in Java Development
SOAP is widely supported in Java through libraries and frameworks like:
- **JAX-WS (Java API for XML Web Services)**: A Java standard API for building and consuming SOAP web services.
- **JAXB (Java Architecture for XML Binding)**: Used for converting Java objects to XML and vice versa, simplifying data serialization/deserialization.
- **Spring Web Services**: Provides support for SOAP-based web services in Spring applications.

### JAX-WS vs. JAXB
- **JAX-WS** focuses on web service communication using SOAP.
- **JAXB** is primarily used for object-to-XML mapping, ensuring seamless serialization and deserialization of Java objects.
- JAX-WS often works with JAXB to convert Java objects into XML when sending SOAP requests.

### JAX-WS vs. Spring Web Services
In addition to JAXB, JAX-WS and Spring Web Services (Spring-WS) are both popular choices for creating SOAP-based web services in Java, but they differ in their approach:

| Feature              | `@Endpoint` (Spring-WS)                                                  | `@WebService` (JAX-WS)                                 |
|----------------------|--------------------------------------------------------------------------|--------------------------------------------------------|
| **Technology**       | Part of **Spring Web Services**                                          | Part of **JAX-WS** (Java EE standard)                  |
| **Protocol**         | SOAP-based (Spring-WS implementation)                                    | SOAP-based (JAX-WS implementation)                     |
| **Usage**            | Used in Spring for building SOAP web services                            | Standard Java EE annotation for SOAP services          |
| **Annotation**       | `@Endpoint`                                                              | `@WebService`                                          |
| **Configuration**    | Custom Spring configuration for SOAP endpoints                           | JAX-WS runtime usually handles configuration           |
| **Message Handling** | Uses `@PayloadRoot`, `@ResponsePayload`, etc.                            | Direct methods annotated with `@WebMethod` (if needed) |
| **WSDL Generation**  | WSDL is not automatically generated; you need to configure it explicitly | WSDL is often generated automatically                  |

In summary:
- If you're working within the **Spring ecosystem** and building SOAP web services, you’ll use `@Endpoint` and other **Spring Web Services** annotations.
- If you're using **standard JAX-WS** for SOAP web services, you would use the `@WebService` annotation.

### Example: Java JAX-WS Web Service Endpoint
```java
@WebService
public class UserService {
    @WebMethod
    public String getUser(int userId) {
        return "User " + userId;
    }
}
```

### Example: Spring Web Services (SOAP) Endpoint
```java
@Endpoint
public class UserServiceEndpoint {

    private static final String NAMESPACE_URI = "http://example.com";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        response.setUserName("User " + request.getUserId());
        return response;
    }
}
```

## 5. Top-Down and Bottom-Up Approaches in WSDL-Based Web Services

Web services can be implemented using two main approaches: **Top-Down** and **Bottom-Up**. These approaches define the sequence in which the WSDL and service implementation are developed.

### Top-Down Approach
The **Top-Down** approach starts with a WSDL file that defines the web service contract. The service implementation is then generated based on this contract.

#### Steps in the Top-Down Approach:
1. **Define the WSDL**: The service contract is written first using WSDL.
2. **Generate Code from WSDL**: A tool is used to generate server-side code (stubs or skeletons) based on the WSDL.
3. **Implement the Business Logic**: The developer writes the actual implementation of the web service methods.

#### Tools for Top-Down Approach in Java:
- **Apache CXF** (`wsdl2java` command)
- **JAX-WS (Java API for XML Web Services)** (`wsimport` tool)

#### When to Use Top-Down:
- When integrating with an **existing** web service.
- When a web service client needs to interact with a server based on a predefined contract.

### Bottom-Up Approach
The **Bottom-Up** approach starts with the implementation code, and the WSDL is generated automatically from it.

#### Steps in the Bottom-Up Approach:
1. **Write the Java Code**: The service implementation is written first.
2. **Generate WSDL from Code**: A tool is used to generate the WSDL from the Java class.
3. **Expose the Web Service**: The generated WSDL is shared with other clients who need to consume the service.

#### Tools for Bottom-Up Approach in Java:
- **JAX-WS (`wsgen` tool)**
- **Spring Boot with Spring Web Services**

#### When to Use Bottom-Up:
- When creating a **new** web service and the contract can be generated dynamically.
- When a web service provider needs to communicate the contract (WSDL) to other web services.

### Client-Server Integration Scenarios
- When a **web service client** needs to interact with another web service, it follows the **Top-Down** approach, generating the client code from the WSDL.
- When a **web service provider** needs to communicate its contract to another service, it follows the **Bottom-Up** approach, generating the WSDL from the implementation.

## 6. Summary
SOAP is a robust XML-based protocol for web services, often used in enterprise applications where security, reliability, and extensibility are crucial. The key components include:
- **SOAP messages**: Structured XML messages for communication.
- **WSDL**: Describes web service operations, including types, messages, and bindings.
- **Binding**: Defines transport and encoding details.
- **Java support**: JAX-WS for web service handling, JAXB for XML serialization.

When developing SOAP web services, two primary approaches are employed:
- **Top-Down (WSDL-First)**: The service is designed starting from the WSDL, allowing for a clear contract that clients must adhere to.
- **Bottom-Up**: The service is implemented first, and the WSDL is generated from the existing code, describing how clients can interact with the service.

Both approaches play a crucial role in how web services are developed and integrated, influencing the relationship between the service and its clients.

