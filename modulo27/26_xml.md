# Introduction to XML

## 1. What is XML?

### Definition and Purpose
XML (eXtensible Markup Language) is a markup language designed to store and transport data in a structured and human-readable format.

### Key Characteristics:
- **Self-descriptive**: Data and structure are stored together.
- **Platform-independent**: Can be used across different platforms and technologies.
- **Extensible**: Custom tags can be created as needed.

---

## 2. Basic Syntax of XML

XML syntax rules are simple yet strict. Proper adherence to these rules ensures that XML documents are well-formed and can be processed by any XML parser. Let’s break it down step-by-step:

### 2.1 XML Declaration

The XML declaration is an optional line at the start of an XML document. It specifies:
- The version of XML being used.
- The character encoding.

Example:
```xml
<?xml version="1.0" encoding="UTF-8"?>
```

- **`version`**: Specifies the XML version (commonly `1.0` or `1.1`).
- **`encoding`**: Defines the character set. UTF-8 is the default and widely used.

The **XML declaration** `<?xml version="1.0" encoding="UTF-8"?>` **does not require an ending tag <\>**. 

Here's why:

- The XML declaration is not an element or a tag, it's an **instruction** to the XML parser.
- It's used to specify important details, like the XML version and character encoding.
- It doesn't have a start or end tag because it's just a single processing instruction, meant to be placed at the very beginning of the document. 
- XML declarations are self-contained, meaning the `<?xml ... ?>` part is all you need. There's no need for a corresponding closing tag like you'd see with elements (e.g. `<tag></tag>`).


The XML declaration is a single statement and doesn't have an "end" tag, which is a key characteristic of processing instructions in XML.

---

### 2.2 Elements

#### What are Elements?

Elements are the fundamental building blocks of an XML document. Each element has:

1. A **start tag**.
2. An **end tag**.
3. Optional **content** in between.

Example:

```xml
<book>
  <title>XML Basics</title>
  <author>Jane Doe</author>
</book>
```

Here:

- `<book>` is the parent element.
- `<title>` and `<author>` are child elements.

Elements are suitable for storing larger or hierarchical data.

Example:

```xml
<book>
  <id>1</id>
  <category>Fiction</category>
  <title>XML Adventures</title>
</book>
```

#### Nesting of Elements


Elements can be nested inside other elements, but they must always be properly closed and not overlap.
**Correct Nesting:**

```xml
<library>
  <book>
    <title>XML Guide</title>
  </book>
</library>
```

**Incorrect Nesting (Invalid):**

```xml
<library>
  <book>
    <title>XML Guide</library>
  </book>
```

Elements in an XML document are arranged hierarchically, forming parent-child relationships.

Example:

```xml
<book>
  <title>XML Guide</title>
  <author>Jane Doe</author>
</book>
```

Here:

- `<book>` is the parent element.
- `<title>` and `<author>` are child elements of `<book>`.


#### Root Element


Every XML document must have a single root element that contains all other elements. This is also known as the "document element."

Example:

```xml
<library>
  <book>
    <title>XML Basics</title>
  </book>
</library>
```

Here, `<library>` is the root element.

Rules:
1. The root element must be unique and occur only once.
2. It should enclose all other elements.

---

### 2.3 Attributes

#### What are Attributes?

Attributes provide additional information about an element. They are defined inside the start tag as name-value pairs.

Example:

```xml
<book category="Programming">
  <title>XML Basics</title>
  <author>Jane Doe</author>
</book>
```

Here:

- `category` is an attribute of the `<book>` element, and its value is `"Programming"`.

Attributes provide metadata about an element.

Example:

```xml
<book id="1" category="Fiction">
  <title>XML Adventures</title>
</book>
```

#### Rules for Attributes:

1. Attribute names must be unique within an element.
   ```xml
   <!-- Valid -->
   <book id="101" category="Programming"></book>

   <!-- Invalid -->
   <book id="101" id="102"></book>
   ```
2. Attribute values must be enclosed in quotes (either single `'` or double `"`).
   ```xml
   <!-- Valid -->
   <book id="101"></book>

   <!-- Invalid -->
   <book id=101></book>
   ```

#### Comparison

XML allows you to store data either as attributes or elements. Choosing between the two depends on the use case:

| Use Case              | Attributes                      | Elements                 |
|-----------------------|---------------------------------|--------------------------|
| Small metadata        | Best suited                    | Not ideal               |
| Complex or nested data| Not suitable                   | Best suited             |
| Readability           | Harder to read in bulk         | Easier to understand    |

---

### 2.4 Comments

Comments in XML are used to include notes or explanations for readers without affecting the document’s functionality. They are enclosed in `<!--` and `-->`.

Example:

```xml
<!-- This is a comment -->
<book>
  <title>XML Basics</title>
</book>
```

**Rules for Comments:**

1. Comments cannot contain `--` within them.
   ```xml
   <!-- Invalid comment -- -->
   ```
2. Comments are ignored by parsers.

---

### 2.5 Handling Text Data in XML

When dealing with text in XML, it's important to differentiate between **CDATA (Character Data) Sections** and **PCDATA (Parsed Character Data) Sections**.

#### 2.5.1 CDATA Sections

CDATA sections allow you to include content that should not be treated as XML markup.

Example:

```xml
<message>
  <![CDATA[
    This is a CDATA section with <b>unparsed</b> text.
  ]]>
</message>
```

- Inside `<![CDATA[ ... ]]>`, everything is treated as raw text and not parsed as XML.
- Useful for embedding HTML, JavaScript, or other non-XML data.
- The closing sequence `]]>` must not appear inside a CDATA section.

##### Use Case: Embedding JavaScript Inside XML

Consider an XML-based configuration file that needs to store JavaScript code. Without CDATA, the `<` and `>` characters in the script might be misinterpreted as XML tags, causing parsing errors.

**Without CDATA (Incorrect & Causes Errors):**
```xml
<config>
    <script>
        function showAlert() {
            alert("Hello, World!");
        }
    </script>
</config>
```

In this case, the XML parser will try to interpret `<script>` and `</script>` as XML tags, which is not the intended behavior.

**With CDATA (Correct & Works as Intended):**
```xml
<config>
    <script>
        <![CDATA[
        function showAlert() {
            alert("Hello, World!");
        }
        ]]>
    </script>
</config>
```

- The JavaScript code inside the `<![CDATA[ ... ]]>` section is treated as raw text.
- The XML parser does not attempt to interpret it as XML, preventing syntax errors.
- This is particularly useful in cases where an XML file needs to store code snippets or other content that contains characters that could be misinterpreted as XML markup.

#### 2.5.2 PCDATA Sections

PCDATA (Parsed Character Data) refers to text that **is** processed by the XML parser. PCDATA is the default type of text in XML, so you won't see `PCDATA` explicitly used as a keyword.

Example:

```xml
<message>
  This is a PCDATA section with <b>parsed</b> text.
</message>
```

- The text content inside the XML element is parsed.
- Any XML markup (such as `<b>bold</b>`) is recognized and processed.
- Special characters like `<` and `&` must be escaped (e.g. `&lt;` for `<` and `&amp;` for `&`).
- Since PCDATA is the default behavior, you do not need to explicitly declare it.

By understanding the difference between CDATA and PCDATA, you can decide when to use raw text sections versus parsed text within your XML documents.

---

### 2.6 Empty Elements

Empty elements are elements with no content. They can be represented in two ways:

1. Using a start tag and end tag:
   ```xml
   <line></line>
   ```
2. Using a self-closing tag (preferred):
   ```xml
   <line />
   ```

---

### 2.7 Special Characters

Certain characters are reserved in XML and cannot be used directly. Instead, use their escape sequences.

| Character | Escape Sequence |
| --------- | --------------- |
| `<`       | `&lt;`          |
| `>`       | `&gt;`          |
| `&`       | `&amp;`         |
| `'`       | `&apos;`        |
| `"`       | `&quot;`        |

Example:

```xml
<note>
  <text>5 &lt; 10</text>
</note>
```

---

### 2.8 Case Sensitivity

XML is case-sensitive. Tags with different cases are treated as distinct elements.

Example:

```xml
<book>
  <Title>Case Matters</Title> <!-- Different from <title> -->
</book>
```

Here, `<Title>` and `<title>` are different.

---

### 2.9 White Space

XML preserves white space within text content but ignores it between tags.

Example:

```xml
<greeting>   Hello, World!   </greeting>
```

In this case, the white space around "Hello, World!" is preserved.

---

### 2.10 Complete Example

Here’s a complete XML document that applies the above concepts:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- This XML describes a bookstore -->

<bookstore>
  <book id="1" category="Fiction">
    <title>XML Adventures</title>
    <author>John Smith</author>
    <price currency="USD">19.99</price>
  </book>

  <book id="2" category="Non-Fiction">
    <title>Understanding XML</title>
    <author>Jane Doe</author>
    <price currency="EUR">29.99</price>
  </book>

  <magazine id="3">
    <title>XML Monthly</title>
    <edition><![CDATA[March <2025>]]></edition>
  </magazine>
</bookstore>
```

### 2.11 Well-formedness of XML Documents

An XML document is "well-formed" if it adheres to all syntax rules of XML.

#### Rules:
1. **Single Root Element:** Must have one and only one root element.
   ```xml
   <root>
     <child>Content</child>
   </root>
   ```
2. **Properly Closed Tags:** All tags must have a matching closing tag or be self-closing.
   ```xml
   <!-- Valid -->
   <note></note>
   <line />

   <!-- Invalid -->
   <note>
   <line>
   ```
3. **Case Sensitivity:** XML is case-sensitive.
   ```xml
   <!-- Valid -->
   <Title>XML</Title>

   <!-- Invalid -->
   <title>XML</Title>
   ```
4. **Attribute Quotation:** Attribute values must be enclosed in quotes.
   ```xml
   <element name="value"></element>
   ```
5. **Proper Nesting:** Tags must be properly nested.
   ```xml
   <!-- Valid -->
   <outer>
     <inner>Text</inner>
   </outer>

   <!-- Invalid -->
   <outer>
     <inner>Text</outer>
   </inner>
   ```

## 3. Validation with DTD

An internal DTD is defined within the XML file itself. It specifies the structure and rules directly inside the document.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE bookstore [
  <!ELEMENT bookstore (book, magazine*)>
  <!ELEMENT book (title, author, price)>
  <!ELEMENT magazine (title, edition)>
]>
<bookstore>
  <book>
    <title>XML Adventures</title>
    <author>John Smith</author>
    <price currency="USD">19.99</price>
  </book>
  <magazine>
    <title>XML Monthly</title>
    <edition><![CDATA[March <2025>]]></edition>
  </magazine>
</bookstore>
```

An external DTD is stored in a separate file and referenced in the XML document, promoting reusability.

**XML File:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE bookstore SYSTEM "bookstore.dtd">
<bookstore>
  <book>
    <title>XML Adventures</title>
    <author>John Smith</author>
    <price currency="USD">19.99</price>
  </book>
  <magazine>
    <title>XML Monthly</title>
    <edition><![CDATA[March <2025>]]></edition>
  </magazine>
</bookstore>
```

**External DTD File (bookstore.dtd):**
```xml
<!ELEMENT bookstore (book, magazine*)>
<!ELEMENT book (title, author, price)>
<!ELEMENT magazine (title, edition)>
```

An **external DTD** can also be hosted at a remote URL, allowing multiple XML documents to reference a single, shared DTD definition. This is particularly useful for standardization and consistency across multiple documents and systems.

A remote external DTD is specified in the `<!DOCTYPE>` declaration with a URL pointing to the DTD file.

```xml
<!DOCTYPE note SYSTEM "https://example.com/dtds/note.dtd">
<note>
  <to>John</to>
  <from>Jane</from>
  <heading>Reminder</heading>
  <body>Don’t forget the meeting!</body>
</note>
```

In this example:
- The `SYSTEM` keyword is used to specify an external DTD located at `https://example.com/dtds/note.dtd`.
- The XML processor retrieves the DTD from the given URL and applies its structure and validation rules to the XML document.

##### Advantages of Using a Remote External DTD:
- **Reusability:** Multiple XML documents can reference the same DTD, ensuring consistency.
- **Centralized Maintenance:** Updates to the DTD are reflected across all XML documents using it.
- **Validation Support:** External DTDs help in defining and enforcing document structure across distributed systems.

##### Considerations:
- **Availability:** The remote DTD must be accessible whenever an XML document referencing it is processed.
- **Performance:** Fetching a remote DTD may introduce latency.
- **Security:** Ensure the remote source is trusted to prevent XML External Entity (XXE) vulnerabilities.

## 4. Namespaces

A **namespace** helps to distinguish between elements that might have the same name but are intended for different purposes or come from different schemas. 

Namespaces are used to avoid naming conflicts by qualifying names with unique prefixes. This is particularly important when combining XML documents from different sources, as different documents may have elements or attributes with the same name but different meanings.

Namespaces are essential for ensuring that elements in an XML document are uniquely identified, especially in complex documents or in systems that merge data from various sources.

### Mimicking Two Namespaces with raw DTD

Although XML namespaces are typically declared using a special **xmlns** attribute, DTDs (Document Type Definitions) can also help structure XML documents while avoiding naming conflicts, which would **effectively mimick what namespaces are*.

DTD itself does not support namespaces in the way XML Schema does. However, you can achieve a similar effect by using prefixes in element names and defining them in the DTD.

Let's see an example:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE bookstore [
  <!ELEMENT bookstore (tech:book+, fiction:book+)>
  <!ELEMENT tech:book (tech:title, tech:author, tech:publisher)>
  <!ELEMENT tech:title (#PCDATA)>
  <!ELEMENT tech:author (#PCDATA)>
  <!ELEMENT tech:publisher (#PCDATA)>
  <!ELEMENT fiction:book (fiction:title, fiction:author, fiction:genre)>
  <!ELEMENT fiction:title (#PCDATA)>
  <!ELEMENT fiction:author (#PCDATA)>
  <!ELEMENT fiction:genre (#PCDATA)>
]>

<bookstore>
  <tech:book>
    <tech:title>XML and Beyond</tech:title>
    <tech:author>John Doe</tech:author>
    <tech:publisher>Tech Publishers</tech:publisher>
  </tech:book>
  
  <fiction:book>
    <fiction:title>Adventures in XML</fiction:title>
    <fiction:author>Jane Smith</fiction:author>
    <fiction:genre>Fantasy</fiction:genre>
  </fiction:book>
</bookstore>
```

#### Explanation:

- **Prefixed Element Names**: Elements such as `<tech:book>` and `<fiction:book>` differentiate between books in different categories (technical and fiction).
- **Distinct Nested Elements**: Technical books have a `<tech:publisher>`, while fiction books have a `<fiction:genre>`, reflecting their unique attributes.
- **Internal DTD**: The DTD enforces that `<bookstore>` contains books with different structures, ensuring proper categorization.
- **No Need for `xmlns` Attributes**: Instead of using XML namespace declarations (`xmlns`), this approach manually qualifies element names by prefixing them with a category.

### Namespaces with native xmlns attribute and DTD

In an XML document, the **root element** is the outermost element that encompasses all other elements. 

The **namespace declaration (`xmlns`)** is typically added to the **root element** to apply it to the entire document or a specific section of it. You typically define a namespace once in the root element (or a relevant parent element) and then use that namespace throughout the document. You do not need to explicitly repeat the namespace for every individual tag, as long as the tag is within the scope of the parent element where the namespace was declared.

#### Declaration
A namespace is declared using the `xmlns` attribute. You can associate a **prefix** with a namespace URI (Uniform Resource Identifier) to qualify element names. The prefix is used as a shorthand to reference the namespace. 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE bookstore [
  <!ELEMENT bookstore (book+)>
  <!ELEMENT book (title, author)>
  <!ELEMENT title (#PCDATA)>
  <!ELEMENT author (#PCDATA)>
]>

<bookstore xmlns:tech="http://example.com/tech">
  <tech:book>
    <tech:title>XML and Beyond</tech:title>
    <tech:author>John Doe</tech:author>
  </tech:book>
</bookstore>
```

`xmlns:tech="http://example.com/tech"` declares a namespace with the prefix `tech` and the URI `http://example.com/tech`.

**Important note**: The URL associated with the namespace (e.g. `http://example.com/tech`) is primarily a **unique identifier** for the namespace and **does not** have to point to an actual resource (such as a file or web page).

Two key points are:

1. **Placeholder/Definition Purpose**:
   - The URL is used to uniquely identify the namespace, so that elements in your XML document can be properly distinguished from elements in other namespaces.
   - It **doesn't have to** lead to a real file or resource, especially if you’re not using an external schema or if your schema is defined locally.
   - It can point to a non-existent or empty resource without affecting the validity of the XML document. 

2. **Not Checked for Existence**:
   - XML processors and parsers **do not** check if the URL actually exists or returns any content.
   - The URL is just a convention to differentiate the namespace and ensure that your XML elements are properly scoped. It helps avoid conflicts between elements with the same name but in different namespaces.

Elements like `tech:book` and `tech:title` now belong to the `http://example.com/tech` namespace, which is identified by the prefix `tech`.

The use of the `tech:` prefix differentiates these elements from others that might have the same name but belong to different namespaces.

#### Default Namespace

Sometimes, you may want all elements within a document to belong to the same namespace without needing to specify a prefix for each element.

The key part that introduces a default namespace is the declaration of the `xmlns` attribute without any prefix inside the the root element, that is:

```xml
xmlns="http://example.com/tech"
```

rather than:

```xml
xmlns:tech="http://example.com/tech"
```

The default namespace applies to all elements in the scope where it's declared, unless explicitly overridden by another namespace declaration. 

#### Examples

We will go through 4 examples:

- Tags belonging to no namespace
- Tags belonging to a default namespace
- Tags belonging to one namespace
- Tags belonging to two namespaces

**1. Tags belonging to no namespace**

Here, the `bookstore` root element does not declare any namespace. As a result, all elements within the `<bookstore>` element (like `<book>`, `<title>`, and `<author>`) do not belong to any namespace. They are considered to be in the default (no namespace) scope, meaning they don't have any namespace attached.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE bookstore SYSTEM "bookstore.dtd">

<bookstore>
  <book>
    <title>XML Basics</title>
    <author>Jane Smith</author>
  </book>
</bookstore>
```

**2. Tags belonging to a default namespace**

In this case, the `bookstore` root element declares a default namespace (`http://example.com/tech`) using the `xmlns` attribute without any prefix (plain `xmlns` rather than `xmlns:tech`). This default namespace is applied to all child elements under the `<bookstore>` element, such as `<book>`, `<title>`, and `<author>`, without the need to specify a prefix for each of them.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE bookstore SYSTEM "bookstore.dtd">

<bookstore xmlns="http://example.com/tech">
  <book>
    <title>XML Basics</title>
    <author>Jane Smith</author>
  </book>
</bookstore>
```

**3. Tags belonging to one namespace**

In this case, the `bookstore` root element declares a **non-default namespace** using the `xmlns:tech="http://example.com/tech"` attribute. The elements within `<bookstore>` (such as `<book>`, `<title>`, and `<author>`) are qualified with the `tech` prefix (e.g. `<tech:book>`, `<tech:title>`, and `<tech:author>`), meaning they belong to the `http://example.com/tech` namespace. The prefix `tech` is used to differentiate these elements from others that might use the same tag names in different namespaces.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE bookstore SYSTEM "bookstore.dtd">

<bookstore xmlns:tech="http://example.com/tech">
  <tech:book>
    <tech:title>XML Basics</tech:title>
    <tech:author>Jane Smith</tech:author>
  </tech:book>
</bookstore>
```

**4. Tags belonging to two namespaces**

If you want the tags to belong to two different namespaces using
`xmlns` and DTD, first off you need to ensure that the DTD can handle elements from different namespaces (in this case, `tech` and `fiction`):

```xml
<!-- bookstore.dtd -->
<!ELEMENT bookstore (tech:book | fiction:book)+>
  
<!-- Definition for the 'tech' namespace 'book' element -->
<!ELEMENT tech:book (tech:title, tech:author, tech:genre)>
<!ELEMENT tech:title (#PCDATA)>
<!ELEMENT tech:author (#PCDATA)>
<!ELEMENT tech:genre (#PCDATA)>

<!-- Definition for the 'fiction' namespace 'book' element -->
<!ELEMENT fiction:book (fiction:title, fiction:author, fiction:genre)>
<!ELEMENT fiction:title (#PCDATA)>
<!ELEMENT fiction:author (#PCDATA)>
<!ELEMENT fiction:genre (#PCDATA)>
```

Recall that DTDs do not natively have the concept of namespaces, but you can still define the structure
of elements and associate them with prefixes.

In this case

- the root element `<bookstore>` contains multiple `<book>` elements, and we define that it can contain either a `tech:book` or a `fiction:book` element

- the `tech:book | fiction:book` means that either of these elements is allowed in the `<bookstore>` container.

Now you can define two namespace, `xmlns:tech="http://example.com/tech"` and `xmlns:fiction="http://example.com/fiction"`;
we can't assign different schemas to different namespaces directly using DTD because you're still working with a single schema:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE bookstore SYSTEM "bookstore.dtd">
<bookstore xmlns:tech="http://example.com/tech"
     xmlns:fiction="http://example.com/fiction">

<!-- Elements in the 'tech' namespace -->
<tech:book>
<tech:title>XML and Beyond</tech:title>
<tech:author>John Doe</tech:author>
</tech:book>

<!-- Elements in the 'fiction' namespace -->
<fiction:book>
<fiction:title>Adventures in XML</fiction:title>
<fiction:author>John Smith</fiction:author>
</fiction:book>

</bookstore>
```

**Take-away:** using `xmlns` with DTDs, you cannot directly associate different schemas to different namespaces within a single XML document without workarounds to achieve a similar result (e.g. DTD with **namespace awareness**).

The best way to achieve such result will be through `xmlns` with **XSD**.

## 5. Validation with XSD (XML Schema Definition)

If you want to mix **two different namespaces** in the same XML document and apply **two different schemas** (one for each namespace), the **DTD** system would not provide the necessary flexibility to enforce this directly. 

An XML document may use an **XML Schema (XSD)** to define its structure and enforce validation rules. The XSD itself can define namespaces to ensure consistency or may just stick with the default namespace.

### From DTD to XSD

When using the XSD approach, old DTDs are replaced by XSDs.

For instance, the equivalent of:

**bookstore.dtd**
```xml
<!ELEMENT bookstore (book, magazine*)>
<!ELEMENT book (title, author, price)>
<!ELEMENT title (#PCDATA)>
<!ELEMENT author (#PCDATA)>
<!ELEMENT price (#PCDATA)>
<!ATTLIST price currency CDATA #REQUIRED>
<!ELEMENT magazine (title, edition)>
<!ELEMENT edition (#PCDATA)>
```

it is going to be:

**bookstore.xsd**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
  <xs:element name="bookstore">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="book" type="BookType"/>
        <xs:element name="magazine" type="MagazineType" minOccurs="0" maxOccurs="unbounded"/>
      </xs:sequence>
    </xs:complexType>
  </xs:element>

  <xs:complexType name="BookType">
    <xs:sequence>
      <xs:element name="title" type="xs:string"/>
      <xs:element name="author" type="xs:string"/>
      <xs:element name="price">
        <xs:complexType>
          <xs:simpleContent>
            <xs:extension base="xs:decimal">
              <xs:attribute name="currency" type="xs:string" use="required"/>
            </xs:extension>
          </xs:simpleContent>
        </xs:complexType>
      </xs:element>
    </xs:sequence>
  </xs:complexType>

  <xs:complexType name="MagazineType">
    <xs:sequence>
      <xs:element name="title" type="xs:string"/>
      <xs:element name="edition" type="xs:string"/>
    </xs:sequence>
  </xs:complexType>
</xs:schema>
```

The `xs` prefix is **custom** and can be changed, but the XML Schema definition must use `http://www.w3.org/2001/XMLSchema`.

1. **`<xs:schema>`**: The root element of an .xsd file that defines a new XML schema.
2. **`<xs:element>`**: Defines an XML element, e.g. `bookstore`, `book`, and `magazine`.
3. **`<xs:complexType>`**: Defines an element that contains child elements (e.g. `book`, `magazine`).
4. **`<xs:sequence>`**: Ensures elements appear in a specific order inside a complex type.
5. **`<xs:attribute>`**: Defines an attribute for an element (e.g. `currency` for `price`).

### Examples

As we did for `xmlns` with DTD, we will go through 4 examples:

- Tags belonging to no namespace
- Tags belonging to a default namespace
- Tags belonging to one namespace
- Tags belonging to two namespaces

**1. Tags belonging to no namespace**

**DTD way**:

**bookstore.dtd**
```xml
<!ELEMENT bookstore (book)>
<!ELEMENT book (title, author)>
<!ELEMENT title (#PCDATA)>
<!ELEMENT author (#PCDATA)>
```

**XML File**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE bookstore SYSTEM "bookstore.dtd">

<bookstore>
  <book>
    <title>XML Basics</title>
    <author>Jane Smith</author>
  </book>
</bookstore>
```

**XSD way**:

**bookstore.xsd**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
  <xs:element name="bookstore">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="book">
          <xs:complexType>
            <xs:sequence>
              <xs:element name="title" type="xs:string"/>
              <xs:element name="author" type="xs:string"/>
            </xs:sequence>
          </xs:complexType>
        </xs:element>
      </xs:sequence>
    </xs:complexType>
  </xs:element>
</xs:schema>
```

**XML File**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<bookstore xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="bookstore.xsd">
  <book>
    <title>XML Basics</title>
    <author>Jane Smith</author>
  </book>
</bookstore>
```

- `xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"` is an XML namespace declaration through **xmlns** which defines a namespace with prefix **xsi** living under `http://www.w3.org/2001/XMLSchema-instance`
- the prefix `xsi` is a **custom alias** that refers to `http://www.w3.org/2001/XMLSchema-instance`, which provides XSD-specific attributes like `noNamespaceSchemaLocation`
- `noNamespaceSchemaLocation` is an **official XML attribute** part of the **XML Schema Instance (XSI)** namespace, which is defined by the W3C (World Wide Web Consortium)
- `noNamespaceSchemaLocation` is from the XML Schema Instance (XSI) namespace and is not a custom-defined attribute
- this attribute is used to specify the location of the XML Schema (XSD) when the XML document does **not use any namespaces**
- `noNamespaceSchemaLocation` can be referred through the prefix `xsi` like in `xsi:noNamespaceSchemaLocation`
- `xsi:noNamespaceSchemaLocation="bookstore.xsd"` is used to link an XML document to an XSD file **when no namespace is defined**.
- `bookstore.xsd` specifies the location of the XSD file for validation; it will define the schema of the XML tags; **we will show how .xsd files look like in a little while**.

This transformation ensures that the XML structure follows the schema rules without utilizing a namespace for elements.

**2. Tags belonging to a default namespace**

**DTD way:**

**bookstore.dtd**
```xml
<!ELEMENT bookstore (book)>
<!ELEMENT book (title, author)>
<!ELEMENT title (#PCDATA)>
<!ELEMENT author (#PCDATA)>
```

**XML file**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE bookstore SYSTEM "bookstore.dtd">

<bookstore xmlns="http://example.com/tech">
  <book>
    <title>XML Basics</title>
    <author>Jane Smith</author>
  </book>
</bookstore>
```

**XSD way:**

**bookstore.xsd**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           targetNamespace="http://example.com/tech"
           xmlns="http://example.com/tech"
           elementFormDefault="qualified">
  
  <xs:element name="bookstore">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="book">
          <xs:complexType>
            <xs:sequence>
              <xs:element name="title" type="xs:string"/>
              <xs:element name="author" type="xs:string"/>
            </xs:sequence>
          </xs:complexType>
        </xs:element>
      </xs:sequence>
    </xs:complexType>
  </xs:element>
</xs:schema>
```

**XML file**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<bookstore xmlns="http://example.com/tech"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://example.com/tech bookstore.xsd">
  <book>
    <title>XML Basics</title>
    <author>Jane Smith</author>
  </book>
</bookstore>
```

In this case, the `bookstore` root element declares a default namespace (`http://example.com/tech`) using the `xmlns` attribute without any prefix. This means that all child elements within `<bookstore>`—such as `<book>`, `<title>`, and `<author>`—are automatically part of this namespace without requiring explicit prefixes.

We define the default namespace at the schema level using `targetNamespace`. We also specify `elementFormDefault="qualified"`, which ensures that all elements within this schema belong to the namespace.

1. `targetNamespace` in XSD
   - The `targetNamespace` attribute in an XSD defines the **namespace** for the elements that this schema defines.  
   - By specifying `targetNamespace="http://example.com/tech"`, all elements declared within this schema file (such as `<bookstore>`, `<book>`, `<title>`, and `<author>`) belong to the `"http://example.com/tech"` namespace.  
   - This ensures that any XML document conforming to this schema will place these elements under the same namespace.  
   - **Such a URL does not need to be an actual, reachable URL**; it simply acts as a unique identifier for the namespace.  

2. Why do `targetNamespace` and `xmlns` have the same value (`http://example.com/tech`)?
   - **`targetNamespace` in XSD**  
     - The `targetNamespace` attribute in the XSD file defines the namespace **for the elements that will appear in XML instance documents**.  
     - This means that any XML document that follows this schema must place its elements under this namespace (`http://example.com/tech`).  

   - **`xmlns` in XSD (`xs:schema` element)**  
     - The `xmlns="http://example.com/tech"` declaration in the XSD file specifies the **default namespace** for elements defined within the schema itself.  
     - This ensures that the elements defined in the XSD (`<xs:element>`, `<xs:complexType>`, etc.) belong to the `http://example.com/tech` namespace **by default**, without requiring explicit prefixes.  

   - **Effect on the XML Document**  
     - When an XML document is created based on this XSD, it must also declare `xmlns="http://example.com/tech"`, ensuring that its elements (`<bookstore>`, `<book>`, etc.) belong to the same namespace defined by `targetNamespace`.  

   - **Summary**  
     - `targetNamespace` defines the namespace **for the XML instance document elements**.  
     - `xmlns="http://example.com/tech"` in the XSD ensures that elements **defined within the schema** also belong to this namespace.  
     - This consistency ensures that XML documents and the XSD defining them are properly aligned and validated.  

3. **`elementFormDefault="qualified"`:**
   - This attribute ensures that **all elements in the schema** are **qualified** by the namespace.
   - When `elementFormDefault="qualified"` is set, the XML elements must be explicitly associated with a namespace (i.e. they must be prefixed with the namespace or declared in the default namespace). If this attribute were set to "unqualified," elements would not need to be associated with a namespace, meaning they could appear without a namespace in the XML document.

4. **`xmlns="http://example.com/tech"` in XML:**
   - This declares the **default namespace** in the XML document, so all child elements automatically inherit this namespace. 
   - Without this, child elements would be unqualified and not associated with the `http://example.com/tech` namespace. This helps ensure that the elements in the XML document match those defined in the schema.

5. **`xsi:schemaLocation` in XML:**
   - The `xsi:schemaLocation` attribute is used to link the XML document to its **XML Schema Definition (XSD)** file.
   - It contains two parts: 
     1. The **namespace URL** (`http://example.com/tech`).
     2. The **location of the XSD file** (`bookstore.xsd`).
   - The **space** between `http://example.com/tech` and `bookstore.xsd` separates the namespace from the location of the XSD file, so that the XML parser knows where to look for the schema definition file related to that namespace.
   - This attribute is specifically used when the XML document has a **namespace**. If no namespace is used, `xsi:noNamespaceSchemaLocation` would be appropriate instead.
   - By using `xsi:schemaLocation`, we associate the XML document with a schema file that contains rules for validating the XML document's structure.

6. **`noNamespaceSchemaLocation` vs. `xsi:schemaLocation`**
   - **`xsi:schemaLocation`** is used when the XML document **includes a namespace** (like `http://example.com/tech` in this case). It connects the XML document to an XSD file that validates it.
   - **`noNamespaceSchemaLocation`** is used when the XML document **does not have a namespace**. It links the XML document to an XSD file without associating any namespaces with the elements. This attribute is only used if the XML document is **namespace-free**, and elements don't have any namespace context.

7. **Why is there a space between `http://example.com/tech` and `bookstore.xsd` in `xsi:schemaLocation="http://example.com/tech bookstore.xsd"`?**
   - The space in `xsi:schemaLocation="http://example.com/tech bookstore.xsd"` separates the **namespace URL** (`http://example.com/tech`) from the **location of the XSD file** (`bookstore.xsd`).
   - The first part of this attribute (`http://example.com/tech`) specifies the namespace that applies to the XML elements, while the second part (`bookstore.xsd`) indicates the location of the XSD file that contains the rules for validating those elements.
   - The space serves as a delimiter to distinguish these two components: the namespace and the schema location.

8. **Why `xs` prefix is required and where it's defined:**
   - The `xs` prefix is used to refer to the **XML Schema** namespace (`http://www.w3.org/2001/XMLSchema`). 
   - This prefix is **defined within the schema declaration itself**: `xmlns:xs="http://www.w3.org/2001/XMLSchema"`.
   - It's needed because XML Schema definitions (like `xs:schema`, `xs:element`, `xs:complexType`) belong to this specific XML Schema namespace, and the prefix (`xs`) ensures the elements are correctly recognized as part of that namespace.

9. **Why are there two `xmlns` attributes (one with a prefix and one without a prefix):**
   - The **`xmlns` attribute without a prefix** (`xmlns="http://example.com/tech"`) defines the **default namespace** for the elements in the XML document.
     - This ensures that all elements within the XML document (like `<bookstore>`, `<book>`, etc.) are automatically placed under the `http://example.com/tech` namespace, without needing an explicit prefix on every element.
   - The **`xmlns:xs` attribute with a prefix** (`xmlns:xs="http://www.w3.org/2001/XMLSchema"`) defines the **namespace for the XML Schema (XSD)** standard.
     - The `xs` prefix is used to distinguish XML Schema elements (e.g. `<xs:element>`, `<xs:complexType>`) from regular XML elements that belong to the `http://example.com/tech` namespace.

**3. Tags belonging to one namespace**

**DTD way**:

**bookstore.dtd**
```xml
<!ELEMENT bookstore (book)>
<!ELEMENT book (title, author)>
<!ELEMENT title (#PCDATA)>
<!ELEMENT author (#PCDATA)>
```

**XML file**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE bookstore SYSTEM "bookstore.dtd">

<bookstore xmlns:tech="http://example.com/tech">
  <tech:book>
    <tech:title>XML Basics</tech:title>
    <tech:author>Jane Smith</tech:author>
  </tech:book>
</bookstore>
```

**XSD way**:

**bookstore.xsd**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           targetNamespace="http://example.com/tech"
           xmlns:tech="http://example.com/tech"
           elementFormDefault="qualified">
  
  <xs:element name="bookstore">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="book">
          <xs:complexType>
            <xs:sequence>
              <xs:element name="title" type="xs:string"/>
              <xs:element name="author" type="xs:string"/>
            </xs:sequence>
          </xs:complexType>
        </xs:element>
      </xs:sequence>
    </xs:complexType>
  </xs:element>
</xs:schema>
```

**XML file**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<bookstore xmlns:tech="http://example.com/tech"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://example.com/tech bookstore.xsd">
  <tech:book>
    <tech:title>XML Basics</tech:title>
    <tech:author>Jane Smith</tech:author>
  </tech:book>
</bookstore>
```

**4. Tags belonging to two namespaces**

**DTD way**:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE bookstore [
  <!ELEMENT bookstore (tech:book+, fiction:book+)>
  <!ELEMENT tech:book (tech:title, tech:author, tech:publisher)>
  <!ELEMENT tech:title (#PCDATA)>
  <!ELEMENT tech:author (#PCDATA)>
  <!ELEMENT tech:publisher (#PCDATA)>
  <!ELEMENT fiction:book (fiction:title, fiction:author, fiction:genre)>
  <!ELEMENT fiction:title (#PCDATA)>
  <!ELEMENT fiction:author (#PCDATA)>
  <!ELEMENT fiction:genre (#PCDATA)>
]>

<bookstore>
  <tech:book>
    <tech:title>XML and Beyond</tech:title>
    <tech:author>John Doe</tech:author>
    <tech:publisher>Tech Publishers</tech:publisher>
  </tech:book>
  
  <fiction:book>
    <fiction:title>Adventures in XML</fiction:title>
    <fiction:author>Jane Smith</fiction:author>
    <fiction:genre>Fantasy</fiction:genre>
  </fiction:book>
</bookstore>
```

**XSD way**:

**tech.xsd**:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           targetNamespace="http://example.com/tech"
           xmlns:tech="http://example.com/tech"
           elementFormDefault="qualified">

  <xs:element name="book">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="title" type="xs:string"/>
        <xs:element name="author" type="xs:string"/>
        <xs:element name="publisher" type="xs:string"/>
      </xs:sequence>
    </xs:complexType>
  </xs:element>
</xs:schema>
```

**fiction.xsd**:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
           targetNamespace="http://example.com/fiction"
           xmlns:fiction="http://example.com/fiction"
           elementFormDefault="qualified">

  <xs:element name="book">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="title" type="xs:string"/>
        <xs:element name="author" type="xs:string"/>
        <xs:element name="genre" type="xs:string"/>
      </xs:sequence>
    </xs:complexType>
  </xs:element>
</xs:schema>
```

**XML file**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<bookstore xmlns:tech="http://example.com/tech"
           xmlns:fiction="http://example.com/fiction"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://example.com/tech tech.xsd
                               http://example.com/fiction fiction.xsd">

  <tech:book>
    <tech:title>XML and Beyond</tech:title>
    <tech:author>John Doe</tech:author>
    <tech:publisher>Tech Publishers</tech:publisher>
  </tech:book>

  <fiction:book>
    <fiction:title>Adventures in XML</fiction:title>
    <fiction:author>Jane Smith</fiction:author>
    <fiction:genre>Fantasy</fiction:genre>
  </fiction:book>
</bookstore>
```

1. **Namespaces (`xmlns:tech` and `xmlns:fiction`)**: Explicitly define two different namespaces for books.
2. **`targetNamespace` in XSD**: Each schema file is associated with a specific namespace.
3. **`elementFormDefault="qualified"`**: Ensures all elements in the namespace must be explicitly prefixed.
4. **Multiple Schema References (`xsi:schemaLocation`)**: Specifies the location of both schemas.
5. **Element Qualification**: `<tech:book>` and `<fiction:book>` must be used instead of unqualified `<book>` elements.

By transitioning from DTD to XSD, we achieve **true namespace enforcement**, allowing elements from different schemas to be validated independently.

## 6. Namespaces and validation

### Why Are Namespaces Not Enough for Validation?

A **namespace** in XML is essentially a mechanism used to avoid naming conflicts by associating a unique identifier (a URI) with element and attribute names. However, namespaces **do not provide validation** of the content, structure, or relationships within the XML document. They only **disambiguate element and attribute names** when you are using multiple vocabularies or sources in the same document. 

Namespaces only ensure that elements and attributes are identified uniquely within the document. They **do not validate the structure or content** of the XML document itself. This is why additional validation tools like **XSD** (XML Schema Definition) or **DTD** (Document Type Definition) are necessary.

### Why Validation Is Needed

Without validation, an XML document may be syntactically correct (well-formed) but not conform to the required rules or structure for the intended use. For example, an XML document might contain a `book` element, but if the `book` element is missing required child elements like `price` or `author`, there would be no way to catch that issue without validation.

### Conclusion

In summary, **namespaces** provide a way to uniquely identify element and attribute names in XML documents, but they **do not enforce validation rules**. To ensure that an XML document adheres to specific rules about its structure, content, and data types, you must use **XSD** or **DTD**. 

XSD provides far more flexibility than DTD and is preferred for modern XML validation. It can be used to validate both the structure and the content of XML documents, ensuring that elements follow the expected patterns, relationships, and data types, as dictated by the schema.

## 7. Validation Steps and Tools

To determine whether an XML document is valid and whether its tags are correctly structured, follow these validation steps:

### 7.1 Well-formedness Check (Basic XML Validation)

A well-formed XML document adheres to the fundamental syntax rules of XML, such as:
- Every opening tag has a corresponding closing tag.
- Tags are properly nested.
- Special characters like `<`, `>`, and `&` are correctly escaped.
- Attribute values are enclosed in quotes.

You can check for well-formedness by parsing the XML document using an XML parser. If no syntax errors are found, the document is considered well-formed.

### 7.2 Validation Against a Schema (Optional Validation)

Validation ensures that the XML content follows specific rules regarding structure, tags, and data types, as defined by a schema (XSD or DTD).

- **XML Schema Definition (XSD)**: Defines the structure, allowed tags, attributes, and data types to validate an XML document.
- **Document Type Definition (DTD)**: Specifies the legal structure and elements but lacks the data type specificity of XSD.

### 7.3 XML Validation Methods

#### 1. Manual Validation
Manually inspect the XML document to ensure it adheres to the expected structure. If an XSD or DTD is available, verify that all elements, attributes, and values comply with the schema.

#### 2. Automated Validation Using Tools
A variety of tools can help validate XML documents:

- **Online Validators**: Websites that check XML well-formedness and validate against a schema.
- **XML Parsers**: Programming libraries in Python, Java, and JavaScript that provide built-in XML validation.

### 7.4 Schema Declaration in XML Documents

For an XML document to be validated against a schema, it should include a reference to an XSD or DTD. Without a schema reference, only well-formedness can be checked, not structural or semantic validity.

### 7.5 Validation Tools

#### Well-formedness and Validity Checks
- **Well-formedness**: Ensures the XML adheres to syntax rules such as proper nesting and escaping of characters.
- **Validity**: Ensures the XML conforms to the predefined structure of an XSD or DTD.

#### Types of Validation
- **Without a Schema**: Only well-formedness can be checked.
- **With a Schema**: Both well-formedness and validity can be verified.

#### Recommended Online Tools
- **Free Online XML Validator** – Checks well-formedness and validates against XSD or DTD.
- **XML Lint** – Command-line tool for checking well-formedness and schema validation.
- **XML Schema Validator** – Verifies XML against an XSD.
- **W3C XML Validator** – Validates XML using a specified schema.

Schema validation typically requires either an uploaded schema file or a valid schema reference within the XML document.

### 7.6 Schema Usage in Web Services

Most web services do not include schemas within XML messages but rather establish schema agreements beforehand.

- **SOAP Web Services**: Use WSDL (Web Services Description Language), which references an XSD for validation.
- **RESTful APIs**: May document XML schemas but typically do not enforce validation at runtime.

#### XML Schema Validation Workflow in Web Services
1. The client and server agree on an XML schema during the design phase.
2. The schema is provided through WSDL (for SOAP) or API documentation (for RESTful services).
3. The receiving service validates incoming XML against the agreed-upon schema.
4. If invalid XML is received, the service may reject the request or return an error message.

### 7.7 Summary
- **Well-formedness**: Always validated, even without a schema.
- **Validity**: Requires an XSD or DTD.
- **Online tools**: Can validate both well-formedness and validity.
- **Web services**: Use predefined schemas for XML validation, ensuring data consistency.

## 8. XML Parsers

### 8.1 Introduction to XML Parsers
- **Definition and Role of Parsers**: An XML parser is a software component that reads and interprets XML documents, allowing programs to access and manipulate the data within them. Parsers convert XML data into a structure that can be easily processed, such as a tree, list, or stream.
- **Why XML Parsers Are Needed**: XML parsers are essential because XML is a markup language designed for data storage and transport. To process XML files programmatically (e.g. extract data or validate structure), an XML parser is required.

### 8.2 Types of XML Parsers
- **DOM (Document Object Model) Parser**: 
  - The DOM parser loads the entire XML document into memory, creating a tree structure where each element, attribute, and text node is represented as a node.
  - DOM allows random access to the XML data, meaning elements can be modified, added, or deleted in the memory tree.
  
- **SAX (Simple API for XML) Parser**: 
  - The SAX parser reads XML documents sequentially and triggers events when elements are encountered. It doesn't load the entire document into memory but processes it as a stream.
  - SAX is event-driven and provides a minimal memory footprint, which is useful for large XML files.
  
### 8.3 DOM vs. SAX: A Comparison
- **Memory Usage**: DOM loads the entire document into memory, which can be resource-intensive, especially for large files. SAX, however, processes XML as a stream, requiring much less memory.
- **Speed and Performance**: SAX is generally faster for large XML documents as it processes data sequentially. DOM is slower because it has to load the entire document into memory before processing.
- **Complexity and Ease of Use**: DOM is easier to work with, as it allows random access to the XML structure, making it more intuitive for tasks like tree traversal and modification. SAX requires a more complex event-based approach.
- **Applicability to Large XML Files**: SAX is better suited for large files as it doesn't load the entire file into memory. DOM is more suited for smaller XML files that need frequent manipulation.
- **Synchronization with XML Documents**: DOM works with a complete, in-memory representation of the document, while SAX works with events that occur as the document is read sequentially.

### 8.4 Advantages and Disadvantages
- **DOM Parser**:
  - **Advantages**: 
    - Easy to navigate and manipulate the document.
    - Allows for random access and modification of elements.
  - **Disadvantages**: 
    - High memory usage as it loads the entire document into memory.
    - Can be slow when dealing with large XML files.

- **SAX Parser**:
  - **Advantages**: 
    - Low memory footprint, as it processes the document sequentially.
    - Suitable for large XML files.
  - **Disadvantages**: 
    - More complex to use, as it requires event handling.
    - No direct access to the XML data once it has been read.

### 8.5 Choosing the Right XML Parser
- **When to Use DOM**: 
  - Use DOM when you need to access and modify elements at random and when the XML document is relatively small to medium in size.
- **When to Use SAX**: 
  - Use SAX when working with large XML files where memory efficiency is a concern, or when you only need to read the document without making changes to its structure.
- **Other Parsing Options**: 
  - **StAX** (Streaming API for XML): A pull-based parser that combines features of both DOM and SAX, allowing developers to control when to pull data from the XML stream.
  - **Pull Parsers**: Similar to StAX, pull parsers provide more control over the flow of the document compared to SAX.

### 8.6 Practical Applications and Usage
- **Parsing XML with DOM and SAX in Different Programming Languages**: 
  - DOM and SAX are supported in many programming languages, including Java, Python, and C#. In Java, for example, `javax.xml.parsers.DocumentBuilderFactory` can be used for DOM, while `org.xml.sax.SAXParser` can be used for SAX.
  
- **Real-World Use Cases for XML Parsers**: 
  - Parsing and manipulating XML configuration files.
  - Reading and processing XML-based data formats such as RSS feeds or SOAP messages.
  - Validating XML documents against DTD or XML Schema definitions.

