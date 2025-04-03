# Rest HelloWorld

# Database Schema Creation

## 1. Login to PostgreSQL CLI
Since the database `my_app_db` is already created, log in to the PostgreSQL CLI:
```bash
sudo -u postgres psql
```
Then switch to `my_app_db`:
```sql
\c my_app_db
```

## 2. Create Schema
We create a new schema named `shop_schema` to encapsulate the tables:
```sql
CREATE SCHEMA shop_schema;
```

## 3. Create Tables
### 3.1 User Table
**Description:** Stores user information. A user can place multiple orders (one-to-many relationship).

- `user_id SERIAL PRIMARY KEY`:
  - `SERIAL`: Automatically increments values.
  - `PRIMARY KEY`: Uniquely identifies each record.
- `username VARCHAR(50) UNIQUE NOT NULL`:
  - `VARCHAR(50)`: Limits username length to 50 characters.
  - `UNIQUE`: Ensures no duplicate usernames.
  - `NOT NULL`: Username must be provided.
- `email VARCHAR(100) UNIQUE NOT NULL`:
  - `VARCHAR(100)`: Limits email length to 100 characters.
  - `UNIQUE`: Ensures no duplicate emails.
  - `NOT NULL`: Email is mandatory.
- `created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP`:
  - `TIMESTAMP`: Stores date and time.
  - `DEFAULT CURRENT_TIMESTAMP`: Automatically sets to current time upon insertion.

```sql
CREATE TABLE shop_schema.User (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3.2 Order Table
**Description:** Stores orders placed by users. Each order belongs to a single user (many-to-one relationship). An order can contain multiple items, and an item can be part of multiple orders, establishing a **many-to-many relationship** with the `Item` table via the `Order_Item` junction table.

- `order_id SERIAL PRIMARY KEY`: Unique auto-incrementing identifier for orders.
- `user_id_fk INT NOT NULL`:
  - References `User(user_id)`, establishing a foreign key.
  - `ON DELETE CASCADE`: If a user is deleted, their orders are also deleted.
- `order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP`: Auto-fills with the current timestamp when a record is inserted.
- `status VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered', 'canceled'))`:
  - `VARCHAR(20)`: Stores predefined status values.
  - `CHECK`: Ensures valid statuses.

```sql
CREATE TABLE shop_schema.Order (
    order_id SERIAL PRIMARY KEY,
    user_id_fk INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK (status IN ('pending', 'shipped', 'delivered', 'canceled')),
    FOREIGN KEY (user_id_fk) REFERENCES shop_schema.User(user_id) ON DELETE CASCADE
);
```

### 3.3 Item Table
**Description:** Stores products available for purchase. One item can belong to multiple orders, and one order can contain multiple items, forming a **many-to-many relationship** with the `Order` table through the `Order_Item` junction table.

- `item_id SERIAL PRIMARY KEY`: Unique identifier for each product.
- `name VARCHAR(100) NOT NULL`: Product name (mandatory).
- `description TEXT`: Optional longer description of the product.
- `price DECIMAL(10,2) NOT NULL CHECK (price > 0)`:
  - Stores price with two decimal places.
  - Ensures price is greater than zero.
- `stock_quantity INT NOT NULL CHECK (stock_quantity >= 0)`:
  - Ensures stock is non-negative.

```sql
CREATE TABLE shop_schema.Item (
    item_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    stock_quantity INT NOT NULL CHECK (stock_quantity >= 0)
);
```

### 3.4 Order_Item (Join Table)
**Description:** Since an order can contain multiple items, and an item can be in multiple orders, we use a join table.

- **Why this table exists:**
  - An order can have multiple items (one-to-many with `Item`).
  - An item can be included in multiple orders (one-to-many with `Order`).
  - This table establishes a many-to-many relationship between orders and items.

**Columns:**
- `order_id_fk INT NOT NULL`:
  - References an order in the `Order` table.
  - `ON DELETE CASCADE`: If an order is deleted, related records in Order_Item are also removed.
- `item_id_fk INT NOT NULL`:
  - References an item in the `Item` table.
  - `ON DELETE CASCADE`: If an item is deleted, related records in Order_Item are also removed.
- `quantity INT NOT NULL CHECK (quantity > 0)`:
  - Stores the number of items ordered.
  - Ensures at least one item is present.

**Primary Key:** Composite key (`order_id_fk`, `item_id_fk`) ensures uniqueness.

**Foreign Keys:**
- `order_id_fk`: References `Order(order_id)`, ensuring each record links to a valid order.
- `item_id_fk`: References `Item(item_id)`, ensuring each record links to a valid item.

```sql
CREATE TABLE shop_schema.Order_Item (
    order_id_fk INT NOT NULL,
    item_id_fk INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    PRIMARY KEY (order_id_fk, item_id_fk),
    FOREIGN KEY (order_id_fk) REFERENCES shop_schema.Order(order_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id_fk) REFERENCES shop_schema.Item(item_id) ON DELETE CASCADE
);
```

## 4. Verify Table Creation
To check that all tables have been created correctly, use:
```sql
\dt shop_schema.*
```
This will list all tables in the `shop_schema` schema.

## 5. Conclusion
Now, we have:
- **`User`**: Stores user information.
- **`Order`**: Stores orders placed by users (one-to-many with `User`).
- **`Item`**: Stores product details (many-to-many with `Order`).
- **`Order_Item`**: Join table linking orders and items (many-to-many relationship).

**Naming Convention:**
- **Foreign keys should have the `_fk` suffix** to clearly indicate their role.
- This makes queries more readable and avoids confusion.
- Example: `user_id_fk` instead of just `user_id`.

This schema ensures structured and efficient order management.

# Directory structure

````tetx
├── pom.xml
└── src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           ├── controller
│   │           │   ├── ItemController.java
│   │           │   ├── OrderController.java
│   │           │   ├── OrderItemController.java
│   │           │   └── UserController.java
│   │           ├── model
│   │           │   ├── enums
│   │           │   │   └── OrderStatus.java
│   │           │   ├── Item.java
│   │           │   ├── key
│   │           │   │   └── OrderItemKey.java
│   │           │   ├── OrderItem.java
│   │           │   ├── Order.java
│   │           │   └── User.java
│   │           ├── repository
│   │           │   ├── ItemRepository.java
│   │           │   ├── OrderItemRepository.java
│   │           │   ├── OrderRepository.java
│   │           │   └── UserRepository.java
│   │           ├── rest
│   │           │   ├── request
│   │           │   │   ├── item
│   │           │   │   │   └── ItemRequest.java
│   │           │   │   ├── order
│   │           │   │   │   ├── OrderRequest.java
│   │           │   │   │   └── RemoveOrderRequest.java
│   │           │   │   ├── orderitem
│   │           │   │   │   ├── AddItemToOrderRequest.java
│   │           │   │   │   └── RemoveItemFromOrderRequest.java
│   │           │   │   └── user
│   │           │   │       ├── RemoveUserRequest.java
│   │           │   │       └── UserRequest.java
│   │           │   └── response
│   │           │       ├── item
│   │           │       │   └── ItemResponse.java
│   │           │       ├── order
│   │           │       │   ├── OrderResponse.java
│   │           │       │   └── RemoveOrderResponse.java
│   │           │       ├── orderitem
│   │           │       │   ├── AddItemToOrderResponse.java
│   │           │       │   └── RemoveItemFromOrderResponse.java
│   │           │       └── user
│   │           │           ├── RemoveUserResponse.java
│   │           │           └── UserResponse.java
│   │           ├── service
│   │           │   ├── ItemService.java
│   │           │   ├── OrderItemService.java
│   │           │   ├── OrderService.java
│   │           │   └── UserService.java
│   │           └── WebServiceRest.java
│   └── resources
│       └── application.properties
````

# pom.xml

````xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>webservicerest</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <!-- Set Java version to 21; this value can be referenced anywhere in the pom.xml -->
        <java.version>21</java.version>

        <!-- Specifies the character encoding used for the source files during the build process.
     UTF-8 is commonly used for compatibility across platforms.
     This avoids relying on the platform's default encoding, which can vary across different operating systems. -->
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

        <!-- Specifies the character encoding used for generating reports during the build process.
             This ensures that the output files (like reports) are encoded in UTF-8.
             By explicitly defining this encoding, it avoids using the platform’s default encoding (which can be inconsistent),
             preventing potential issues with special characters or incorrect file reading/writing. -->
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <!-- Spring Boot 3.2.2 Web Starter for REST Controllers -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>3.2.2</version> <!-- Updated Spring Boot -->
        </dependency>

        <!-- Spring Boot Data JPA Starter -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>3.2.2</version> <!-- Updated Spring Boot -->
        </dependency>

        <!-- SLF4J API (Logging API) -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>2.0.9</version> <!-- Updated SLF4J to match newer Spring Boot -->
            <scope>runtime</scope>
        </dependency>

        <!-- PostgreSQL Database Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.6.0</version> <!-- Latest version -->
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok for reducing boilerplate code -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.30</version> <!-- Updated to latest version -->
            <scope>provided</scope>
        </dependency>

        <!-- Hibernate Validator: An implementation of the Bean Validation API (JSR 303/JSR 380) used to perform Java Bean validation -->
        <dependency>
            <groupId>org.hibernate.validator
            </groupId>  <!-- Hibernate Validator is the reference implementation of Bean Validation -->
            <artifactId>hibernate-validator
            </artifactId>  <!-- This dependency provides the core validation logic for annotations like @NotNull, @Size, etc. -->
            <version>8.0.0.Final</version>  <!-- Specifies the version of Hibernate Validator to use -->
        </dependency>

        <!-- Jakarta Expression Language (EL): Required for certain Bean Validation features like using expressions in constraints -->
        <dependency>
            <groupId>org.glassfish</groupId>
            <artifactId>jakarta.el</artifactId>
            <version>4.0.2
            </version>  <!-- EL is used for handling expressions in constraints (e.g. for custom validation logic) -->
        </dependency>

        <!-- Jakarta Validation API: Defines the API for Java Bean validation annotations like @NotNull, @Size, etc. -->
        <dependency>
            <groupId>jakarta.validation
            </groupId>  <!-- The Jakarta Validation API provides the annotations and interfaces used for Bean Validation -->
            <artifactId>jakarta.validation-api
            </artifactId>  <!-- This API is the basis for validation annotations such as @Min, @NotNull, etc. -->
            <version>3.0.0</version>  <!-- This version is compatible with the Jakarta EE 9+ ecosystem -->
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                    <!--
                    Why this is required in Java 21:
                    Starting from Java 12 (and continuing in Java 21), the default behavior does not retain parameter names for reflection unless the -parameters flag is explicitly passed during compilation.
                    This stricter behavior ensures better performance and consistency, but frameworks like Spring rely on reflection to map HTTP request parameters to method arguments.

                    Specifically, when using annotations like @RequestParam, Spring uses reflection to access the method parameter names.
                    If the parameter names aren't retained in the bytecode (which happens without the -parameters flag), Spring can't properly map the request parameters to the method parameters, leading to errors or issues during runtime.

                    By adding the -parameters flag, we ensure that parameter names are included in the bytecode, allowing Spring to use reflection correctly and perform tasks like mapping @RequestParam to method parameters.
                    -->
                    <compilerArgument>-parameters</compilerArgument> <!-- Add this line -->
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
````

# Entity classes

In order to perform CRUD operations efficiently in an object-oriented programming language like Java while interacting with a relational database, we need a way to bridge the object-relational mismatch. This is where JPA (Java Persistence API) and Hibernate come into play. JPA allows us to define entity classes that directly map to tables in the database, making it easier to manipulate data using Java objects while persisting changes in the database.

## POJO (Plain Old Java Object)
- A POJO is a simple Java class that does not depend on external frameworks.
- It contains attributes and their respective getter/setter methods.
- It is used for representing data in a structured way but does not enforce any specific behavior.

## Entity (JPA Entity)
- An entity is a special type of POJO mapped to a relational database table using JPA.
- Entities are annotated with `@Entity` and must have a primary key (`@Id`).
- They allow ORM (Object-Relational Mapping) tools like Hibernate to handle database operations automatically.

## DTO (Data Transfer Object)
- It is a simple object that holds data and is specifically designed to transfer data between layers, such as from the backend service to the client.
- It helps in **separating** the internal representation of data (entities) from what is exposed to the client, providing a clear and controlled interface.
- In a good design, DTOS should be used for transferring data from the servic elayer to the controller layer.
- By using DTOs, you can avoid exposing unnecessary internal details of your application, preventing issues such as **recursive nesting**.


### Summary Table:
| Feature  | POJO | Entity | DTO |
|----------|------|--------|-----|
| Framework Dependency | No | Yes (JPA/Hibernate) | No |
| Maps to DB Table | No | Yes | No |
| Used in Persistence | No | Yes | No |
| Used for API Communication | No | No | Yes |

---

## Constraints: Entity Level vs Database Level

When designing an application, it is essential to enforce data integrity and business rules. Constraints play a crucial role in this, and they can be defined both at the **entity level** (in the code, using an ORM like Hibernate) and at the **database level** (in the database schema). Both levels serve distinct purposes, but together, they form a comprehensive validation and security layer. Understanding why we should repeat constraints at both levels is vital for building reliable and maintainable systems.

---

### Entity Level Constraints

Entity-level constraints are defined within the Java code using annotations that are understood by the ORM (e.g. Hibernate). These constraints are typically used to ensure that the data adheres to the business rules before it is persisted in the database. Common entity-level annotations include:

- `@NotNull`: Ensures that the field cannot be null.
- `@Size`: Restricts the length of a string field.
- `@Unique`: Though not a standard JPA annotation, it is often implemented via custom logic or a database-level constraint to ensure uniqueness.
- `@Min`, `@Max`: Defines a range for numerical values.
- `@Pattern`: Ensures that a string field matches a specific regular expression pattern.
- `@Enumerated(EnumType.STRING)`: Defines how an enum should be persisted in the database.

**Advantages of Entity-Level Constraints:**

1. **Immediate Validation:** Entity-level constraints allow for **real-time validation** when the application processes data. For example, `@NotNull` ensures that a field is non-null before the data is even sent to the database, avoiding potential database errors caused by invalid data.

2. **Separation of Business Logic:** By using annotations like `@Size` or `@Pattern`, you ensure that business rules are handled within the application layer, separate from the underlying database. This separation makes your application more maintainable and flexible.

3. **Portability Across Databases:** Entity-level constraints are database-agnostic. Whether you're using PostgreSQL, MySQL, or any other database, these constraints ensure consistent validation in your application without being tied to a specific database engine or its constraints.

4. **User-Friendliness:** By validating at the entity level, you can catch errors earlier in the application's flow and provide meaningful error messages to users, improving the user experience. This is much easier than dealing with cryptic database errors after the data has been committed.

---

### Database Level Constraints

Database-level constraints are enforced directly in the database schema and are used to ensure that data adheres to specific rules, even when data is inserted or modified outside of the application (e.g. via SQL scripts or other services interacting with the database). Common database constraints include:

- `NOT NULL`: Ensures a column cannot contain null values.
- `UNIQUE`: Ensures all values in a column are unique across the table.
- `CHECK`: Enforces a custom condition for data in a column (e.g. a price cannot be less than 0).
- `FOREIGN KEY`: Ensures that the data in a column matches data in another table, enforcing referential integrity.
- `DEFAULT`: Automatically sets a value for a column if no value is provided.

**Advantages of Database-Level Constraints:**

1. **Data Integrity:** Database constraints are a last line of defense to ensure that only valid data is stored. These constraints are enforced by the database engine itself, ensuring that no invalid data can be written to the database, even if the application fails to validate the data correctly.

2. **Security and Consistency:** By enforcing constraints like `UNIQUE` or `FOREIGN KEY` at the database level, the database ensures that data integrity is maintained, even if other applications or services interact with the database. This is critical when multiple systems need to share the same database.

3. **Efficient Performance:** Constraints such as `UNIQUE` or `CHECK` are often optimized by the database engine, making them more efficient than implementing the same checks at the application level, particularly when dealing with large datasets.

4. **Enforcing Complex Rules:** Some database constraints, such as cascading delete operations (`ON DELETE CASCADE` for foreign keys), are better handled at the database level. These ensure referential integrity is automatically managed by the database, reducing the need for complex logic in the application.

---

### Why Reinforce Constraints at Both Levels?

Although both entity-level and database-level constraints are crucial for maintaining data integrity, they serve different, complementary purposes. Here’s why it makes sense to define constraints at both levels:

1. **Redundancy for Reliability:** While database constraints are the final safeguard, entity-level constraints prevent invalid data from ever reaching the database. This early validation helps prevent database errors and allows your application to catch problems before they cause issues at the storage level. For example, without entity-level validation, an application could attempt to insert a null value into a field marked `NOT NULL` in the database, causing a database error at runtime.

2. **Decoupling Business Logic from Data Storage:** Entity-level constraints define **application-specific rules** (e.g. a user’s email must be in a specific format) that are independent of the database. These constraints ensure that the business rules are enforced regardless of the database system, making the application more portable and flexible. Database constraints, on the other hand, ensure the **integrity of the stored data**, maintaining consistency and relationships between tables even if the data comes from other sources.

3. **Better User Experience with Immediate Feedback:** Entity-level constraints allow for early detection of issues like invalid input or missing fields, which can be handled more gracefully in the application (e.g. showing a user-friendly error message). Database-level errors, by contrast, typically occur later in the process and may require more complex debugging.

4. **Ensuring Data Integrity Across Multiple Sources:** In systems where multiple applications or services interact with the same database, database constraints ensure **consistent rules** across the entire system. For example, if one service bypasses the application logic and directly manipulates the database, the database constraints will ensure that data integrity is not compromised. This redundancy is especially useful in environments with distributed systems or microservices.

5. **Database-Specific Features and Optimization:** Some constraints or features are more naturally enforced at the database level, such as `FOREIGN KEY` relationships, cascading operations, and performance optimizations on indexed columns. These are specific to the capabilities of the database engine and are often necessary to maintain optimal performance and consistency.

---

### Conclusion

Both **entity-level constraints** and **database-level constraints** play critical roles in maintaining data integrity and ensuring the reliability of an application.

- **Entity-level constraints** ensure that business rules are respected before data is persisted, improving application portability and providing early feedback.
- **Database-level constraints** ensure the integrity of the data at rest, protecting against corruption and ensuring consistency, even when data is manipulated outside of the application.

Reinforcing constraints at both levels provides a **robust, fault-tolerant system** that can handle data correctly, regardless of where it is being processed or accessed. By using both, you create an application that is **secure, maintainable, and reliable**.

---

## **1. User Entity**
### **Purpose:**
The `User` entity represents a user in the shop system, storing details like username, email, and creation timestamp. A user can place multiple orders
forming a **one-to-many relationship** with the `Order` table.

### **Annotations Explained:**
- `@Entity` – Marks this class as a JPA entity, meaning it will be mapped to a database table.
- `@Table(name = "User", schema = "shop_schema")` – Specifies the table name and schema.
- `@Id` – Marks `userId` as the primary key.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)` – Allows the database to auto-generate IDs.
- `@Column(name = "user_id")` - Explicitly maps the `userId` field to the `user_id` column in the database.
- `@Column(name = "username", nullable = false, unique = true, length = 50)` – Ensures `username` is unique and cannot be null.
- `@Column(name = "created_at", nullable = false, updatable = false)` – The `created_at` timestamp is set when the user is created and cannot be updated.
- `@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)`:
  - **One user can have multiple orders**, so a **List<Order>** is used.
  - `mappedBy = "user"` means the `Order` entity holds the foreign key.
    - In the `Order` entity, there's a field like:
    
      ```java
      @ManyToOne
      @JoinColumn(name = "user_id_fk", nullable = false)
      private User user;
      ```
      
    - `"user"` in `mappedBy = "user"` refers to this field in the `Order` class, which represents the relationship between the `Order` and `User`.
    - The `User` entity does **not** manage the foreign key; the foreign key is in the `Order` entity, making the `Order` entity the **owning side** of the relationship.
    - The **ownership** of the relationship is with the `Order` entity, as it contains the foreign key (`user_id_fk`) and is responsible for managing it. The `User` entity simply holds a reference to a collection of `Order` objects.
  - `cascade = CascadeType.ALL` ensures that changes in `User` propagate to related `Order` records.
    - For example, if a `User` is deleted, their associated `Orders` will also be deleted automatically because of `CascadeType.ALL`.
    - This cascade ensures that all related orders are handled when the user is removed or updated, reinforcing the relationship's integrity.

````java
package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User entity representing a user in the shop system.
 */
@Getter // Lombok annotation to generate getter methods
@Setter // Lombok annotation to generate setter methods
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@Entity // Marks this class as a JPA entity
@Table(name = "User", schema = "shop_schema") // Maps this entity to the "User" table in "shop_schema"
public class User {

  /**
   * Primary key, automatically generated.
   */
  @Id // Marks this field as the primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies auto-incrementing primary key generation
  @Column(name = "user_id")
  private Integer userId;

  /**
   * Unique username for the user.
   */
  @Column(name = "username", nullable = false, unique = true, length = 50)
  // Ensures this column is non-null and unique
  private String username;

  /**
   * Unique email for the user.
   */
  @Column(name = "email", nullable = false, unique = true, length = 100) // Ensures this column is non-null and unique
  private String email;

  /**
   * Timestamp of user creation.
   */
  @Column(name = "created_at", nullable = false, updatable = false)
  // Prevents updates and ensures non-null constraint
  private LocalDateTime createdAt = LocalDateTime.now();

  /**
   * One user can have many orders.
   * The `mappedBy = "user"` indicates that the `Order` entity owns the relationship,
   * with the `user` field in the `Order` entity holding the foreign key reference to `User`.
   */
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Order> orders;
}
````

- Since **one** user can place **many** orders, we store orders as a `List<Order>` in `User`.
- The **foreign key resides in the `Order` entity** (on the `Many` side).
- The **ownership** of `@OneToMany` the relationship is with the `Order` entity.
- When deleting a `User`, their orders are also deleted (due to `CascadeType.ALL`).

---

## **2. Order Entity**
### **Purpose:**
The `Order` entity represents a purchase made by a user, including order date, status, and related order items. An order belongs to a single user and can have multiple items. This forms both a **many-to-one** relationship with the `User` table and a **many-to-many** relationship with the `Item` table, which is managed through a junction table `Order_Item`.

A **many-to-many** relationship between `Order` and `Item` is translated into two **one-to-many** relationships through the `Order_Item` junction table. This table is responsible for storing the relationship between the `Order` and `Item` entities.

### **Annotations Explained:**
- `@Entity` and `@Table(name = "Order", schema = "shop_schema")` – Marks this class as an entity and maps it to the `Order` table.
- `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)` – Defines `order_id` as an auto-generated primary key.
- `@Column(name = "order_id")` - Explicitly maps the `orderId` field to the `order_id` column in the database.
- `@Column(name = "order_date", nullable = false)` - Explicitly maps the `orderDate` field to the `order_date` column in the database and ensures `orderDate` is required.
- `@Enumerated(EnumType.STRING)` – Stores `OrderStatus` as a string in the database.
- `@ManyToOne`
  - **Many orders belong to one user**, so we store a **single `User` object** in `Order`.
  - `@JoinColumn(name = "user_id_fk", nullable = false)` – The **foreign key (`user_id_fk`) is stored in the `Order` table**.
  - The **ownership** of this `@ManyToOne` relationship is with the `Order` entity, as it contains the foreign key (`user_id_fk`). This means the `Order` entity is responsible for managing the foreign key, linking it to the `User` entity.
  - The `User` entity does **not** hold the foreign key, but rather, it is referenced in the `Order` entity.
- **One-to-many from Order to OrderItem**:
  - **One order can have multiple items**, so a **List<OrderItem>** is used in the `Order` entity.
  - `@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)` specifies that `OrderItem` is the owning side of the relationship.
  - `mappedBy = "order"` refers to the `Order` field in the `OrderItem` entity that holds a reference to `Order`.

    In the `OrderItem` entity, there's a field like:
  
    ```java
    @ManyToOne
    @MapsId("item_id_pk") 
    @JoinColumn(name = "order_id_fk", nullable = false)
    private Order order;
    ```
    
    This means the `OrderItem` entity holds the foreign key (`order_id_fk`) to represent the relationship between `Order` and `OrderItem`. The `Order` entity itself does **not** manage the foreign key.

    - `"order"` in `mappedBy = "order"` refers to this field in the `OrderItem` class, which represents the relationship between `OrderItem` and `Order`.
    - The `Order` entity does **not** manage the foreign key; the foreign key is in the `OrderItem` entity, making the `OrderItem` entity the **owning side** of the relationship.
    - The **ownership** of the relationship is with the `OrderItem` entity, as it contains the foreign key (`order_id_fk`) and is responsible for managing it. The `Order` entity simply holds a reference to a collection of `OrderItem` objects.
  - `cascade = CascadeType.ALL` ensures that changes in `Order` propagate to related `OrderItem` records.
    - For example, if an `Order` is deleted, the associated `OrderItems` will also be deleted automatically because of `CascadeType.ALL`.
    - This cascade ensures that all related order items are handled when the order is removed or updated, reinforcing the relationship's integrity.
    - **As result**, deleting a `User` will result in the deletion of all associated `Orders` and their related `OrderItem` entities due to the cascading delete behavior set by `CascadeType.ALL`. This helps maintain referential integrity within the database and prevents orphaned records in the `Order` and `OrderItem` tables.

````java
package com.example.model;

import com.example.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Order", schema = "shop_schema")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Integer orderId;

  /**
   * Many orders can belong to one user.
   */
  @ManyToOne // Defines a many-to-one relationship with User
  @JoinColumn(name = "user_id_fk", nullable = false) // Specifies the foreign key column
  private User user;

  @Column(name = "order_date", nullable = false)
  private LocalDateTime orderDate = LocalDateTime.now();

  @Column(name = "status", nullable = false, length = 20)
  @Enumerated(EnumType.STRING) // Stores enum values as strings in the database
  private OrderStatus status = OrderStatus.pending;  // Default to pending

  /**
   * One order can have many order items.
   * The `mappedBy = "order"` indicates that the `OrderItem` entity owns the relationship,
   * with the `order` field in the `OrderItem` entity holding the foreign key reference to `Order`.
   */
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems;
}
````

````java
package com.example.model.enums;

/**
 * The `OrderStatus` enum represents the different states an order can have in the system.
 * It defines four possible statuses:
 * <p>
 * - `pending`: The order has been created but not yet processed.
 * - `shipped`: The order has been shipped to the customer.
 * - `delivered`: The order has been successfully delivered to the customer.
 * - `canceled`: The order has been canceled and will not be processed.
 */
public enum OrderStatus {
  pending, shipped, delivered, canceled
}
````

- Each order is placed by **one** user, so `Order` stores a **single `User` reference**.
- The foreign key (`user_id_fk`) is **stored in the `Order` table**.
- The **ownership** of the `@OneToMany` relationship is with the `OrderItem` entity.
- When a user places an order, it links to that user using this foreign key.

---

## **3. OrderItem Entity (Join Table)**
### **Purpose:**
This entity represents an **association table** `Order_Item` between `Order` and `Item`, allowing many-to-many relationships.

A **many-to-many** relationship between `Order` and `Item` is translated into two **one-to-many** relationships through the `Order_Item` junction table. This table is responsible for storing the relationship between the `Order` and `Item` entities.

### Mapping in a junction table
The `OrderItem` entity represents a junction table with a composite primary key composed of two foreign keys: `order_id_fk` and `item_id_fk`. The steps to correctly map this entity in JPA are as follows:

- **Composite Primary Key**:
  - The primary key of the `Order_Item` table is composed of two columns: `order_id_fk` and `item_id_fk`. This requires the use of a composite key.
  - In JPA, this is represented using the `@EmbeddedId` annotation, which marks the `OrderItemKey` class as the composite primary key.

- **Using `@EmbeddedId` for Composite Key**:
  - The `@EmbeddedId` annotation is applied to a field of type `OrderItemKey`, which holds the composite key.
  - `order_id_fk` and `item_id_fk` are mapped by `order_id_pk` and `item_id_pk` in the composite key.
  - This ensures that both foreign keys are treated as part of the primary key for the entity.

- **Mapping Foreign Keys with `@ManyToOne`**:
  - The `OrderItem` entity has two `@ManyToOne` relationships: one with the `Order` entity and one with the `Item` entity. This reflects the foreign key relationship in the junction table.
  - The `@ManyToOne` annotation indicates that each `OrderItem` refers to one `Order` and one `Item`, respectively.

- **Mapping the Foreign Key Columns with `@JoinColumn`**:
  - The `@JoinColumn` annotation is used to specify the foreign key column in the `Order_Item` table. The `name` attribute in `@JoinColumn` refers to the actual column name in the database.
  - For `Order`, the foreign key column is `order_id_fk`, and for `Item`, the foreign key column is `item_id_fk`.

- **Using `@MapsId` to Link to Composite Key**:
  - The `@MapsId` annotation is used to link the `Order` and `Item` objects to the respective parts of the composite key. This ensures that the `OrderItem` entity is correctly mapped to the corresponding `Order` and `Item` objects via the composite key.
  - For example, `@MapsId("order_id_pk")` tells JPA to use the `order_id_pk` part of the composite key to map the `Order` entity, while `@MapsId("item_id_pk")` maps the `Item` entity.

### **Annotations Explained:**
- `@Entity` and `@Table(name = "Order_Item", schema = "shop_schema")` – Defines the join table.
- `@EmbeddedId` – Uses a composite primary key (`OrderItemKey`).
- `@ManyToOne`
  - **Each `OrderItem` is linked to one `Order`**.
    - The **ownership** of this relationship is with the `OrderItem` entity, as it contains the foreign key linking to `Order`.
    - The `OrderItem` entity is responsible for maintaining the reference to the `Order` entity.
  - **Each `OrderItem` is linked to one `Item`**.
    - Similarly, the **ownership** of this relationship is with the `OrderItem` entity, which holds the foreign key linking to the `Item`.
    - The `OrderItem` entity manages the foreign key to `Item`, making it the owner of the relationship.

- `@MapsId("order_id_pk")` and `@MapsId("item_id_pk")` – These annotations ensure that the `OrderItem` entity’s composite key is correctly mapped to the corresponding columns in the `Order` and `Item` entities. The `@MapsId` annotation tells Hibernate to use the specified fields (`order_id_pk` and `item_id_pk`) from the composite primary key (`OrderItemKey`) as the foreign keys for the `Order` and `Item` associations. This allows the `OrderItem` entity to correctly reference both the `Order` and `Item` entities based on their respective primary keys.
- `@JoinColumn(name = "order_id_fk")` and `@JoinColumn(name = "item_id_fk")` – These annotations define the foreign key columns in the `Order_Item` table. They specify how the `OrderItem` entity should connect to the `Order` and `Item` entities by using the respective foreign key columns (`order_id_fk` and `item_id_fk`). The foreign key columns in the `OrderItem` table are used to maintain the relationship with the `Order` and `Item` tables, ensuring the integrity of the many-to-many relationship between them.
- `@Column(name = "quantity", nullable = false)` – Ensures quantity is required.

````java
package com.example.model;

import com.example.model.key.OrderItemKey;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Order_Item", schema = "shop_schema")
@EqualsAndHashCode
@AllArgsConstructor
public class OrderItem {

  /**
   * Composite key for OrderItem.
   */
  @EmbeddedId // Marks this field as a composite primary key
  private OrderItemKey id;

  @ManyToOne // Defines a many-to-one relationship with Order
  @MapsId("order_id_pk") // Maps this field to the "order_id_pk" part of the composite key
  @JoinColumn(name = "order_id_fk") // Specifies the foreign key column
  private Order order;

  @ManyToOne // Defines a many-to-one relationship with Item
  @MapsId("item_id_pk") // Maps this field to the "item_id_pk" part of the composite key
  @JoinColumn(name = "item_id_fk", nullable = false) // Specifies the foreign key column
  private Item item;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;
}
````

### **Why is a Join Table Needed?**
- **Many orders contain many items**, and **many items can be part of multiple orders**.
- A separate `Order_Item` table handles this **many-to-many** relationship.

---

## **4. Composite Key of OrderItem Entity**
### **Purpose:**
The `OrderItemKey` class is used as a composite primary key for `OrderItem`.

The `OrderItem` entity represents a junction table with a composite primary key composed of two foreign keys: `order_id_fk` and `item_id_fk`.

`order_id_fk` and `item_id_fk` are mapped by `order_id_pk` and `item_id_pk` in the composite key.

### **Annotations Explained:**
- `@Embeddable` – Marks this class as a composite key.
- Implements `java.io.Serializable` – Ensures the key can be serialized.

````java
package com.example.model.key;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable // Marks this class as an embeddable composite key
public class OrderItemKey implements java.io.Serializable {
  private Integer order_id_pk;
  private Integer item_id_pk;
}
````

### **Why Use a Composite Key?**
- The primary key consists of **both `order_id_pk` and `item_id_pk`**, making sure **each item in an order is unique**.

---

## **5. Item Entity**
### **Purpose:**
The `Item` entity represents a **product** or **service** available for purchase in the system. It stores the essential attributes of an item, such as its name, description, and price. This entity is crucial for managing products and associating them with orders through the `Order_Item` join table.

### **Entity Mapping and Attributes:**
The `Item` entity participates in a **many-to-many** relationship with the `Order` entity via the `Order_Item` join table.

- **Many-to-Many Relationship via `OrderItem`:**
  - Each `Item` can be associated with multiple `Order` entities.
  - This relationship is implemented using a **join table** (`Order_Item`), which holds additional information like `quantity`.

- **Bidirectional Relationship (`@OneToMany` in `Item` and `@ManyToOne` in `OrderItem`):**
  - The `Item` entity **does not directly store foreign keys** to `Order`. Instead, the `Order_Item` table manages the association.
  - The `@OneToMany(mappedBy = "item")` annotation in the `Item` entity defines the relationship with `OrderItem`, indicating that one item can be present in multiple order items.
  - The `OrderItem` entity, in turn, has a `@ManyToOne` relationship with `Item`, with `@JoinColumn(name = "item_id_fk")` mapping the foreign key.

### **Annotations Explained:**
- `@Entity` and `@Table(name = "Item", schema = "shop_schema")` → Defines the `Item` table within the database schema.
- `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)` → Specifies the primary key and its auto-generation strategy.
- `@Column(name = "item_id")` - Explicitly maps the `itemId` field to the `item_id` column in the database.
- `@Column(name = "name", nullable = false, length = 100)` → Ensures that the name field is required and restricts its length.
- `@Column(name = "price", nullable = false, precision = 10, scale = 2)` → Defines monetary precision for the price column.
- `@Column(name = "stock_quantity")` - Explicitly maps the `stockQuantity` field to the `stock_quantity` column in the database.
- `@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)` → Establishes a **bidirectional** link between `Item` and `OrderItem`, ensuring item deletion cascades to its associated `OrderItem` records.

This mapping ensures that the `Item` entity is properly integrated within the system, allowing it to be linked with orders while maintaining flexibility for product management.

```java
package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Item entity representing a product in the shop system.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Item", schema = "shop_schema")
public class Item {

  /**
   * Primary key, automatically generated.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Integer itemId;

  /**
   * Name of the item.
   */
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  /**
   * Description of the item.
   */
  @Column(name = "description")
  private String description;

  /**
   * Price of the item, must be greater than zero.
   */
  @Column(name = "price", nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  /**
   * Available stock quantity, must be non-negative.
   */
  @Column(name = "stock_quantity", nullable = false)
  private Integer stockQuantity;

  /**
   * Many-to-Many relationship with Order through OrderItem.
   * An item can be part of multiple orders, and an order can contain multiple items.
   * The join table "OrderItem" maintains this relationship.
   */
  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems;
}
```

# Repository

Spring Data JPA provides a powerful abstraction for interacting with the database, offering built-in CRUD operations through the `JpaRepository` interface. By extending `JpaRepository`, we automatically inherit several methods without needing to declare them explicitly in our repository interfaces. These include:

- `save(S entity)`: Persists a given entity to the database (both insert and update).
- `findById(ID id)`: Retrieves an entity by its primary key.
- `findAll()`: Returns a list of all entities.
- `deleteById(ID id)`: Deletes an entity by its primary key.
- `delete(S entity)`: Deletes a specific entity.

For more complex queries, Spring Data JPA allows further auto-generation of queries based on method name conventions. If a method follows specific naming rules, Spring will interpret it and create the corresponding query, removing the need to manually define SQL or JPQL queries.

## Repository Interfaces

### UserRepository

This repository provides basic CRUD operations without requiring explicit method declarations. Additionally, we define two methods following Spring Data JPA’s naming conventions to auto-generate queries for retrieving users by `username` and `email`.

```java
package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The `UserRepository` interface provides basic CRUD operations for the `User` entity.
 * By extending `JpaRepository`, it automatically inherits several methods for working with the `User` entity.
 * <p>
 * Basic CRUD methods provided by JpaRepository:
 * - `save(S entity)` - Persists a given `User` entity to the database (both insert and update).
 * - `findById(ID id)` - Retrieves a `User` entity by its primary key (`userId` in this case).
 * - `findAll()` - Returns a list of all `User` entities.
 * - `deleteById(ID id)` - Deletes a `User` entity by its primary key (`userId`).
 * - `delete(S entity)` - Deletes a specific `User` entity.
 * </p>
 * <p>
 * The methods `findByUsername` and `findByEmail` demonstrate how Spring Data JPA uses the method name conventions to automatically
 * generate queries. These conventions allow for creating custom queries without needing to explicitly define SQL or JPQL.
 * </p>
 * <h3>Method Name Conventions:</h3>
 * Spring Data JPA interprets method names based on certain conventions to automatically generate queries.
 * Some examples:
 * - `findBy` indicates a query to retrieve data.
 * - `By` separates the property name(s) that are part of the query.
 * - For example, `findByUsername(String username)` generates a query that searches for a `User` entity where the `username` field matches the provided value.
 * - The method names can also use logical operators like `And`, `Or`, `Between`, etc., to build more complex queries (e.g. `findByUsernameAndEmail`).
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  /**
   * Retrieves a `User` by their unique `username`.
   * This method is automatically implemented by Spring Data JPA based on the method name convention.
   * Spring Data JPA generates a query like: `SELECT u FROM User u WHERE u.username = ?1`
   *
   * @param username The username of the user to find.
   * @return An `Optional` containing the `User` with the given `username`, or empty if no user is found.
   */
  Optional<User> findByUsername(String username);

  /**
   * Retrieves a `User` by their unique `email`.
   * This method is automatically implemented by Spring Data JPA based on the method name convention.
   * Spring Data JPA generates a query like: `SELECT u FROM User u WHERE u.email = ?1`
   *
   * @param email The email of the user to find.
   * @return An `Optional` containing the `User` with the given `email`, or empty if no user is found.
   */
  Optional<User> findByEmail(String email);
}
```

### OrderRepository

This repository also provides basic CRUD operations without needing explicit method declarations. Additionally, we define a method using method name conventions to auto-generate a query for retrieving orders by `userId`.

```java
package com.example.repository;

import com.example.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The `OrderRepository` interface provides basic CRUD operations for the `Order` entity.
 * By extending `JpaRepository`, it inherits several methods for working with the `Order` entity, including:
 * <ul>
 *     <li>`save(S entity)` - Saves or updates an `Order` entity in the database.</li>
 *     <li>`findById(ID id)` - Finds an `Order` entity by its primary key (`orderId`).</li>
 *     <li>`findAll()` - Retrieves a list of all `Order` entities.</li>
 *     <li>`deleteById(ID id)` - Deletes an `Order` entity by its primary key (`orderId`).</li>
 *     <li>`delete(S entity)` - Deletes a specific `Order` entity.</li>
 * </ul>
 *
 * <p>
 * The method `findByUser_UserId` demonstrates how Spring Data JPA generates queries automatically
 * by interpreting method names based on naming conventions.
 * Spring Data JPA translates method names into SQL or JPQL queries at runtime.
 * </p>
 *
 * <h3>Method Name Conventions:</h3>
 * Spring Data JPA uses method name conventions to generate queries dynamically. The naming conventions follow these rules:
 * <ul>
 *     <li>`findBy` - This prefix indicates that the method will retrieve data.</li>
 *     <li>The next part of the method name specifies the field or property that the query will filter by.</li>
 *     <li>If you have a relationship between entities (e.g. `Order` and `User`), you can traverse the relationships by including the related entity's property in the method name.</li>
 *     <li>For example, `User` refers to the `User` entity, and `UserId` refers to the `userId` property in the `User` entity, which is mapped as a foreign key in the `Order` entity via the `user` field.</li>
 * </ul>
 *
 * <h3>Query Generation:</h3>
 * Spring Data JPA will automatically generate a query like:
 * <pre>
 *     SELECT o FROM Order o WHERE o.user.userId = ?1
 * </pre>
 * This query selects `Order` entities where the `userId` of the associated `User` matches the provided `userId`.
 *
 * <h3>Return Type:</h3>
 * Since the method returns a `List<Order>`, it will return an empty list if no matching orders are found for the given `userId`.
 * <br>
 * There's no need to wrap the result in `Optional` for a `List`, as `List` can always be returned, even if it's empty.
 * </p>
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

  /**
   * Retrieves a list of `Order` entities associated with a specific `userId`.
   * Spring Data JPA automatically implements this method based on the name, generating a query
   * like: `SELECT o FROM Order o WHERE o.user.userId = ?1`
   *
   * @param userId The `userId` of the `User` whose orders are being queried.
   * @return A list of `Order` entities related to the given `userId`.
   *         Returns an empty list if no orders are found.
   */
  List<Order> findByUser_UserId(Integer userId);
}
```

### ItemRepository

This repository provides all standard CRUD operations inherited from `JpaRepository`, so there is no need to explicitly declare any methods.

```java
package com.example.repository;

import com.example.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
```

### OrderItemRepository

Like `ItemRepository`, this repository also provides standard CRUD operations without requiring explicit method declarations.

```java
package com.example.repository;

import com.example.model.OrderItem;
import com.example.model.key.OrderItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemKey> {
}
```

By leveraging Spring Data JPA, these repositories simplify database interactions while still allowing for more advanced queries when needed. Basic CRUD operations are always available without additional method declarations, and further queries can be auto-generated using method name conventions where necessary.

# Service

In a Spring Boot application, the service layer plays a crucial role in orchestrating business logic and interacting with the repository layer. The service layer is where the core logic of your application resides, and it acts as an intermediary between the controllers and repositories. This separation ensures a clean architecture and improves maintainability.

## Overview of the Service Layer

The service layer in Spring Boot typically provides methods that encapsulate CRUD (Create, Read, Update, Delete) operations for each entity. These methods interact with the repositories, which handle database interactions, while the service layer handles business rules and transformations.

For this application, we have created service classes for different entities, such as `User`, `Order`, `Item`, and `OrderItem`. These services abstract away the database interactions, ensuring that the controllers can focus solely on HTTP-related logic.

Each service class uses the corresponding repository to perform CRUD operations, which means the data is persisted, retrieved, and manipulated through the repository interfaces automatically provided by Spring Data JPA. The repositories, in turn, use Hibernate to manage the database operations.

---

## UserService

The `UserService` class is responsible for handling the business logic related to the `User` entity. It acts as a mediator between the controller layer and the `UserRepository`, enabling the application to manage user-related operations such as creating, retrieving, updating, and deleting users.

The service class provides several methods, including:
- **saveUser**: Used to create or update a user.
- **getAllUsers**: Retrieves a list of all users in the system.
- **getUserById**: Fetches a user by their unique ID.
- **getUserByUsername**: Retrieves a user by their username.
- **getUserByEmail**: Fetches a user by their email.
- **deleteUser**: Deletes a user based on their ID.

This separation ensures that the business logic is encapsulated in the service, making the application more modular and easier to maintain.

````java
package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // Create or update a User
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  // Retrieve all Users
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  // Retrieve a User by ID
  public Optional<User> getUserById(Integer userId) {
    return userRepository.findById(userId);
  }

  // Retrieve a User by Username
  public Optional<User> getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  // Retrieve a User by Email
  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  // Delete a User by ID
  public void deleteUser(Integer userId) {
    userRepository.deleteById(userId);
  }
}
````

---

## OrderService

The `OrderService` class is designed to handle CRUD operations for the `Order` entity. It provides methods to create, retrieve, and delete orders. Additionally, it has a specific method to fetch all orders associated with a particular user.

The service class includes the following methods:
- **saveOrder**: Creates or updates an order.
- **getAllOrders**: Retrieves all orders from the database.
- **getOrderById**: Fetches an order by its unique ID.
- **getOrdersByUserId**: Retrieves a list of orders placed by a specific user.
- **deleteOrder**: Deletes an order by its ID.

By using the `OrderRepository`, the service abstracts database interactions, allowing for more readable and maintainable code. This ensures that complex logic related to order processing and retrieval is handled within the service layer.

````java
package com.example.service;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

  private final OrderRepository orderRepository;

  @Autowired
  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  // Create or update an Order
  public Order saveOrder(Order order) {
    return orderRepository.save(order);
  }

  // Retrieve all Orders
  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  // Retrieve an Order by ID
  public Optional<Order> getOrderById(Integer orderId) {
    return orderRepository.findById(orderId);
  }

  // Retrieve Orders by User ID
  public List<Order> getOrdersByUserId(Integer userId) {
    return orderRepository.findByUser_UserId(userId);
  }

  // Delete an Order by ID
  public void deleteOrder(Integer orderId) {
    orderRepository.deleteById(orderId);
  }
}
````

---

## ItemService

The `ItemService` class is responsible for managing the `Item` entity. This service provides methods for CRUD operations on items and abstracts away the complexity of interacting directly with the database. It is designed to work with the `ItemRepository` and supports managing products in the system.

Key methods in this service include:
- **saveItem**: Creates or updates an item in the database.
- **getAllItems**: Retrieves all items available in the system.
- **getItemById**: Fetches an item by its unique ID.
- **deleteItem**: Deletes an item by its ID.

The `ItemService` ensures that the business logic for managing inventory or items in the system is separated from the controller, providing a cleaner and more scalable solution for the application.

````java
package com.example.service;

import com.example.model.Item;
import com.example.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

  private final ItemRepository itemRepository;

  @Autowired
  public ItemService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  // Create or update an Item
  public Item saveItem(Item item) {
    return itemRepository.save(item);
  }

  // Retrieve all Items
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

  // Retrieve an Item by ID
  public Optional<Item> getItemById(Integer itemId) {
    return itemRepository.findById(itemId);
  }

  // Delete an Item by ID
  public void deleteItem(Integer itemId) {
    itemRepository.deleteById(itemId);
  }
}
````

---

## OrderItemService

The `OrderItemService` class handles the CRUD operations for the `OrderItem` entity, which is a join entity between `Order` and `Item`. This service provides methods for managing the items within orders, such as adding, updating, or deleting order items.

The service includes the following methods:
- **saveOrderItem**: Creates or updates an order item.
- **getAllOrderItems**: Retrieves all order items from the database.
- **getOrderItemById**: Fetches an order item based on its composite key.
- **deleteOrderItem**: Deletes an order item using its composite key.

The service layer abstracts the complexity of handling the relationships between orders and items, ensuring that business logic regarding order items is centralized and maintainable.

````java
package com.example.service;

import com.example.model.OrderItem;
import com.example.model.key.OrderItemKey;
import com.example.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

  private final OrderItemRepository orderItemRepository;

  @Autowired
  public OrderItemService(OrderItemRepository orderItemRepository) {
    this.orderItemRepository = orderItemRepository;
  }

  // Create or update an OrderItem
  public OrderItem saveOrderItem(OrderItem orderItem) {
    return orderItemRepository.save(orderItem);
  }

  // Retrieve all OrderItems
  public List<OrderItem> getAllOrderItems() {
    return orderItemRepository.findAll();
  }

  // Retrieve an OrderItem by ID (using composite key)
  public Optional<OrderItem> getOrderItemById(OrderItemKey orderItemKey) {
    return orderItemRepository.findById(orderItemKey);
  }

  // Delete an OrderItem by ID (using composite key)
  public void deleteOrderItem(OrderItemKey orderItemKey) {
    orderItemRepository.deleteById(orderItemKey);
  }
}
````

---

# Mapper Classes for REST Messages

## Introduction

When designing request and response bodies for a RESTful API, the first step is to carefully consider the operations you want to allow and the actions you want to expose to clients through HTTP methods (such as GET, POST, PUT, PATCH, and DELETE). Defining these operations ensures that the API structure aligns with business needs and provides clarity on data exchange between clients and the server.

### Why This Matters

#### Operations Dictate the Structure
Each API operation determines the fields required in requests and the data returned in responses:
- A **POST** request to create a resource typically includes all necessary fields for creating a new record (e.g. `name`, `price`, `quantity` for an item).
- A **GET** request generally requires only an identifier (e.g. `itemId`) to fetch specific resource details.
- A **PUT** or **PATCH** request updates a resource, requiring relevant fields and an identifier.
- A **DELETE** request usually requires an identifier to remove the resource.

#### Key Considerations
- **Request Bodies:** What data should the client send? Are validations needed (e.g. required fields, length constraints, data format)? Which fields should be read-only or auto-generated?
- **Response Bodies:** What data should be returned after an operation? Should related entities be included (e.g. `OrderItem` in an `Order`)? Should sensitive data be excluded (e.g. passwords)?

### Supported Operations in Our API

The following operations are supported in our system:

#### **User Management**
- Create a new user
- Modify a user’s username and/or email
- Remove a user
- Retrieve user details

#### **Order Management**
- Retrieve an order
- Create an order (initially without items)
- Delete an order

#### **Item Management**
- Create an item
- Retrieve an item
- Modify an item’s name, description, or price

#### **Order-Item Management**
- Add an item to an order
- Remove an item from an order

### Operations That Are Disallowed
- Users cannot modify their ID or other users’ data.
- Orders cannot be updated once created (modifications happen through item additions/removals).
- Items cannot be deleted, only modified.
- An order’s status cannot be manually changed—it follows predefined business logic.

## Request and Response Mappings

### **User Requests and Responses**
#### **UserRequest**
Defines the payload for creating or updating a user, requiring a `username` and `email`.

**Note that no `userId` is passed because `username` is `UNIQUE`; that is, it identifies a user uniquely.**

````java
package com.example.rest.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Request class for creating or updating a User.
 * Used when sending a POST or PUT request to create or modify a User.
 */
@Getter
@Setter
public class UserRequest {

  @NotBlank(message = "Username cannot be blank")
  @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
  @JsonProperty("username") // Jackson annotation for property binding
  private String username;

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email should be valid")
  @JsonProperty("email") // Jackson annotation for property binding
  private String email;
}
````

##### Example JSON Request:
```json
{
  "username": "john_doe",
  "email": "john.doe@example.com"
}
```

#### **UserResponse**
Returns user details including `userId`, `username`, `email`, and `createdAt`.

````java
package com.example.rest.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Response class for retrieving User details.
 * Used for:
 * - GET requests to retrieve a User;
 * - response when sending a POST or PUT request to create or modify a User.
 */
@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

  @JsonProperty("user_id")
  private Integer userId;

  @JsonProperty("username")
  private String username;

  @JsonProperty("email")
  private String email;

  @JsonProperty("created_at")
  private String createdAt; // Represented as a String for easy JSON formatting
}
````

##### Example JSON Response:
```json
{
  "user_id": 1,
  "username": "john_doe",
  "email": "john.doe@example.com",
  "created_at": "2024-02-29T12:34:56Z"
}
```

#### **RemoveUserRequest**
Specifies the `userId` required for user deletion.

````java
package com.example.rest.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request class for removing a User.
 * Used when sending a DELETE request to remove a User by their ID.
 */
@Getter
@Setter
public class RemoveUserRequest {

  @NotNull(message = "User ID cannot be null")
  @JsonProperty("user_id")
  private Integer userId;
}
````

##### Example JSON Request:
```json
{
  "user_id": 1
}
```

#### **RemoveUserResponse**
Confirms the user’s deletion with `userId` and a message.

```java
package com.example.rest.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Response class for removing a User.
 * This response will confirm that a User has been successfully removed from the system.
 */
@Getter
@Setter
@AllArgsConstructor
public class RemoveUserResponse {

  @JsonProperty("user_id")
  private Integer userId;

  @JsonProperty("message")
  private String message;
}
```

##### Example JSON Response:
```json
{
  "user_id": 1,
  "message": "User successfully removed."
}
```

### **Order Requests and Responses**
#### **OrderRequest**
Defines the payload for creating an order, requiring a `userId`.

```java
package com.example.rest.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request class for creating an Order.
 * Used when sending a POST request to create a new Order.
 */
@Getter
@Setter
public class OrderRequest {

  @NotNull(message = "User ID cannot be null")
  @JsonProperty("user_id")
  private Integer userId;
}
```

##### Example JSON Request:
```json
{
  "user_id": 1
}
```

#### **OrderResponse**
Returns order details including `orderId`, `orderDate`, `status`, `userId`, and `orderItems`.

```java
package com.example.rest.response.order;

import com.example.model.OrderItem;
import com.example.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response class for retrieving Order details.
 * Used for:
 * - GET requests to retrieve an Order;
 * - response when sending a POST request to create a new Order.
 */
@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {

  @JsonProperty("order_id")
  private Integer orderId;

  @JsonProperty("order_date")
  private LocalDateTime orderDate;

  @JsonProperty("status")
  private OrderStatus status;

  @JsonProperty("user_id")
  private Integer userId;

  @JsonProperty("order_items")
  private List<OrderItem> orderItems;
}
```

##### Example JSON Response:
```json
{
  "order_id": 100,
  "order_date": "2024-02-29T14:00:00Z",
  "status": "pending",
  "user_id": 1,
  "order_items": []
}
```

#### **RemoveOrderRequest**
Specifies the `orderId` required for order deletion.

````java
package com.example.rest.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request class for removing an Order.
 * Used when sending a DELETE request to remove an Order by its ID.
 */
@Getter
@Setter
public class RemoveOrderRequest {

  @NotNull(message = "Order ID cannot be null")
  @JsonProperty("order_id")
  private Integer orderId;
}
````

##### Example JSON Request:
```json
{
  "order_id": 100
}
```

#### **RemoveOrderResponse**
Confirms the order’s deletion with `orderId` and a message.

````java
package com.example.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Response class for removing an Order.
 * This response will confirm that an Order has been successfully removed from the system.
 */
@Getter
@Setter
@AllArgsConstructor
public class RemoveOrderResponse {

  @JsonProperty("order_id")
  private Integer orderId;

  @JsonProperty("message")
  private String message;
}
````
##### Example JSON Response:
```json
{
  "order_id": 100,
  "message": "Order successfully removed."
}
```

### **Item Requests and Responses**
#### **ItemRequest**
Defines the payload for creating or modifying an item, including `itemId`, `name`, `description`, and `price`.

**Note that `itemId` is annotated with `@Nullable` because, when creating an item, it responsibility of the web service to assign it uniquely;
it will be passed only when an existing item has to be modified.**

````java
package com.example.rest.request.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * Request class for modifying an Item's details.
 * Used when sending a POST or PUT request to create or modify an Item.
 */
@Getter
@Setter
public class ItemRequest {

  @Nullable
  @JsonProperty("item_id")
  private Integer itemId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @Positive(message = "Price must be positive")
  @JsonProperty("price")
  private Double price;
}
````

##### Example JSON Request:
```json
{
  "name": "Laptop",
  "description": "High-end gaming laptop",
  "price": 1500.99
}
```

#### **ItemResponse**
Returns item details including `itemId`, `name`, `description`, `price`, and `stockQuantity`.

````java
package com.example.rest.response.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Response class for retrieving Item details.
 * Used for:
 * - GET requests to retrieve an Item;
 * - response when sending a POST or PUT request to create or modify an Item.
 */
@Getter
@Setter
@AllArgsConstructor
public class ItemResponse {

  @JsonProperty("item_id")
  private Integer itemId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("price")
  private Double price;

  @JsonProperty("stock_quantity")
  private Integer stockQuantity;
}
````

##### Example JSON Response:
```json
{
  "item_id": 200,
  "name": "Laptop",
  "description": "High-end gaming laptop",
  "price": 1500.99,
  "stock_quantity": 10
}
```

### **Order-Item Requests and Responses**
#### **AddItemToOrderRequest**
Specifies the `orderId`, `itemId`, and `quantity` required to add an item to an order.

````java
package com.example.rest.request.orderitem;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * Request class for adding an Item to an Order.
 * Used when sending a POST request to add an Item to an Order.
 */
@Getter
@Setter
public class AddItemToOrderRequest {

  @NotNull(message = "Order ID cannot be null")
  @JsonProperty("order_id")
  private Integer orderId;

  @NotNull(message = "Item ID cannot be null")
  @JsonProperty("item_id")
  private Integer itemId;

  @Positive(message = "Quantity must be greater than zero")
  @JsonProperty("quantity")
  private Integer quantity;
}
````

##### Example JSON Request:
```json
{
  "order_id": 100,
  "item_id": 200,
  "quantity": 2
}
```

#### **AddItemToOrderResponse**
Confirms the addition of an item to an order with `orderId`, `itemId`, `quantity`, and a message.

````java
package com.example.rest.response.orderitem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Response class for adding an Item to an Order.
 * This response will be used to confirm that an item has been successfully added to an order.
 */
@Getter
@Setter
@AllArgsConstructor
public class AddItemToOrderResponse {

  @JsonProperty("order_id")
  private Integer orderId;

  @JsonProperty("item_id")
  private Integer itemId;

  @JsonProperty("quantity")
  private Integer quantity;

  @JsonProperty("message")
  private String message;
}
````

##### Example JSON Response:
```json
{
  "order_id": 100,
  "item_id": 200,
  "quantity": 2,
  "message": "Item successfully added to order."
}
```

#### **RemoveItemFromOrderRequest**
Specifies the `orderId` and `itemId` required to remove an item from an order.

````java
package com.example.rest.request.orderitem;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Request class for deleting an Item from an Order.
 * Used when sending a DELETE request to remove an Item from an Order.
 */
@Getter
@Setter
public class RemoveItemFromOrderRequest {

  @NotNull(message = "Order ID cannot be null")
  @JsonProperty("order_id")
  private Integer orderId;

  @NotNull(message = "Item ID cannot be null")
  @JsonProperty("item_id")
  private Integer itemId;
}
````

##### Example JSON Request:
```json
{
  "order_id": 100,
  "item_id": 200
}
```

#### **RemoveItemFromOrderResponse**
Confirms the removal of an item from an order with `orderId`, `itemId`, and a message.

````java
package com.example.rest.response.orderitem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Response class for deleting an Item from an Order.
 * This response will confirm that an item has been successfully removed from the order.
 */
@Getter
@Setter
@AllArgsConstructor
public class RemoveItemFromOrderResponse {

  @JsonProperty("order_id")
  private Integer orderId;

  @JsonProperty("item_id")
  private Integer itemId;

  @JsonProperty("message")
  private String message;
}
````

##### Example JSON Response:
```json
{
  "order_id": 100,
  "item_id": 200,
  "message": "Item successfully removed from order."
}
```

---

# Controller

## ResponseEntity<T>

`ResponseEntity<T>` is a generic class in Spring that represents an HTTP response, including its status code, headers, and body. It is used to build responses in a RESTful API, allowing developers to specify the exact status code and payload for the response. This class is a powerful way to work with HTTP responses in Spring-based web applications.

### Key Features:
- **Status Codes**: Allows you to specify various HTTP status codes like `200 OK`, `404 Not Found`, `201 Created`, etc.
- **Body**: The body can be any object, which will be serialized to JSON (or other formats) and included in the response.
- **Headers**: You can also include custom HTTP headers in the response.

### Common Methods of `ResponseEntity<T>`

- **`ResponseEntity.ok(T body)`**:
  - Returns a response with the `200 OK` HTTP status code and the provided body.
  - Example: `ResponseEntity.ok(response)`.

- **`ResponseEntity.notFound().build()`**:
  - Returns a `404 Not Found` HTTP status without a body. It indicates that the requested resource could not be found.
  - Example: `ResponseEntity.notFound().build()`.

- **`ResponseEntity.status(HttpStatus status)`**:
  - Allows you to specify a custom HTTP status code for the response. You can combine it with a body, if needed.
  - Example: `ResponseEntity.status(HttpStatus.CREATED).body(response)`.

- **`ResponseEntity.noContent().build()`**:
  - Returns a `204 No Content` response with no body, typically used for successful operations like deletes.
  - Example: `ResponseEntity.noContent().build()`.

- **`ResponseEntity.badRequest().body(T body)`**:
  - Returns a `400 Bad Request` response with the provided body, commonly used to signal invalid input.
  - Example: `ResponseEntity.badRequest().body(errorResponse)`.

### Common Annotations in SpringBoot

Spring provides several annotations for mapping HTTP requests to controller methods. Below is a list of the commonly used annotations:

- **`@GetMapping`**:
  - Maps HTTP GET requests to the specified handler method.
  - Commonly used for retrieving resources or querying data.
  - Example: `@GetMapping("/users")`.

- **`@PostMapping`**:
  - Maps HTTP POST requests to the specified handler method.
  - Used for creating or updating resources.
  - Example: `@PostMapping("/users")`.

- **`@PutMapping`**:
  - Maps HTTP PUT requests to the specified handler method.
  - Typically used for updating an existing resource.
  - Example: `@PutMapping("/users")`.

- **`@DeleteMapping`**:
  - Maps HTTP DELETE requests to the specified handler method.
  - Used for deleting a resource.
  - Example: `@DeleteMapping("/users/{id}")`.

- **`@RequestParam`**:
  - Binds request parameters (from the query string) to method parameters in the controller.
  - Example: `@RequestParam Integer id`.

- **`@RequestBody`**:
  - Binds the HTTP request body to a method parameter, commonly used with POST, PUT, and PATCH requests.
  - Example: `@RequestBody UserRequest userRequest`.

- **`@PathVariable`**:
  - Binds path variables in the URL to method parameters.
  - Example: `@PathVariable("id") Integer id`.

- **`@RequestMapping`**:
  - The most general annotation for mapping HTTP requests to handler methods.
  - It can be used with various HTTP methods and provides flexibility for defining request mappings.
  - Example: `@RequestMapping("/users")`.

### Usage of `ResponseEntity` in Controller

In a Spring-based REST API, `ResponseEntity` is commonly used to return responses with different HTTP status codes and bodies. The following are some examples of how `ResponseEntity` can be used in controller methods:

- **Successful Creation (`201 Created`)**:
  - When a new resource is created (such as a user), the response should return a `201 Created` status code, often along with the newly created resource.

- **Successful Retrieval (`200 OK`)**:
  - When a resource is retrieved successfully, `200 OK` is returned along with the resource’s details.

- **Resource Not Found (`404 Not Found`)**:
  - If the requested resource is not found (e.g. when querying by `id`, `email`, or `username`), a `404 Not Found` response is returned without a body.

- **Bad Request (`400 Bad Request`)**:
  - If the client sends an invalid request (e.g. missing required fields), a `400 Bad Request` status code is returned, often with error details in the response body.

## UserController

The `UserController` class handles HTTP requests related to user operations within the application. It exposes various endpoints to create, modify, retrieve, and delete users. This controller interacts with the `UserService` to manage user-related logic and responds with appropriate status codes and data wrapped in `ResponseEntity`.

### Controller Annotations

- **`@RestController`**: Indicates that this class is a RESTful controller, capable of handling HTTP requests and returning responses.
- **`@RequestMapping("/shop/users")`**: This defines the base URL path for all user-related operations. All methods in this controller will be accessible under this base path.

### Endpoints Overview

- **Create a new user** (`POST /shop/users`)
  - **HTTP Method**: POST
  - **Description**: Creates a new user with the provided username and email.
  - **Response**: Returns a `201 Created` status with the created user's details in the response body.

- **Update an existing user** (`PUT /shop/users`)
  - **HTTP Method**: PUT
  - **Description**: Modifies an existing user's username and/or email.
  - **Response**: Returns a `200 OK` status with the updated user's details. If the user is not found, it returns a `404 Not Found`.

- **Retrieve user by ID** (`GET /shop/users?id=<id>`)
  - **HTTP Method**: GET
  - **Description**: Retrieves a user by their unique `id`.
  - **Response**: Returns a `200 OK` status with the user details if found, otherwise a `404 Not Found`.

- **Retrieve user by email** (`GET /shop/users?email=<email>`)
  - **HTTP Method**: GET
  - **Description**: Retrieves a user by their email.
  - **Response**: Returns a `200 OK` status with the user details if found, otherwise a `404 Not Found`.

- **Retrieve user by username** (`GET /shop/users?username=<username>`)
  - **HTTP Method**: GET
  - **Description**: Retrieves a user by their username.
  - **Response**: Returns a `200 OK` status with the user details if found, otherwise a `404 Not Found`.

- **Delete a user** (`DELETE /shop/users`)
  - **HTTP Method**: DELETE
  - **Description**: Deletes a user by their ID.
  - **Response**: Returns a `200 OK` status with a success message if the user is deleted, or a `404 Not Found` if the user does not exist.

### HTTP Methods Used

- **`@PostMapping`**: Maps HTTP POST requests to the `createUser` method to create a new user.
- **`@PutMapping`**: Maps HTTP PUT requests to the `updateUser` method to update an existing user's details.
- **`@GetMapping`**: Maps HTTP GET requests to retrieve user details by ID, email, or username.
- **`@DeleteMapping`**: Maps HTTP DELETE requests to the `deleteUser` method to remove a user.

### Detailed Breakdown of Methods

#### `createUser()`
- **URL**: `/shop/users`
- **Method**: POST
- **Request Body**: The user details in the form of a `UserRequest` object (containing `username` and `email`).
- **Response**: Returns a `200 OK` status with a `UserResponse` object containing the created user's ID, username, email, and creation timestamp.

#### `updateUser()`
- **URL**: `/shop/users`
- **Method**: PUT
- **Request Body**: The updated user details in the form of a `UserRequest` object (containing `username` and `email`).
- **Response**: Returns a `200 OK` status with a `UserResponse` object containing the updated user's details if the user exists. If the user is not found, it returns a `404 Not Found`.

#### `getUserById()`
- **URL**: `/shop/users?id=<id>`
- **Method**: GET
- **Request Parameter**: `id` (integer)
- **Response**: Returns a `200 OK` status with a `UserResponse` object containing the user's details if found. If the user does not exist, returns `404 Not Found`.

#### `getUserByEmail()`
- **URL**: `/shop/users?email=<email>`
- **Method**: GET
- **Request Parameter**: `email` (string)
- **Response**: Returns a `200 OK` status with a `UserResponse` object containing the user's details if found. If the user does not exist, returns `404 Not Found`.

#### `getUserByUsername()`
- **URL**: `/shop/users?username=<username>`
- **Method**: GET
- **Request Parameter**: `username` (string)
- **Response**: Returns a `200 OK` status with a `UserResponse` object containing the user's details if found. If the user does not exist, returns `404 Not Found`.

#### `deleteUser()`
- **URL**: `/shop/users`
- **Method**: DELETE
- **Request Body**: A `RemoveUserRequest` object containing the user's ID to be deleted.
- **Response**: Returns a `200 OK` status with a `RemoveUserResponse` object containing a success message if the user is deleted. If the user does not exist, it returns `404 Not Found`.

### Path Variables vs Query Parameters

In this controller, **query parameters** are used for retrieving users based on different criteria like `id`, `email`, or `username`. This allows the API to support multiple types of search queries through the same `GET` endpoint. This is similar to the `OrderController` approach, where query parameters allow filtering based on specific fields, which is useful when there are multiple retrieval criteria.

However, when only one unique resource is being accessed by a single identifier (like `userId` or `itemId`), a **path variable** is preferred, as it provides a cleaner and more intuitive URL structure. For example, if there were only one `GET` method to retrieve a user by their `userId`, a path variable would be the ideal choice. This is why we used query parameters in this case for flexibility across different retrieval methods.

````java
package com.example.controller;

import com.example.model.User;
import com.example.rest.request.user.RemoveUserRequest;
import com.example.rest.request.user.UserRequest;
import com.example.rest.response.user.RemoveUserResponse;
import com.example.rest.response.user.UserResponse;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/shop/users")  // Base path for user-related operations
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  // Create a new user
  @PostMapping
  public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
    User user = new User();
    user.setUsername(userRequest.getUsername());
    user.setEmail(userRequest.getEmail());

    User savedUser = userService.saveUser(user);

    UserResponse response = new UserResponse(
            savedUser.getUserId(),
            savedUser.getUsername(),
            savedUser.getEmail(),
            savedUser.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME)
    );

    return ResponseEntity.ok(response);
  }

  // Modify an existing user (update username and/or email)
  @PutMapping
  public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequest userRequest) {

    Optional<User> existingUser = userService.getUserByEmail(userRequest.getEmail());
    if (existingUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    User user = existingUser.get();
    user.setUsername(userRequest.getUsername());
    user.setEmail(userRequest.getEmail());

    User updatedUser = userService.saveUser(user);

    UserResponse response = new UserResponse(
            updatedUser.getUserId(),
            updatedUser.getUsername(),
            updatedUser.getEmail(),
            updatedUser.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME)
    );

    return ResponseEntity.ok(response);
  }

  // Retrieve user by ID (query parameter)
  @GetMapping(params = "id")
  public ResponseEntity<UserResponse> getUserById(@RequestParam Integer id) {
    Optional<User> user = userService.getUserById(id);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    UserResponse response = new UserResponse(
            user.get().getUserId(),
            user.get().getUsername(),
            user.get().getEmail(),
            user.get().getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME)
    );

    return ResponseEntity.ok(response);
  }

  // Retrieve user by email (query parameter)
  @GetMapping(params = "email")
  public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
    Optional<User> user = userService.getUserByEmail(email);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    UserResponse response = new UserResponse(
            user.get().getUserId(),
            user.get().getUsername(),
            user.get().getEmail(),
            user.get().getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME)
    );

    return ResponseEntity.ok(response);
  }

  // Retrieve user by username (query parameter)
  @GetMapping(params = "username")
  public ResponseEntity<UserResponse> getUserByUsername(@RequestParam String username) {
    Optional<User> user = userService.getUserByUsername(username);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    UserResponse response = new UserResponse(
            user.get().getUserId(),
            user.get().getUsername(),
            user.get().getEmail(),
            user.get().getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME)
    );

    return ResponseEntity.ok(response);
  }

  // Remove a user (delete)
  @DeleteMapping
  public ResponseEntity<RemoveUserResponse> deleteUser(@RequestBody @Valid RemoveUserRequest request) {
    Optional<User> user = userService.getUserById(request.getUserId());
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    userService.deleteUser(request.getUserId());

    RemoveUserResponse response = new RemoveUserResponse(
            request.getUserId(),
            "User deleted successfully"
    );

    return ResponseEntity.ok(response);
  }
}
````

## OrderController

The `OrderController` class handles HTTP requests related to order operations within the application. It exposes various endpoints to create, retrieve, and delete orders. This controller interacts with the `OrderService` to manage order-related logic and responds with appropriate status codes and data wrapped in `ResponseEntity`.

### Controller Annotations

- **`@RestController`**: Indicates that this class is a RESTful controller, capable of handling HTTP requests and returning responses.
- **`@RequestMapping("/shop/orders")`**: This defines the base URL path for all order-related operations. All methods in this controller will be accessible under this base path.

### Endpoints Overview

- **Create a new order** (`POST /shop/orders`)
  - **HTTP Method**: POST
  - **Description**: Creates a new order associated with the user provided in the request. Initially, the order does not have any items.
  - **Response**: Returns a `200 OK` status with the created order's details in the response body.

- **Retrieve an order by ID** (`GET /shop/orders?orderId=<orderId>`)
  - **HTTP Method**: GET
  - **Description**: Retrieves an order by its unique `orderId`.
  - **Response**: Returns a `200 OK` status with the order's details if found, otherwise a `404 Not Found`.

- **Retrieve orders by user ID** (`GET /shop/orders?userId=<userId>`)
  - **HTTP Method**: GET
  - **Description**: Retrieves a list of orders associated with a specific user.
  - **Response**: Returns a `200 OK` status with a list of `OrderResponse` objects containing the order details if orders are found. If no orders exist for the given user, it returns a `404 Not Found`.

- **Delete an order by ID** (`DELETE /shop/orders`)
  - **HTTP Method**: DELETE
  - **Description**: Deletes an order by its `orderId`.
  - **Response**: Returns a `200 OK` status with a success message if the order is deleted, or a `404 Not Found` if the order does not exist.

### HTTP Methods Used

- **`@PostMapping`**: Maps HTTP POST requests to the `createOrder` method to create a new order.
- **`@GetMapping`**: Maps HTTP GET requests to retrieve order details by ID or to fetch orders associated with a particular user.
- **`@DeleteMapping`**: Maps HTTP DELETE requests to the `deleteOrder` method to remove an order.

### Detailed Breakdown of Methods

#### `createOrder()`
- **URL**: `/shop/orders`
- **Method**: POST
- **Request Body**: The order details in the form of an `OrderRequest` object, containing the `userId` of the user placing the order.
- **Response**: Returns a `200 OK` status with an `OrderResponse` object containing the created order's details, including order ID, date, status, user ID, and order items (if available).

#### `getOrderById()`
- **URL**: `/shop/orders?orderId=<orderId>`
- **Method**: GET
- **Request Parameter**: `orderId` (integer)
- **Response**: Returns a `200 OK` status with an `OrderResponse` object containing the order details if found. If the order does not exist, returns `404 Not Found`.

#### `getOrdersByUserId()`
- **URL**: `/shop/orders?userId=<userId>`
- **Method**: GET
- **Request Parameter**: `userId` (integer)
- **Response**: Returns a `200 OK` status with a list of `OrderResponse` objects for the orders associated with the given `userId`. If no orders exist for the user, returns `404 Not Found`.

#### `deleteOrder()`
- **URL**: `/shop/orders`
- **Method**: DELETE
- **Request Body**: A `RemoveOrderRequest` object containing the `orderId` of the order to be deleted.
- **Response**: Returns a `200 OK` status with a `RemoveOrderResponse` object containing a success message if the order is deleted. If the order does not exist, it returns `404 Not Found`.

### Path Variables vs Query Parameters

In this controller, we use **query parameters** in the `GET` methods for retrieving orders by `orderId` or `userId` because there are two distinct retrieval operations. Query parameters are helpful here for specifying different search criteria, making it easy to extend the API with additional filters (e.g. order status or date ranges).

In contrast, when we had only one `GET` mapping to retrieve an item by its unique ID, we used a **path variable** because there was no need for multiple retrieval methods. Path variables are ideal when accessing a single resource by its unique identifier. For example, we used a path variable in the `ItemController` for retrieving an item by its `itemId`, which makes it simpler and more intuitive for this case.

````java
package com.example.controller;

import com.example.model.Order;
import com.example.model.User;
import com.example.rest.request.order.OrderRequest;
import com.example.rest.request.order.RemoveOrderRequest;
import com.example.rest.response.RemoveOrderResponse;
import com.example.rest.response.order.OrderResponse;
import com.example.service.OrderService;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shop/orders")  // Class-level mapping
public class OrderController {

  private final OrderService orderService;
  private final UserService userService;

  @Autowired
  public OrderController(OrderService orderService, UserService userService) {
    this.orderService = orderService;
    this.userService = userService;
  }

  // Create a new order (without items initially)
  @PostMapping  // Method-level mapping
  public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
    Optional<User> user = userService.getUserById(orderRequest.getUserId());
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    // Create and save the order
    Order order = new Order();
    order.setUser(user.get());

    Order savedOrder = orderService.saveOrder(order);

    // Directly creating the response here
    OrderResponse response = new OrderResponse(
            savedOrder.getOrderId(),
            savedOrder.getOrderDate(),
            savedOrder.getStatus(),
            savedOrder.getUser().getUserId(),
            savedOrder.getOrderItems()  // Initially empty, will be populated later
    );

    return ResponseEntity.ok(response);
  }

  // Retrieve an order by its ID with explicit parameter
  @GetMapping(params = "orderId")  // Method-level mapping with explicit param
  public ResponseEntity<OrderResponse> getOrderById(@RequestParam Integer orderId) {
    Optional<Order> order = orderService.getOrderById(orderId);
    if (order.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    // Directly creating the response here
    OrderResponse response = new OrderResponse(
            order.get().getOrderId(),
            order.get().getOrderDate(),
            order.get().getStatus(),
            order.get().getUser().getUserId(),
            order.get().getOrderItems()  // This will contain the list of items for the order
    );

    return ResponseEntity.ok(response);
  }

  // Retrieve orders by user ID
  @GetMapping(params = "userId")  // Method-level mapping with userId parameter
  public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@RequestParam Integer userId) {
    List<Order> orders = orderService.getOrdersByUserId(userId);
    if (orders.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    List<OrderResponse> responses = orders.stream()
            .map(order -> new OrderResponse(
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.getStatus(),
                    order.getUser().getUserId(),
                    order.getOrderItems()))  // Convert each Order to OrderResponse
            .collect(Collectors.toList());

    return ResponseEntity.ok(responses);
  }

  // Delete an order by its ID
  @DeleteMapping  // Method-level mapping
  public ResponseEntity<RemoveOrderResponse> deleteOrder(@RequestBody @Valid RemoveOrderRequest request) {
    Optional<Order> order = orderService.getOrderById(request.getOrderId());
    if (order.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    orderService.deleteOrder(request.getOrderId());

    // Directly creating the response here
    RemoveOrderResponse response = new RemoveOrderResponse(
            request.getOrderId(),
            "Order deleted successfully"
    );

    return ResponseEntity.ok(response);
  }
}
````

## ItemController

The `ItemController` class handles HTTP requests related to item operations within the application. It exposes various endpoints to create, retrieve, and modify items. This controller interacts with the `ItemService` to manage item-related logic and responds with appropriate status codes and data wrapped in `ResponseEntity`.

### Controller Annotations

- **`@RestController`**: Marks the class as a RESTful controller capable of handling HTTP requests and returning responses.
- **`@RequestMapping("/shop/items")`**: Defines the base URL path for all item-related operations.

### Endpoints Overview

- **Create a new item** (`POST /shop/items`)
  - **HTTP Method**: POST
  - **Description**: Creates a new item with the provided details.
  - **Response**: Returns a `200 OK` status with the created item's details in the response body.

- **Retrieve an item by ID** (`GET /shop/items/{itemId}`)
  - **HTTP Method**: GET
  - **Description**: Retrieves an item by its unique ID.
  - **Response**: Returns a `200 OK` status with the item details if found. If the item does not exist, returns a `404 Not Found`.

- **Update an existing item** (`PUT /shop/items/{itemId}`)
  - **HTTP Method**: PUT
  - **Description**: Updates an item's details such as name, description, and price.
  - **Response**: Returns a `200 OK` status with the updated item details.

### HTTP Methods Used

- **`@PostMapping`**: Maps HTTP POST requests to the `createItem` method to create a new item.
- **`@GetMapping`**: Maps HTTP GET requests to retrieve an item by its unique `itemId` (using path variables).
- **`@PutMapping`**: Maps HTTP PUT requests to update an existing item's details.

### Detailed Breakdown of Methods

#### `getItemById()`
- **URL**: `/shop/items/{itemId}`
- **Method**: GET
- **Request Parameter**: `itemId` (path variable)
- **Response**: Returns a `200 OK` status with a `ItemResponse` object containing the item's details if found. If the item does not exist, returns `404 Not Found`.

This controller uses **path variables** in the `GET` request (`/shop/items/{itemId}`) as there is only one `GET` endpoint that retrieves an item by its unique identifier. Path variables are ideal when a single resource is being accessed based on a unique ID. Query parameters, on the other hand, are typically used for filtering, pagination, or when multiple retrieval options exist, as seen in the `OrderController`.

````java
package com.example.controller;

import com.example.model.Item;
import com.example.rest.request.item.ItemRequest;
import com.example.rest.response.item.ItemResponse;
import com.example.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/shop/items")  // Base path for all item-related endpoints
public class ItemController {

  private final ItemService itemService;

  @Autowired
  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  // Create a new item
  @PostMapping  // Method-level mapping
  public ResponseEntity<ItemResponse> createItem(@RequestBody @Valid ItemRequest itemRequest) {
    Item item = new Item();
    item.setName(itemRequest.getName());
    item.setDescription(itemRequest.getDescription());
    item.setPrice(BigDecimal.valueOf(itemRequest.getPrice()));
    item.setStockQuantity(0);  // Default stock quantity can be 0 or defined as needed

    Item savedItem = itemService.saveItem(item);

    // Creating the response
    ItemResponse response = new ItemResponse(
            savedItem.getItemId(),
            savedItem.getName(),
            savedItem.getDescription(),
            savedItem.getPrice().doubleValue(),
            savedItem.getStockQuantity()
    );

    return ResponseEntity.ok(response);
  }

  // Retrieve an item by its ID
  @GetMapping("/{itemId}")  // Method-level mapping with path variable
  public ResponseEntity<ItemResponse> getItemById(@PathVariable Integer itemId) {
    Optional<Item> item = itemService.getItemById(itemId);
    if (item.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    // Creating the response
    ItemResponse response = new ItemResponse(
            item.get().getItemId(),
            item.get().getName(),
            item.get().getDescription(),
            item.get().getPrice().doubleValue(),
            item.get().getStockQuantity()
    );

    return ResponseEntity.ok(response);
  }

  // Modify an existing item (update name, description, and price)
  @PutMapping("/{itemId}")  // Method-level mapping with path variable
  public ResponseEntity<ItemResponse> updateItem(@PathVariable Integer itemId, @RequestBody @Valid ItemRequest itemRequest) {
    Optional<Item> existingItem = itemService.getItemById(itemId);
    if (existingItem.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Item item = existingItem.get();
    if (itemRequest.getName() != null) item.setName(itemRequest.getName());
    if (itemRequest.getDescription() != null) item.setDescription(itemRequest.getDescription());
    if (itemRequest.getPrice() != null) item.setPrice(BigDecimal.valueOf(itemRequest.getPrice()));

    Item updatedItem = itemService.saveItem(item);

    // Creating the response
    ItemResponse response = new ItemResponse(
            updatedItem.getItemId(),
            updatedItem.getName(),
            updatedItem.getDescription(),
            updatedItem.getPrice().doubleValue(),
            updatedItem.getStockQuantity()
    );

    return ResponseEntity.ok(response);
  }
}
````

## OrderItemController

The `OrderItemController` class handles HTTP requests related to managing items within orders. It exposes endpoints to add items to an order, remove items from an order, and interact with the `OrderService`, `ItemService`, and `OrderItemService` to manage the relationships between orders and items. This controller allows users to efficiently modify the contents of an order by adding or removing items based on the provided input.

### Controller Annotations

- **`@RestController`**: Indicates that this class is a RESTful controller capable of handling HTTP requests and returning responses.
- **`@RequestMapping("/shop/orderitems")`**: This defines the base URL path for all order-item related operations. All methods in this controller will be accessible under this base path.

### Endpoints Overview

- **Add an item to an order** (`POST /shop/orderitems`)
  - **HTTP Method**: POST
  - **Description**: Adds a specific item to an existing order, updating the quantity if the item already exists in the order. If the item doesn't exist, it is added as a new `OrderItem`.
  - **Response**: Returns a `200 OK` status with the updated details of the `OrderItem` or an error message if the order or item is not found.

- **Remove an item from an order** (`DELETE /shop/orderitems`)
  - **HTTP Method**: DELETE
  - **Description**: Removes a specific item from the order. If the item does not exist in the order, an error message is returned.
  - **Response**: Returns a `200 OK` status with a success message if the item is removed, or a `404 Not Found` if the item is not found in the order.

### HTTP Methods Used

- **`@PostMapping`**: Maps HTTP POST requests to the `addItemToOrder` method, allowing the addition of an item to an order.
- **`@DeleteMapping`**: Maps HTTP DELETE requests to the `removeItemFromOrder` method, allowing the removal of an item from an order.

### Detailed Breakdown of Methods

#### `addItemToOrder()`
- **URL**: `/shop/orderitems`
- **Method**: POST
- **Request Body**: The details of the item to be added in the form of an `AddItemToOrderRequest` object, which includes `orderId`, `itemId`, and `quantity`.
- **Response**: Returns a `200 OK` status with an `AddItemToOrderResponse` object containing a success message and updated details of the order item, or an error message if the order or item is not found.

#### `removeItemFromOrder()`
- **URL**: `/shop/orderitems`
- **Method**: DELETE
- **Request Body**: The details of the item to be removed in the form of a `RemoveItemFromOrderRequest` object, which includes `orderId` and `itemId`.
- **Response**: Returns a `200 OK` status with a `RemoveItemFromOrderResponse` object containing a success message or an error message if the item is not found in the order.

````java
package com.example.controller;

import com.example.model.Item;
import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.model.key.OrderItemKey;
import com.example.rest.request.orderitem.AddItemToOrderRequest;
import com.example.rest.request.orderitem.RemoveItemFromOrderRequest;
import com.example.rest.response.orderitem.AddItemToOrderResponse;
import com.example.rest.response.orderitem.RemoveItemFromOrderResponse;
import com.example.service.ItemService;
import com.example.service.OrderItemService;
import com.example.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shop/orderitems")  // Base path for order item-related endpoints
public class OrderItemController {

  private final OrderItemService orderItemService;
  private final OrderService orderService;
  private final ItemService itemService;

  @Autowired
  public OrderItemController(OrderItemService orderItemService, OrderService orderService, ItemService itemService) {
    this.orderItemService = orderItemService;
    this.orderService = orderService;
    this.itemService = itemService;
  }

  // Add an item to an order (Use POST for creating the relationship)
  @PostMapping  // Changed from PUT to POST
  public ResponseEntity<AddItemToOrderResponse> addItemToOrder(@RequestBody @Valid AddItemToOrderRequest request) {
    // Retrieve the Order and Item by their IDs
    Optional<Order> orderOptional = orderService.getOrderById(request.getOrderId());
    Optional<Item> itemOptional = itemService.getItemById(request.getItemId());

    // Check if the Order and Item exist
    if (orderOptional.isEmpty()) {
      return ResponseEntity.badRequest().body(new AddItemToOrderResponse(
              request.getOrderId(), request.getItemId(), request.getQuantity(), "Order not found."));
    }

    if (itemOptional.isEmpty()) {
      return ResponseEntity.badRequest().body(new AddItemToOrderResponse(
              request.getOrderId(), request.getItemId(), request.getQuantity(), "Item not found."));
    }

    Order order = orderOptional.get();
    Item item = itemOptional.get();

    // Create a composite key for the OrderItem
    OrderItemKey orderItemKey = new OrderItemKey(request.getOrderId(), request.getItemId());

    // Check if the OrderItem already exists
    Optional<OrderItem> existingOrderItem = orderItemService.getOrderItemById(orderItemKey);

    if (existingOrderItem.isPresent()) {
      // If the item already exists in the order, update the quantity
      OrderItem orderItem = existingOrderItem.get();
      orderItem.setQuantity(orderItem.getQuantity() + request.getQuantity());
      orderItemService.saveOrderItem(orderItem);
    } else {
      // If the item doesn't exist in the order, create a new OrderItem
      OrderItem newOrderItem = new OrderItem();
      newOrderItem.setId(orderItemKey);
      newOrderItem.setOrder(order);  // Use the retrieved order
      newOrderItem.setItem(item);    // Use the retrieved item
      newOrderItem.setQuantity(request.getQuantity());

      orderItemService.saveOrderItem(newOrderItem);
    }

    // Preparing the response
    AddItemToOrderResponse response = new AddItemToOrderResponse(
            request.getOrderId(),
            request.getItemId(),
            request.getQuantity(),
            "Item successfully added to the order."
    );

    return ResponseEntity.ok(response);
  }

  // Remove an item from an order
  @DeleteMapping  // Method-level mapping for DELETE
  public ResponseEntity<RemoveItemFromOrderResponse> removeItemFromOrder(@RequestBody @Valid RemoveItemFromOrderRequest request) {
    // Retrieve the Order and Item by their IDs
    Optional<Order> orderOptional = orderService.getOrderById(request.getOrderId());
    Optional<Item> itemOptional = itemService.getItemById(request.getItemId());

    // Check if the Order and Item exist
    if (orderOptional.isEmpty()) {
      return ResponseEntity.badRequest().body(new RemoveItemFromOrderResponse(
              request.getOrderId(), request.getItemId(), "Order not found."));
    }

    if (itemOptional.isEmpty()) {
      return ResponseEntity.badRequest().body(new RemoveItemFromOrderResponse(
              request.getOrderId(), request.getItemId(), "Item not found."));
    }

    OrderItemKey orderItemKey = new OrderItemKey(request.getOrderId(), request.getItemId());
    Optional<OrderItem> orderItemOptional = orderItemService.getOrderItemById(orderItemKey);

    if (orderItemOptional.isEmpty()) {
      return ResponseEntity.badRequest().body(new RemoveItemFromOrderResponse(
              request.getOrderId(), request.getItemId(), "Item not found in the order."));
    }

    // If the OrderItem exists, delete it
    orderItemService.deleteOrderItem(orderItemKey);

    // Preparing the response
    RemoveItemFromOrderResponse response = new RemoveItemFromOrderResponse(
            request.getOrderId(),
            request.getItemId(),
            "Item successfully removed from the order."
    );

    return ResponseEntity.ok(response);
  }
}
````

# Main class

````java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebServiceRest {
    public static void main(String[] args) {
        SpringApplication.run(WebServiceRest.class, args);
    }
}
````

# Running the Spring Boot Application

To run the `WebServiceRest` application, follow these steps:

## Steps to Run the Application:

1. **Open the Project in IntelliJ IDEA**:
  - Launch IntelliJ IDEA and choose **File -> Open**.
  - Select the root folder of the project and open it. IntelliJ will automatically detect the **pom.xml** file and configure the project with Maven as the build tool. If Maven is not selected automatically, make sure to manually set Maven as the build tool when prompted.

2. **Set JDK to 21**:
  - When you open the project, IntelliJ will automatically detect the project SDK. If JDK 21 is not set, IntelliJ will prompt you to configure it.
  - To check or change the JDK version, go to **File -> Project Structure** and under **Project Settings -> Project**, set the **Project SDK** to **JDK 21**. If JDK 21 isn't available, you can add it by clicking on **New...** and selecting the appropriate JDK folder.

3. **Run the Application**:
  - In the **Project** view, locate the main class `WebServiceRest` (the class with the `main` method).
  - In the top-right corner of IntelliJ, click the **Run** button (green arrow) or go to **Run -> Run 'WebServiceRest'** to start the application.

4. **Verify Application Startup**:
  - Once the application starts, you’ll see logs in the terminal indicating that the Spring Boot application is running.

# API Testing Guide for `ItemController`

This guide provides test cases for the following endpoints:
- **Create an item** (`POST /shop/items`)
- **Retrieve an item by ID** (`GET /shop/items/{itemId}`)
- **Update an item** (`PUT /shop/items/{itemId}`)

---

## 1. Create an Item

### JSON Request:
```json
{
  "name": "Laptop",
  "description": "A high-performance laptop",
  "price": 1299.99
}
```

### cURL Command:
```bash
curl -X POST "http://localhost:8080/shop/items" \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Laptop",
           "description": "A high-performance laptop",
           "price": 1299.99
         }'
```

### Postman Steps:
1. Open Postman.
2. Select **POST** method.
3. Enter the URL: `http://localhost:8080/shop/items`
4. Go to the **Body** tab → Select **raw** → **JSON**.
5. Copy-paste the JSON request.
6. Click **Send**.
7. Verify the response.

### Expected Response:
```json
{
  "item_id": 1,
  "name": "Laptop",
  "description": "A high-performance laptop",
  "price": 1299.99,
  "stock_quantity": 0
}
```
### Database check
Following query returns inserted row:

````sql
SELECT * FROM shop_schema.Item WHERE name = 'Laptop';
````
---

## 2. Retrieve an Item by ID

### cURL Command:
```bash
curl -X GET "http://localhost:8080/shop/items/1"
```

### Postman Steps:
1. Open Postman.
2. Select **GET** method.
3. Enter the URL: `http://localhost:8080/shop/items/1`
4. Click **Send**.
5. Verify the response.

### Expected Response:
```json
{
  "item_id": 1,
  "name": "Laptop",
  "description": "A high-performance laptop",
  "price": 1299.99,
  "stock_quantity": 0
}
```

---

## 3. Update an Item

### JSON Request:
```json
{
  "name": "Gaming Laptop",
  "description": "A high-performance gaming laptop",
  "price": 1499.99
}
```

### cURL Command:
```bash
curl -X PUT "http://localhost:8080/shop/items/1" \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Gaming Laptop",
           "description": "A high-performance gaming laptop",
           "price": 1499.99
         }'
```

### Postman Steps:
1. Open Postman.
2. Select **PUT** method.
3. Enter the URL: `http://localhost:8080/shop/items/1`
4. Go to the **Body** tab → Select **raw** → **JSON**.
5. Copy-paste the JSON request.
6. Click **Send**.
7. Verify the response.

### Expected Response:
```json
{
  "item_id": 1,
  "name": "Gaming Laptop",
  "description": "A high-performance gaming laptop",
  "price": 1499.99,
  "stock_quantity": 0
}
```

### Database check
Following query returns updated row:

````sql
SELECT * FROM shop_schema.Item WHERE item_id = 1;
````

---

## Notes:
- Ensure the server is running (`http://localhost:8080`).
- Replace `{itemId}` with the actual `item_id` from previous responses.
- If the **GET** request returns `404 Not Found`, verify if the item exists in the database.

# API Testing Guide for `UserController`

This guide provides test cases for the following endpoints:
- **Create a user** (`POST /shop/users`)
- **Retrieve a user by ID** (`GET /shop/users?id={userId}`)
- **Retrieve a user by email** (`GET /shop/users?email={email}`)
- **Retrieve a user by username** (`GET /shop/users?username={username}`)
- **Update a user** (`PUT /shop/users`)
- **Delete a user** (`DELETE /shop/users`)

---

## 1. Create a User

### JSON Request:
```json
{
  "username": "john_doe",
  "email": "john.doe@example.com"
}
```

### cURL Command:
```bash
curl -X POST "http://localhost:8080/shop/users" \
     -H "Content-Type: application/json" \
     -d '{
           "username": "john_doe",
           "email": "john.doe@example.com"
         }'
```

### Postman Steps:
1. Open Postman.
2. Select **POST** method.
3. Enter the URL: `http://localhost:8080/shop/users`
4. Go to the **Body** tab → Select **raw** → **JSON**.
5. Copy-paste the JSON request.
6. Click **Send**.
7. Verify the response.

### Expected Response:
```json
{
  "user_id": 1,
  "username": "john_doe",
  "email": "john.doe@example.com",
  "created_at": "2024-03-03T12:34:56"
}
```

### Database Check
Following query returns the inserted row:
```sql
SELECT * FROM shop_schema.User WHERE email = 'john.doe@example.com';
```

---

## 2. Retrieve a User by ID

### cURL Command:
```bash
curl -X GET "http://localhost:8080/shop/users?id=1"
```

### Postman Steps:
1. Open Postman.
2. Select **GET** method.
3. Enter the URL: `http://localhost:8080/shop/users?id=1`
4. Click **Send**.
5. Verify the response.

### Expected Response:
```json
{
  "user_id": 1,
  "username": "john_doe",
  "email": "john.doe@example.com",
  "created_at": "2024-03-03T12:34:56"
}
```

---

## 3. Retrieve a User by Email

### cURL Command:
```bash
curl -X GET "http://localhost:8080/shop/users?email=john.doe@example.com"
```

### Postman Steps:
1. Open Postman.
2. Select **GET** method.
3. Enter the URL: `http://localhost:8080/shop/users?email=john.doe@example.com`
4. Click **Send**.
5. Verify the response.

### Expected Response:
```json
{
  "user_id": 1,
  "username": "john_doe",
  "email": "john.doe@example.com",
  "created_at": "2024-03-03T12:34:56"
}
```

---

## 4. Retrieve a User by Username

### cURL Command:
```bash
curl -X GET "http://localhost:8080/shop/users?username=john_doe"
```

### Postman Steps:
1. Open Postman.
2. Select **GET** method.
3. Enter the URL: `http://localhost:8080/shop/users?username=john_doe`
4. Click **Send**.
5. Verify the response.

### Expected Response:
```json
{
  "user_id": 1,
  "username": "john_doe",
  "email": "john.doe@example.com",
  "created_at": "2024-03-03T12:34:56"
}
```

---

## 5. Update a User

### JSON Request:
```json
{
  "username": "john_updated",
  "email": "john.doe@example.com"
}
```

### cURL Command:
```bash
curl -X PUT "http://localhost:8080/shop/users" \
     -H "Content-Type: application/json" \
     -d '{
           "username": "john_updated",
           "email": "john.doe@example.com"
         }'
```

### Postman Steps:
1. Open Postman.
2. Select **PUT** method.
3. Enter the URL: `http://localhost:8080/shop/users`
4. Go to the **Body** tab → Select **raw** → **JSON**.
5. Copy-paste the JSON request.
6. Click **Send**.
7. Verify the response.

### Expected Response:
```json
{
  "user_id": 1,
  "username": "john_updated",
  "email": "john.doe@example.com",
  "created_at": "2024-03-03T12:34:56"
}
```

### Database Check
Following query returns the updated row:
```sql
SELECT * FROM shop_schema.User WHERE user_id = 1;
```

---

## 6. Delete a User

### JSON Request:
```json
{
  "user_id": 1
}
```

### cURL Command:
```bash
curl -X DELETE "http://localhost:8080/shop/users" \
     -H "Content-Type: application/json" \
     -d '{
           "user_id": 1
         }'
```

### Postman Steps:
1. Open Postman.
2. Select **DELETE** method.
3. Enter the URL: `http://localhost:8080/shop/users`
4. Go to the **Body** tab → Select **raw** → **JSON**.
5. Copy-paste the JSON request.
6. Click **Send**.
7. Verify the response.

### Expected Response:
```json
{
  "user_id": 1,
  "message": "User deleted successfully"
}
```

### Database Check
Following query should return an empty result:
```sql
SELECT * FROM shop_schema.User WHERE user_id = 1;
```

---

## Notes:
- Ensure the server is running (`http://localhost:8080`).
- Replace `{userId}`, `{email}`, and `{username}` with actual values.
- **GET requests have no request body**.
- If a **GET** request returns `404 Not Found`, verify if the user exists in the database.

# API Testing Guide for `OrderController`

This guide covers testing for the `OrderController` endpoints. It follows these steps:

1. **Create two users** (`POST /shop/users`)
2. **Create two orders per user** (`POST /shop/orders`)
3. **Retrieve orders by ID and user ID** (`GET /shop/orders`)
4. **Delete an order** (`DELETE /shop/orders`)

Each step includes:
- **cURL commands**
- **Postman steps**
- **Database validation queries**

---

## 1. Create Two Users
Given we delete the only user from the db in the previuos step, we should first create some new user;
the DB will assign them ids **2** and **3** because **1** we already used.

### JSON Request:
```json
{
  "username": "alice_smith",
  "email": "alice@example.com"
}
```

```json
{
  "username": "bob_jones",
  "email": "bob@example.com"
}
```

### cURL Commands:
```bash
curl -X POST "http://localhost:8080/shop/users" \
     -H "Content-Type: application/json" \
     -d '{"username": "alice_smith", "email": "alice@example.com"}'

curl -X POST "http://localhost:8080/shop/users" \
     -H "Content-Type: application/json" \
     -d '{"username": "bob_jones", "email": "bob@example.com"}'
```

### Postman Steps:
1. Select **POST** method.
2. Enter URL: `http://localhost:8080/shop/users`
3. Go to **Body** → Select **raw** → **JSON**.
4. Copy-paste the JSON request.
5. Click **Send** and verify response.

### Expected Response:
```json
{
  "user_id": 2,
  "username": "alice_smith",
  "email": "alice@example.com"
}
```
```json
{
  "user_id": 3,
  "username": "bob_jones",
  "email": "bob@example.com"
}
```

### Database Check:
```sql
SELECT * FROM shop_schema.User WHERE email IN ('alice@example.com', 'bob@example.com');
```

---

## 2. Create Two Orders for Each User

### JSON Request (Example for Alice):
```json
{
  "user_id": 2
}
```

```json
{
  "user_id": 3
}
```

### cURL Commands:
```bash
curl -X POST "http://localhost:8080/shop/orders" \
     -H "Content-Type: application/json" \
     -d '{"user_id": 2}'

curl -X POST "http://localhost:8080/shop/orders" \
     -H "Content-Type: application/json" \
     -d '{"user_id": 2}'

curl -X POST "http://localhost:8080/shop/orders" \
     -H "Content-Type: application/json" \
     -d '{"user_id": 3}'

curl -X POST "http://localhost:8080/shop/orders" \
     -H "Content-Type: application/json" \
     -d '{"user_id": 3}'
```

### Postman Steps:
1. Select **POST** method.
2. Enter URL: `http://localhost:8080/shop/orders`
3. Go to **Body** → Select **raw** → **JSON**.
4. Copy-paste the JSON request.
5. Click **Send** and verify response.

### Expected Response:
```json
[
  {
    "order_id": 1,
    "order_date": "2025-03-03T18:48:54.206957044",
    "status": "pending",
    "user_id": 2,
    "order_items": null
  },
  {
    "order_id": 2,
    "order_date": "2025-03-03T18:48:54.254454597",
    "status": "pending",
    "user_id": 2,
    "order_items": null
  },
  {
    "order_id": 3,
    "order_date": "2025-03-03T18:48:54.280005819",
    "status": "pending",
    "user_id": 3,
    "order_items": null
  },
  {
    "order_id": 4,
    "order_date": "2025-03-03T18:48:54.2943636",
    "status": "pending",
    "user_id": 3,
    "order_items": null
  }
]
```

### Database Check:
```sql
SELECT * FROM shop_schema.order WHERE "user_id_fk" IN (2, 3);
```

result should be:

````bash
my_app_db=# SELECT * FROM shop_schema.order WHERE "user_id_fk" IN (2, 3);
 order_id | user_id_fk |         order_date         | status  
----------+------------+----------------------------+---------
        1 |          2 | 2025-03-03 18:48:54.206957 | pending
        2 |          2 | 2025-03-03 18:48:54.254455 | pending
        3 |          3 | 2025-03-03 18:48:54.280006 | pending
        4 |          3 | 2025-03-03 18:48:54.294364 | pending
(4 rows)
````
---

## 3. Retrieve Orders by User ID

### cURL Command:
```bash
curl -X GET "http://localhost:8080/shop/orders?userId=3"
```

### Postman Steps:
1. Select **GET** method.
2. Enter URL: `http://localhost:8080/shop/orders?userId=3`
3. Click **Send**.

### Expected Response:
```json
[
  { "order_id": 3, "user_id": 3, "order_items": [] },
  { "order_id": 4, "user_id": 3, "order_items": [] }
]
```

---

## 4. Retrieve Order by Order ID

### cURL Command:
```bash
curl -X GET "http://localhost:8080/shop/orders?orderId=3"
```

### Postman Steps:
1. Select **GET** method.
2. Enter URL: `http://localhost:8080/shop/orders?orderId=3`
3. Click **Send**.

### Expected Response:
```json
{
  "order_id": 3,
  "user_id": 3,
  "order_items": []
}
```

---

## 5. Delete an Order

### JSON Request:
```json
{
  "order_id": 1
}
```

### cURL Command:
```bash
curl -X DELETE "http://localhost:8080/shop/orders" \
     -H "Content-Type: application/json" \
     -d '{"order_id": 1}'
```

### Postman Steps:
1. Select **DELETE** method.
2. Enter URL: `http://localhost:8080/shop/orders`
3. Go to **Body** → Select **raw** → **JSON**.
4. Copy-paste the JSON request.
5. Click **Send** and verify response.

### Expected Response:
```json
{
  "order_id": 1,
  "message": "Order deleted successfully"
}
```

### Database Check:
```sql
SELECT * FROM shop_schema.Order WHERE order_id = 1;
-- Should return no rows
```

---

# API Testing Guide for `OrderItemController`

This guide covers test cases for the following endpoints:
- **Add an item to an order** (`POST /shop/orderitems`)
- **Remove an item from an order** (`DELETE /shop/orderitems`)
- **Retrieve orders with their items** (`GET /shop/orders?userId={userId}`)

## Initial Database State
Ensure the following scenario exists in the database before testing:

### Users Table:
```sql
SELECT * FROM shop_schema.User;
```
| user_id | username    | email            | created_at              |
|---------|------------|------------------|-------------------------|
| 2       | alice_smith| alice@example.com| 2025-03-03 18:46:12.235 |
| 3       | bob_jones  | bob@example.com  | 2025-03-03 18:46:12.248 |

### Orders Table:
```sql
SELECT * FROM shop_schema.Order;
```
| order_id | user_id_fk | order_date                 | status  |
|----------|-----------|----------------------------|---------|
| 2        | 2         | 2025-03-03 18:48:54.254455 | pending |
| 3        | 3         | 2025-03-03 18:48:54.280006 | pending |
| 4        | 3         | 2025-03-03 18:48:54.294364 | pending |

### Items Table (Initial):
```sql
SELECT * FROM shop_schema.Item;
```
| item_id | name           | description                    | price   | stock_quantity |
|---------|---------------|--------------------------------|---------|---------------|
| 1       | Gaming Laptop | A high-performance laptop     | 1499.99 | 0             |

## 1. Create Additional Items

We will create 3 more items:

### JSON Request:
```json
{
  "name": "Smartphone",
  "description": "A flagship smartphone",
  "price": 999.99
}
```

### cURL Command:
```bash
curl -X POST "http://localhost:8080/shop/items" \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Smartphone",
           "description": "A flagship smartphone",
           "price": 999.99
         }'
```

### Expected Response:
```json
{
  "item_id": 2,
  "name": "Smartphone",
  "description": "A flagship smartphone",
  "price": 999.99,
  "stock_quantity": 0
}
```

Likewise for **Tablet**:

````json
{
  "name": "Tablet",
  "description": "A sleek tablet for everyday use",
  "price": 499.99
}
````

````bash
curl -X POST "http://localhost:8080/shop/items" \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Tablet",
           "description": "A sleek tablet for everyday use",
           "price": 499.99
         }'
````

and **Headphones**:

````json
{
  "name": "Headphones",
  "description": "Noise-cancelling headphones",
  "price": 199.99
}
````

````bash
curl -X POST "http://localhost:8080/shop/items" \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Headphones",
           "description": "Noise-cancelling headphones",
           "price": 199.99
         }'
````

at the end on the database:

````sql
SELECT * FROM shop_schema.Item;
````

````bash
my_app_db=# SELECT * FROM shop_schema.Item;
 item_id |     name      |           description            |  price  | stock_quantity 
---------+---------------+----------------------------------+---------+----------------
       1 | Gaming Laptop | A high-performance gaming laptop | 1499.99 |              0
       2 | Smartphone    | A flagship smartphone            |  999.99 |              0
       3 | Tablet        | A sleek tablet for everyday use  |  499.99 |              0
       4 | Headphones    | Noise-cancelling headphones      |  199.99 |              0
(4 rows)
````


---

## 2. Add Items to Orders

### Order **2**: 3 Items

#### JSON Requests:
```json
{
  "order_id": 2,
  "item_id": 1,
  "quantity": 1
}
```
```json
{
  "order_id": 2,
  "item_id": 2,
  "quantity": 2
}
```
```json
{
  "order_id": 2,
  "item_id": 3,
  "quantity": 1
}
```

#### cURL Commands:
```bash
curl -X POST "http://localhost:8080/shop/orderitems" \
     -H "Content-Type: application/json" \
     -d '{"order_id": 2, "item_id": 1, "quantity": 1}'

curl -X POST "http://localhost:8080/shop/orderitems" \
     -H "Content-Type: application/json" \
     -d '{"order_id": 2, "item_id": 2, "quantity": 2}'

curl -X POST "http://localhost:8080/shop/orderitems" \
     -H "Content-Type: application/json" \
     -d '{"order_id": 2, "item_id": 3, "quantity": 1}'
```

---

### Order **3**: 4 Items

#### JSON Requests:
```json
{
  "order_id": 3,
  "item_id": 1,
  "quantity": 1
}
```
```json
{
  "order_id": 3,
  "item_id": 2,
  "quantity": 1
}
```
```json
{
  "order_id": 3,
  "item_id": 3,
  "quantity": 1
}
```
```json
{
  "order_id": 3,
  "item_id": 4,
  "quantity": 1
}
```

#### cURL Commands:
```bash
curl -X POST "http://localhost:8080/shop/orderitems" \
     -H "Content-Type: application/json" \
     -d '{"order_id": 3, "item_id": 1, "quantity": 1}'

curl -X POST "http://localhost:8080/shop/orderitems" \
     -H "Content-Type: application/json" \
     -d '{"order_id": 3, "item_id": 2, "quantity": 1}'

curl -X POST "http://localhost:8080/shop/orderitems" \
     -H "Content-Type: application/json" \
     -d '{"order_id": 3, "item_id": 3, "quantity": 1}'

curl -X POST "http://localhost:8080/shop/orderitems" \
     -H "Content-Type: application/json" \
     -d '{"order_id": 3, "item_id": 4, "quantity": 1}'
```

---

### Order **4**: 2 Items

#### JSON Requests:
```json
{
  "order_id": 4,
  "item_id": 2,
  "quantity": 1
}
```
```json
{
  "order_id": 4,
  "item_id": 3,
  "quantity": 1
}
```

#### cURL Commands:
```bash
curl -X POST "http://localhost:8080/shop/orderitems" \
     -H "Content-Type: application/json" \
     -d '{"order_id": 4, "item_id": 2, "quantity": 1}'

curl -X POST "http://localhost:8080/shop/orderitems" \
     -H "Content-Type: application/json" \
     -d '{"order_id": 4, "item_id": 3, "quantity": 1}'
```

at the end on the database you should have the following situation:

````sql
SELECT * FROM shop_schema.order_item;
````

````bash
 order_id_fk | item_id_fk | quantity 
-------------+------------+----------
           2 |          1 |        1
           2 |          2 |        2
           2 |          3 |        1
           3 |          1 |        1
           3 |          2 |        1
           3 |          3 |        1
           3 |          4 |        1
           4 |          2 |        2
           4 |          3 |        2
(9 rows)
````

as you can see:

- Order 2 has 3 items
- Order 3 had 4 items
- Order 4 has 2 items

---

## 3. Remove Items from Orders

### JSON Request:
```json
{
  "order_id": 3,
  "item_id": 2
}
```

### cURL Command:
```bash
curl -X DELETE "http://localhost:8080/shop/orderitems" \
     -H "Content-Type: application/json" \
     -d '{
           "order_id": 3,
           "item_id": 2
         }'
```

### Expected Response:
```json
{
  "order_id": 3,
  "item_id": 2,
  "message": "Item successfully removed from the order."
}
```

as you can see on the DB:

````sql
SELECT * FROM shop_schema.order_item;
````

````bash
 order_id_fk | item_id_fk | quantity 
-------------+------------+----------
           2 |          1 |        1
           2 |          2 |        2
           2 |          3 |        1
           3 |          1 |        1
           3 |          3 |        1
           3 |          4 |        1
           4 |          2 |        2
           4 |          3 |        2
(8 rows)
````

now:

- Order 2 has 3 items
- Order 3 had 3 items (**one item was deleted**)
- Order 4 has 2 items

---

## 4. Retrieve Orders by User ID

### cURL Command:
```bash
curl -X GET "http://localhost:8080/shop/orders?userId=3"
```

### Response:
````json
[
  {
    "order_id": 3,
    "order_date": "2025-03-03T18:48:54.280006",
    "status": "pending",
    "user_id": 3,
    "order_items": [
      {
        "id": {
          "order_id_pk": 3,
          "item_id_pk": 1
        },
        "order": {
          "orderId": 3,
          "user": {
            "userId": 3,
            "username": "bob_jones",
            "email": "bob@example.com",
            "createdAt": "2025-03-03T18:46:12.248419",
            "orders": [
              {
                "orderId": 3,
                "user": {
                  "userId": 3,
                  "username": "bob_jones",
                  "email": "bob@example.com",
                  "createdAt": "2025-03-03T18:46:12.248419",
                  "orders": [
                    {
                      "orderId": 3,
                      "user": {
                        "userId": 3,
                        "username": "bob_jones",
                        "email": "bob@example.com",
                        "createdAt": "2025-03-03T18:46:12.248419",
                        "orders": [
                          {
                            "orderId": 3,
                            "user": {
                              "userId": 3,
                              "username": "bob_jones",
                              "email": "bob@example.com",
                              "createdAt": "2025-03-03T18:46:12.248419",
                              "orders": [
                                {
                                  "orderId": 3,
                                  "user": {
````

This confirms that order items are properly retrieved with the orders, but we have an issue.

#### Recursive nesting
When dealing with relationships between entities in a typical REST API, you may encounter **recursive nesting**. This issue arises when one object references another, and that second object references the first, creating a loop of references.

The issue originates within the class definition of `OrderReponse`:

````java
@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {

    @JsonProperty("order_id")
    private Integer orderId;

    @JsonProperty("order_date")
    private LocalDateTime orderDate;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("order_items")
    private List<OrderItem> orderItems;
}
````

that is, whenever the following field is not empty:

````bash
    @JsonProperty("order_items")
    private List<OrderItem> orderItems;
````

then:

- Each `OrderItem` has a reference to `Order` object.
- The `Order` object inside `OrderItem` has a reference to `User` object.
- The `User` object inside `Order` has a reference to an array `orders`.
- The `orders` array inside the `User` object contains an order.
- The `Order` object inside that array includes a reference to the same `User` object.
- This `User` object has an `orders` array, which contains the same order, causing an **infinite loop**.

This issue occurs because of **recursive relationships** between entities, where:

- `User` has a list of `orders` (a **one-to-many** relationship).
- `Order` has a reference back to the `User` who placed the order (a **many-to-one** relationship).

This leads to an infinite loop when serializing the data into **JSON**.

- When the API serializes the `User` object, it includes a list of `orders`.
- Each `Order` references the same `User` object, and when the `User` is serialized, the `orders` list is serialized again.
- This creates a **recursive loop** where the `User` object keeps including itself in the response.

Then:

- As a result, the response grows **exponentially**, and the server can even crash due to excessive memory usage or timeouts, as the recursion never ends.
- The `orders` array inside the `User` object seems to be referencing itself over and over again, which causes this infinite loop of data.

#### Design Issue

In a **bugged design**, where **entities** are directly exposed to API responses, a common issue is **recursive nesting**. This occurs due to **bidirectional relationships** between entities that lead to circular references, resulting in infinite loops during serialization. Let's break down why this happens and how **DTOs** can conceptually resolve the issue.

In a flawed design, the system uses **entities**—the database models—as the objects to be serialized and returned in API responses. These entities often contain **bidirectional relationships**, where one entity references another, and vice versa. For example:
- An `Order` entity contains a list of `OrderItem` entities.
- Each `OrderItem` entity might have a reference back to the `Order` entity.

When these entities are serialized, the **circular reference** (i.e. `Order -> OrderItem -> Order`) causes an infinite loop, leading to:

- **Stack Overflow Errors**: The serialization keeps going back and forth between the entities, eventually exceeding the call stack limits.
- **Unnecessary Data Exposure**: Internal details of the entity relationships are exposed, which might not be needed by the client.
- **Performance Issues**: Recursive relationships can lead to excessive resource consumption and slow response times.

#### Refactoring solution
The solution to the problem lies in using **DTOs (Data Transfer Objects)**. Here’s how they conceptually address the issue:

- **Breaking Circular References**: DTOs are specifically designed to **flatten** the data structure and **omit** bidirectional relationships.

- **Flattening the Data Structure**: Unlike entities that can contain nested, complex relationships, DTOs simplify the structure. They allow the necessary data to be represented in a **non-recursive** format, avoiding the circular references that cause serialization issues.

- **Controlled and Selective Serialization**: **With DTOs, we can selectively decide what to serialize**. Instead of serializing entire entities with all their relationships, we can choose to expose only the fields that are necessary for the client.

- **Controlled Data Exposure**: DTOs enable **fine-grained control** over which fields and relationships are exposed to the client. This not only helps avoid unnecessary recursion but also protects internal data structures from being exposed.

Unlike entities, **DTOs (Data Transfer Objects)** are used to map request and response bodies, typically in the form of JSON structures for REST APIs.

- A **DTO** is a simple object that holds data and is specifically designed to transfer data between layers, such as from the backend service to the client.
- **DTOs** are meant to **decouple** the internal repository layer from the controller layer. This separation allows the controller to manage the data representation independently of the entity structure, ensuring that complex domain models (entities) are not directly exposed in the API.
- By isolating the business logic in the entity layer and transferring simplified data structures with DTOs, we reduce the risk of data leakage, recursive nesting issues, and unnecessary data exposure.

In short, using **DTOs** conceptually decouples the internal entity structure from the API response, eliminating the root cause of recursive nesting and improving both performance and maintainability.

# Other needed improvements

While the current codebase is functional and represents the initial stages of the project, there are several aspects that need to be addressed before it can be considered robust enough and production-ready. Below, I highlight some of the key areas that need further attention.

## 1. **Stock Quantity Validation**
Currently, the system does not handle scenarios where the quantity of items being added to an order exceeds the available stock. The logic should be implemented to ensure that users cannot add more items than are available in stock. This validation needs to be added to the `addItemToOrder` and similar methods to ensure that the system is consistent with available inventory.

**To Do:**
- Add stock quantity validation before adding items to orders.
- Respond with a meaningful error message if the requested quantity exceeds available stock.

## 2. **Exception Handling**
At present, basic exception handling is implemented using `Optional` checks and simple response statuses like `ResponseEntity.ok()` and `ResponseEntity.notFound()`. However, the application lacks comprehensive exception handling to manage unexpected scenarios such as:
- Database connection failures.
- Internal server errors.
- Invalid data formats or unexpected inputs.

**To Do:**
- Implement a global exception handler using `@ControllerAdvice`.
- Handle common exceptions such as `EntityNotFoundException`, `DataIntegrityViolationException`, etc.
- Return consistent and user-friendly error responses with appropriate HTTP status codes.

## 3. **Response Status Handling**
Although the current response status handling uses `ResponseEntity.ok()` for successful requests and `ResponseEntity.notFound()` for missing resources, there is a need for more nuanced handling of different HTTP statuses. For example:
- **400 Bad Request**: For invalid inputs or missing required fields.
- **401 Unauthorized**: For unauthorized access attempts.
- **403 Forbidden**: When a user tries to perform an action they are not allowed to.
- **500 Internal Server Error**: For unexpected server-side issues.

**To Do:**
- Review the response status codes and add more specific status codes based on different error conditions.

## 4. **Validation and Constraints**
While Java Bean validation is being used in request DTOs (e.g. `@NotBlank`, `@Size`, `@Email`), the validation is not comprehensive enough. For instance:
- There is no validation on the `quantity` field in the `AddItemToOrderRequest` class to ensure it's greater than zero.
- Some request parameters (like `userId` in order creation) may benefit from more specific constraints or checks.

**To Do:**
- Enhance validation annotations on request DTOs to cover all required fields and logical constraints.
- Ensure that custom validation logic is implemented for complex cases (e.g. item availability).

## 5. **Error Responses**
Currently, error responses are minimal and only return a basic message. For better user experience and debugging, detailed error responses should be returned, including:
- Error codes.
- Descriptions of what went wrong.
- Suggested actions to resolve the error.

**To Do:**
- Standardize error responses with a consistent format (e.g. `ErrorCode`, `ErrorMessage`).
- Provide more detailed error messages where necessary.

## 6. **Unit and Integration Testing**
There are no unit or integration tests present in the current codebase. For this system to be ready for production, comprehensive testing is required to ensure the correctness of the application, including:
- Unit tests for services and controllers.
- Integration tests to validate the complete flow of operations (e.g. creating an order, adding/removing items, etc.).
- Mocking database calls to test scenarios without interacting with the actual database.

**To Do:**
- Implement unit tests for the `UserService`, `OrderService`, and other critical services.
- Write integration tests that simulate real-world operations.

## 7. **Logging**
The application lacks detailed logging, which makes it harder to trace issues during runtime. Proper logging is critical for debugging and tracking system health.

**To Do:**
- Implement logging using a framework like `SLF4J` with `Logback` or `Log4j`.
- Log important events such as user actions, exceptions, and system failures.

## 8. **Security Considerations**
The application does not currently include any security measures, such as authentication and authorization, which are critical for a production environment. There should be mechanisms to:
- Authenticate users via JWT tokens or sessions.
- Authorize users to ensure they only have access to their own data (e.g. preventing users from accessing other users' orders).
- Prevent SQL injection, cross-site scripting (XSS), and other common security vulnerabilities.

**To Do:**
- Implement security using Spring Security.
- Add authentication and authorization to protect sensitive endpoints.

## 9. **API Documentation**
Although the endpoints are defined, there is no API documentation available for users or developers to understand how to interact with the system. An API documentation tool such as Swagger can help generate interactive documentation for the REST API.

**To Do:**
- Integrate Swagger or another API documentation tool.
- Document the available endpoints, request/response formats, and error codes.

---
