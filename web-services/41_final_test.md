## Multiple Choice Test on Web Services

### Question 1
```java
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
```
What is the purpose of `@PathVariable` in this method?

- It binds the value from the URL path to the method parameter.
- It retrieves data from the request body.
- It maps the response to JSON.

---

### Question 2
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;
}
```
What does `@GeneratedValue(strategy = GenerationType.IDENTITY)` do?

- Manages ID generation automatically using database identity columns.
- Assigns a random UUID as the ID.
- Requires the application to manually provide an ID.

---

### Question 3
```java
@GetMapping("/orders")
public ResponseEntity<List<Order>> getAllOrders() {
    return ResponseEntity.ok(orderService.getAllOrders());
}
```
What HTTP method does this controller handle?

- POST
- GET
- DELETE

---

### Question 4
```java
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
}
```
What does this repository method do?

- Fetches all orders for a given user.
- Deletes orders based on user ID.
- Retrieves a single order by order ID.

---

### Question 5
```java
@PostMapping("/items")
public ResponseEntity<Item> createItem(@RequestBody Item item) {
    return ResponseEntity.ok(itemService.saveItem(item));
}
```
What does `@RequestBody` do?

- Maps JSON request data to a Java object.
- Retrieves data from query parameters.
- Maps path variables to method parameters.

---

### Question 6
```java
@PutMapping("/users/{id}")
public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
    return ResponseEntity.ok(userService.updateUser(id, updatedUser));
}
```
What is the purpose of `@PutMapping` in this method?

- It maps HTTP PUT requests to this method.
- It maps HTTP POST requests to this method.
- It maps HTTP DELETE requests to this method.

---

### Question 7
```java
@DeleteMapping("/orders/{id}")
public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
}
```
What is the correct HTTP status returned when deleting an order?

- 201 Created
- 204 No Content
- 404 Not Found

---

### Question 8
```java
public class OrderItem {
    @EmbeddedId
    private OrderItemKey id;
}
```
What is the purpose of `@EmbeddedId`?

- It defines a composite primary key.
- It automatically generates an ID.
- It marks the field as unique.

---

### Question 9
```java
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
private List<Order> orders;
```
What does `CascadeType.ALL` do in this relationship?

- It prevents deletion of related orders.
- It allows all operations (persist, remove, merge) to be cascaded to related entities.
- It disables cascading for related entities.

### Question 10
```java
@Enumerated(EnumType.STRING)  
@Column(name = "status", nullable = false, length = 20)  
private OrderStatus status;
```  
What does the `@Enumerated(EnumType.STRING)` annotation do in this entity field?

- It stores the enum as a string in the database.
- It maps the enum to an integer representation.
- It automatically converts the enum to a boolean value.

---

### Question 11
```java
public enum OrderStatus {  
    pending, shipped, delivered, canceled  
}
```  
What is the purpose of the `OrderStatus` enum in this application?

- It restricts order status values to predefined constants.
- It dynamically generates order statuses at runtime.
- It allows users to set any string value for the order status.

---

### Question 12
```java
@ManyToOne  
@JoinColumn(name = "user_id_fk", nullable = false)  
private User user;
```  
What is the function of the `@ManyToOne` annotation in this context?

- It creates a many-to-one relationship between orders and users.
- It ensures that each order can be linked to multiple users.
- It prevents the use of foreign keys in the database schema.

---

### Question 13
```java
@Repository  
public interface ItemRepository extends JpaRepository<Item, Integer> {  
}
```  
What does extending `JpaRepository<Item, Integer>` provide to the `ItemRepository`?

- It provides built-in CRUD operations for the `Item` entity.
- It automatically maps the repository to a REST endpoint.
- It forces the developer to implement custom database queries manually.

---

### Question 14
```java
@MapsId("order_id_pk")  
@JoinColumn(name = "order_id_fk")  
private Order order;
```  
What is the function of the `@MapsId` annotation in this entity mapping?

- It links the field to a composite primary key.
- It allows an entity to have multiple primary keys.
- It prevents automatic ID generation in the database.

---

### Question 15
```java
@Column(name = "price", nullable = false, precision = 10, scale = 2)  
private BigDecimal price;
```  
What does the `precision = 10, scale = 2` configuration do for the `price` column?

- It allows up to 10 digits, with 2 decimal places.
- It restricts all price values to whole numbers.
- It automatically converts prices to integers.

---

### Question 16
```java
public interface UserRepository extends JpaRepository<User, Integer> {  
    Optional<User> findByUsername(String username);  
}
```  
What does the `findByUsername(String username)` method do in Spring Data JPA?

- It automatically generates a query to retrieve a user by username.
- It updates the username of a user in the database.
- It deletes a user with the specified username.

---

### Question 17
```java
@DeleteMapping("/users/{id}")  
public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {  
    userService.deleteUser(id);  
    return ResponseEntity.noContent().build();  
}
```  
Why does this method return `ResponseEntity.noContent()`?

- It indicates that the resource was deleted successfully but has no response body.
- It prevents the deletion operation from executing.
- It returns a `404 Not Found` status.

---

### Question 18
```java
CREATE TABLE shop_schema.Order (  
    order_id SERIAL PRIMARY KEY,  
    user_id_fk INT NOT NULL,  
    FOREIGN KEY (user_id_fk) REFERENCES shop_schema.User(user_id) ON DELETE CASCADE  
);
```  
What does the `ON DELETE CASCADE` constraint do in this SQL schema?

- It automatically deletes all related orders when a user is deleted.
- It prevents the deletion of users who have placed orders.
- It changes the foreign key reference to NULL when a user is deleted.

---

### Question 19
```java
@PatchMapping("/users/{id}")
public ResponseEntity<UserDTO> updateUserEmail(
    @PathVariable Integer id, @RequestParam String newEmail) {
    return ResponseEntity.ok(userService.updateEmail(id, newEmail));
}
```
What does `@RequestParam` do in this method?

- It binds the `newEmail` value from the query parameter to the method argument.
- It extracts data from the request body.
- It maps a path variable to the method parameter.

---

### Question 20
```java
@PostMapping("/orders/{id}/items")
public ResponseEntity<OrderItemDTO> addItemToOrder(
    @PathVariable Integer id, @RequestBody OrderItemDTO orderItemDTO) {
    return ResponseEntity.ok(orderService.addItem(id, orderItemDTO));
}
```
What HTTP method does this controller handle?

- POST
- GET
- DELETE

---

### Question 21
```java
@PutMapping("/items/{id}")
public ResponseEntity<ItemDTO> updateItem(
    @PathVariable Integer id, @RequestBody ItemDTO itemDTO) {
    return ResponseEntity.ok(itemService.updateItem(id, itemDTO));
}
```
What is the primary purpose of `@PutMapping` in this method?

- It maps HTTP PUT requests to this method.
- It maps HTTP POST requests to this method.
- It maps HTTP PATCH requests to this method.

---

### Question 22
```java
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByStatus(OrderStatus status);
}
```
What does the `findByStatus(OrderStatus status)` method do?

- It retrieves all orders with a specific status.
- It updates the order status in the database.
- It deletes all orders with a given status.

---

### Question 23
```java
@Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
List<Order> findOrdersByUser(@Param("userId") Integer userId);
```
What does the `@Query` annotation do in this method?

- It defines a custom JPQL query for fetching orders by user ID.
- It automatically generates a SQL query based on method name conventions.
- It maps a DTO to the database entity.

---

### Question 24
```java
@Column(name = "created_at", nullable = false, updatable = false)
private LocalDateTime createdAt = LocalDateTime.now();
```
What is the purpose of `updatable = false` in this column definition?

- It prevents modifications to the `created_at` field after insertion.
- It ensures the field can only be updated manually.
- It marks the column as an indexed field.

---

### Question 25
```java
@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }
}
```
What is the purpose of the `@RestController` annotation?

- It marks the class as a Spring MVC controller returning JSON or XML responses.
- It registers the class as a Spring Bean only.
- It is used for handling database transactions.

---

### Question 26
```java
public class UserDTO {
    @JsonProperty("user_id")
    private Integer userId;
}
```
What is the function of `@JsonProperty("user_id")` in this DTO?

- It maps the JSON field `user_id` to `userId` in the Java object.
- It serializes the Java object as an XML format.
- It enforces field immutability.

---

### Question 27
```java
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByName(String name);
}
```
What is the purpose of the `@Repository` annotation?

- It marks the class as a Data Access Object (DAO) and enables exception translation.
- It defines a transactional boundary for methods within the repository.
- It specifies a service layer component.

---

### Question 28
```java
@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
```
What is the role of the `@Service` annotation?

- It marks a class as a Spring service component for business logic.
- It defines an HTTP endpoint in a Spring Boot application.
- It is used for transaction management.

---

### Question 29
```java
@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource();
    }
}
```
What is the purpose of the `@Bean` annotation?

- It defines a Spring-managed bean within a `@Configuration` class.
- It registers an entity in JPA.
- It marks a method for transactional execution.

---

### Question 30
```java
@PostMapping("/users")
public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
    return ResponseEntity.ok(userService.createUser(userDTO));
}
```
What is the purpose of the `@Valid` annotation?

- It triggers validation constraints on the `UserDTO` fields.
- It ensures the request body is always non-null.
- It automatically converts JSON to XML.

---

### Question 31
```java
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
```
What does the `@Entity` annotation do?

- It marks a class as a JPA entity mapped to a database table.
- It registers a Spring component.
- It defines an HTTP controller for API requests.

---

### Question 32
```java
@WebService
public interface OrderService {
    @WebMethod
    Order getOrder(int id);
}
```
What does the `@WebMethod` annotation indicate?

- It marks the method as a web service operation.
- It ensures the method runs asynchronously.
- It binds the method to a specific REST endpoint.

---

### Question 33
```java
@WebService
public class GreetingService {
    @WebMethod
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
```
What does this web service method return when called with `"Alice"`?

- "Hello, Alice"
- "Error: Method not allowed"
- An XML document with no content

---

### Question 34
```java
@Endpoint
public class PersonEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/soap";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetPersonRequest")
    @ResponsePayload
    public GetPersonResponse getPerson(@RequestPayload GetPersonRequest request) {
        GetPersonResponse response = new GetPersonResponse();
        response.setName("John Doe");
        return response;
    }
}
```
What does the `@PayloadRoot` annotation do?

- It maps a SOAP request to a Java method.
- It defines the root element of the response XML.
- It enforces security constraints on the method.

---

### Question 35
```java
@WebService
public class CalculatorService {
    @WebMethod
    public int subtract(int a, int b) {
        return a - b;
    }
}
```
What protocol does this web service use by default?

- SOAP
- REST
- GraphQL

---

### Question 36
```java
@Endpoint
public class OrderEndpoint {
    @PayloadRoot(namespace = "http://example.com/orders", localPart = "CreateOrderRequest")
    @ResponsePayload
    public CreateOrderResponse createOrder(@RequestPayload CreateOrderRequest request) {
        CreateOrderResponse response = new CreateOrderResponse();
        response.setOrderId(1234);
        return response;
    }
}
```
What is the expected SOAP response structure?

- An XML document containing `<CreateOrderResponse>`
- A JSON object with `{ "orderId": 1234 }`
- A plain text response with `Order ID: 1234`

---

### Question 37
```java
@WebService
public class DivisionService {
    @WebMethod
    public double divide(int a, int b) {
        return a / (double) b;
    }
}
```
What happens if `b` is zero?

- The service throws an exception.
- The service returns `Infinity`.
- The service ignores the request.

---

### Question 38
```java
@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }
}
```
What does the `@SpringBootApplication` annotation do?

- It enables component scanning, auto-configuration, and configuration.
- It marks the main method as a REST API endpoint.
- It enables JPA repositories.

---

### Question 39
What status code should a server return when a DELETE request is successfully processed but there is no response body?

- 204 No Content
- 200 OK
- 404 Not Found

---


### Question 40
```java
@WebService
public class TemperatureService {
    @WebMethod
    public double convertToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }
}
```
What does this method return when called with `0`?

- `32.0`
- `0.0`
- `100.0`

---

### Question 41
What is the typical response status code for a successful GET request?

- 200 OK
- 201 Created
- 204 No Content

---

### Question 42
```java
@DeleteMapping("/users/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
}
```
What HTTP status is returned by this method?

- `204 No Content`
- `404 Not Found`
- `201 Created`

---

### Question 43
```java
@Query("SELECT p FROM Person p WHERE p.city = :city")
List<Person> findPeopleByCity(@Param("city") String city);
```
What does the `@Query` annotation do?

- It defines a custom JPQL query.
- It automatically generates SQL based on method name.
- It forces eager loading of results.

---

### Question 44
```java
@EmbeddedId
private OrderItemKey id;
```
What is the purpose of `@EmbeddedId`?

- It defines a composite primary key.
- It automatically generates an ID.
- It prevents duplicate entries.

---

### Question 45
```java
@Column(name = "price", precision = 8, scale = 2)
private BigDecimal price;
```
What does `precision = 8, scale = 2` mean?

- The column supports 8 total digits, with 2 decimal places.
- The column allows only integer values.
- The column enforces a maximum value of 8.

---

### Question 46
```java
@WebService
public class CurrencyConverterService {
    @WebMethod
    public double convertToEuros(double usdAmount) {
        return usdAmount * 0.85;
    }
}
```
What does this method return when called with `100`?

- `85.0`
- `100.0`
- `0.85`

---

### Question 47
Which HTTP method is NOT idempotent?

- POST
- PUT
- DELETE

---

### Question 48
What does it mean for an HTTP method to be idempotent?

- Multiple identical requests have the same effect as a single request.
- The request always creates a new resource.
- The request modifies the resource in unpredictable ways.

---

### Question 49
```java
@WebService
public class ShippingService {
    @WebMethod
    public String trackPackage(String trackingNumber) {
        return "Tracking info for " + trackingNumber;
    }
}
```
Which protocol does this web service primarily use?

- SOAP
- REST
- WebSockets

---

### Question 50
```java
@WebService
public class PaymentService {
    @WebMethod
    public boolean processPayment(double amount) {
        return amount > 0;
    }
}
```
What does the method return when called with `-10.0`?

- `false`
- `true`
- An exception is thrown

---

### Question 51
```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany
    private List<Course> courses;
}
```
What does the `@ManyToMany` annotation indicate?

- A student can enroll in multiple courses, and each course can have multiple students.
- A student can only enroll in one course.
- A course can have only one student.

---

### Question 52
```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Department department;
}
```
What does the `@ManyToOne` annotation mean?

- Many employees belong to one department.
- One employee belongs to many departments.
- Each department has only one employee.

---

### Question 53
```java
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy = "order")
    private List<Item> items;
}
```
What does the `@OneToMany` annotation indicate?

- An order can have multiple items.
- An order can have only one item.
- Each item is associated with multiple orders.

---

### Question 54
```java
@Entity
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    private Address address;
}
```
What does the `@OneToOne` annotation mean?

- A library has exactly one address.
- A library can have multiple addresses.
- An address can be shared by multiple libraries.

---

### Question 55
```java
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Owner owner;
}
```
Which statement is true about the relationship between `Car` and `Owner`?

- Many cars can belong to one owner.
- Each car has multiple owners.
- Each owner can own only one car.

---

### Question 56
```java
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy = "invoice")
    private List<Payment> payments;
}
```
What does the `mappedBy` attribute do in `@OneToMany`?

- It specifies the field in the `Payment` entity that owns the relationship.
- It creates a separate join table.
- It enforces unique constraints.

---

### Question 57
```java
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Category category;
}
```
Which statement is correct about `Product` and `Category`?

- Many products can belong to one category.
- One product can belong to multiple categories.
- Each category has only one product.

---

### Question 58
```java
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToMany(mappedBy = "books")
    private List<Author> authors;
}
```
What does `mappedBy = "books"` mean in `@ManyToMany`?

- The `books` field in `Author` is the owning side of the relationship.
- The `Book` entity owns the relationship.
- It enforces a unique constraint.

---

### Question 59
```java
@RestController  
@RequestMapping("/products")  
public class ProductController {  
    @GetMapping("/{id}")  
    public ResponseEntity<String> getProduct(@PathVariable Long id) {  
        return ResponseEntity.ok("Product ID: " + id);  
    }  
}  
```  
What is the purpose of `@PathVariable` in this code?

- It extracts the value from the URL and passes it as a method parameter.
- It converts the request body into a `Long` value.
- It ensures the `id` parameter is always present in the request body.

---

### Question 60
```java
public class Order {  
    @NotNull  
    private String orderNumber;  
      
    @Size(min = 1)  
    private List<Product> products;  
}
```  
What happens if a request with an empty `products` list is received?

- A validation error occurs because `@Size(min = 1)` requires at least one product.
- The request is accepted, and the `products` list is stored as empty.
- The request fails only if `orderNumber` is null.

---

### Question 61
```java
@RestController  
@RequestMapping("/users")  
public class UserController {  
    @PostMapping  
    public ResponseEntity<String> createUser(@RequestBody User user) {  
        return ResponseEntity.ok("User created");  
    }  
}  
```  
What does `@RequestBody` do in this code?

- It binds the HTTP request body to the `user` parameter.
- It validates the `user` object before processing.
- It ensures that only JSON requests are accepted.

---

### Question 62
```java
@Service  
public class OrderService {  
    private final PaymentService paymentService;  
      
    @Autowired  
    public OrderService(PaymentService paymentService) {  
        this.paymentService = paymentService;  
    }  
}
```  
What does `@Autowired` do in this code?

- It injects an instance of `PaymentService` into `OrderService`.
- It initializes `paymentService` with a default implementation.
- It ensures that `OrderService` is a singleton.

---

### Question 63
```java
@Component  
public class EmailService {  
    @Autowired  
    private NotificationService notificationService;  
}
```  
Which statement is true about `@Autowired` when used on fields?

- Spring automatically injects a `NotificationService` instance.
- It allows the field to be modified at runtime.
- It requires an explicit constructor to initialize `notificationService`.

---

### Question 64
```java
public ResponseEntity<String> getProduct() {  
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");  
}
```  
What does `ResponseEntity` provide in this method?

- It allows returning an HTTP response with a status and body.
- It converts the response into a JSON object automatically.
- It forces all responses to be in XML format.

---

### Question 65
```java
public ResponseEntity<Product> getProduct() {  
    Product product = new Product("Laptop");  
    return ResponseEntity.ok(product);  
}
```  
What is the default response format when using `ResponseEntity<T>`?

- JSON
- XML
- Plain text

---

### Question 66
```json
{
    "id": 101,
    "name": "Laptop",
    "price": 999.99
}
```  
Which statement is correct about this JSON structure?

- It represents an object with three key-value pairs.
- It is invalid because the price should be a string.
- It must have an explicit `null` value for missing fields.

---

### Question 67
```json
[
    {
        "id": 1,
        "name": "Alice"
    },
    {
        "id": 2,
        "name": "Bob"
    }
]
```  
What does this JSON represent?

- A list (array) of objects.
- A nested object inside another object.
- An invalid JSON format due to missing commas.

---

### Question 68
```xml
<product>  
    <id>101</id>  
    <name>Laptop</name>  
    <price>999.99</price>  
</product>  
```  
Which statement is correct about this XML structure?

- It defines an XML document with a root element `<product>`.
- It is invalid because XML does not support nested elements.
- It is missing a required `<root>` tag.

---

### Question 69
```xml
<orders>  
    <order>  
        <id>1</id>  
        <total>100.00</total>  
    </order>  
    <order>  
        <id>2</id>  
        <total>200.00</total>  
    </order>  
</orders>  
```  
What does this XML structure represent?

- A list of orders inside a root element `<orders>`.
- A single order with multiple `id` elements.
- An invalid XML due to duplicate `id` elements.

---

### Question 70
```xml
<user id="123">  
    <name>John Doe</name>  
</user>  
```  
Which feature of XML is demonstrated by `id="123"`?

- An attribute inside the `<user>` tag.
- A self-closing tag syntax.
- A required primary key for XML elements.  





