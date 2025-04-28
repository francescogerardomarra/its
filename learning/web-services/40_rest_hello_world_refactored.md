# Refactoring of Rest HelloWorld
This lesson continues from the previous one, which introduced a REST Web Service exposing CRUD operations in a Spring Data JPA layered application with Hibernate
and PostgreSQL, without using DTOs.

In the last lesson, we encountered a major problem with the final test in our testing guide for the project.

This issue is addressed in this lesson through the use of DTOs.

# Recursive Nesting

## Issue
When building a REST API, recursive nesting due to bidirectional relationships between
entities can lead to performance issues, stack overflow errors, and unwanted data exposure.
This documentation explains the problem, how DTOs (Data Transfer Objects) can solve it,
and guidelines for deciding which fields should be included in your DTOs.

In our API, entities such as **User**, **Order**, and **OrderItem** are related bidirectionally:

- An **Order** contains a list of **OrderItems**.
- Each **OrderItem** references its **Order**.
- The **Order** has a reference to the **User** who placed it.
- The **User** maintains a list of **Orders**.

**During JSON serialization:**

- The **Order** is serialized including its **OrderItems**.
- Each **OrderItem** contains an **Order**, which again contains the **User**.
- The **User** includes its list of **Orders**, and the loop repeats.

This infinite loop of nested references results in:

- **Exponential growth of the JSON payload**
- **Stack Overflow errors**
- **Unnecessary exposure of internal relationships**

The code below shows an `OrderResponse` that directly includes the `order_items` field as
a list of `OrderItem` entities. Because each `OrderItem` holds a reference back to the parent
`Order` (and subsequently to the `User`), this design causes recursive nesting during JSON serialization.

```java
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
    private List<OrderItem> orderItems; // This field causes recursive nesting!
}
```

Explanation:

- **Recursive Nesting Issue:** each `OrderItem` contains a reference to its `Order`, which in turn
  references the `User`, and the `User` holds a list of `Orders`. This cycle creates
  an infinite loop when serializing.
- **Impact:** the JSON payload can grow exponentially, potentially leading
  to stack overflow errors and severe performance issues.

## Solution
After refactoring, `OrderItem` will be replaced with its flattened version `OrderItemDTO`:

````java
package com.example.rest.response.order;

import com.example.dto.orderitem.OrderItemDTO;
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
    private String status;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("order_items")
    private List<OrderItemDTO> orderItems;
}
````

DTOs help decouple your API's internal entity design from the data exposed to the client by:

- **Breaking Circular References:** only the required data is included in the DTO, without embedding
the complete related entity.
- **Flattening the Data Structure:** the DTO contains only essential fields, eliminating
deep or recursive nesting.
- **Selective Serialization:** you decide exactly which fields to include, allowing you to
omit sensitive or unnecessary relationships.

Instead of directly serializing an **Order** entity that contains nested objects, use a
DTO that represents only what the client needs.

## DTO fields
A DTO should include **all fields** that a controller might pass to a service and that a service might return to a controller.

This ensures a clear, complete data contract between layers, even if not every field is non-null at the same time. 

It provides flexibility for different operations where some fields may be optional while others are required.

# Directory structure

This is the new directory structure of the updated project with DTOs.

````tetx
webservicerestrefactored/
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           ├── WebServiceRest.java
    │   │           ├── controller
    │   │           │   ├── ItemController.java
    │   │           │   ├── OrderController.java
    │   │           │   ├── OrderItemController.java
    │   │           │   └── UserController.java
    │   │           ├── model
    │   │           │   ├── Item.java
    │   │           │   ├── Order.java
    │   │           │   ├── OrderItem.java
    │   │           │   ├── User.java
    │   │           │   ├── enums
    │   │           │   │   └── OrderStatus.java
    │   │           │   └── key
    │   │           │       └── OrderItemKey.java
    │   │           ├── repository
    │   │           │   ├── ItemRepository.java
    │   │           │   ├── OrderRepository.java
    │   │           │   ├── OrderItemRepository.java
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
    │   │           │   │       ├── UserRequest.java
    │   │           │   │       └── RemoveUserRequest.java
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
    │   │           │           ├── UserResponse.java
    │   │           │           └── RemoveUserResponse.java
    │   │           ├── service
    │   │           │   ├── ItemService.java
    │   │           │   ├── OrderService.java
    │   │           │   ├── OrderItemService.java
    │   │           │   └── UserService.java
    │   │           └── dto
    │   │               ├── item
    │   │               │   └── ItemDTO.java
    │   │               ├── order
    │   │               │   └── OrderDTO.java
    │   │               ├── orderitem
    │   │               │   └── OrderItemDTO.java
    │   │               └── user
    │   │                   └── UserDTO.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java

````

# DTOs
These four DTOs were added to act as a bridge in the communication
between the controllers and the services.

**Simplified JSON Serialization:**

- some entity relationships can cause circular references or unnecessary data bloat in JSON responses like the previously talked about recursive problem;
- DTOs provide a structured and optimized way to format data for frontend consumption.

## ItemDTO

```java
package com.example.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO class for transferring Item data without exposing the internal entity relationships.
 */
@Getter
@Setter
@AllArgsConstructor
public class ItemDTO {

    private Integer itemId;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
}
```

## OrderDTO

```java
package com.example.dto.order;

import com.example.dto.orderitem.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {

    private Integer orderId;
    private LocalDateTime orderDate;
    private String status;
    private Integer userId;
    private List<OrderItemDTO> orderItems;
}
```

## OrderItemDTO

```java
package com.example.dto.orderitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemDTO {

    private Integer orderId;
    private Integer itemId;
    private Integer quantity;
}
```

## UserDTO

```java
package com.example.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private Integer userId;
    private String username;
    private String email;
    private String createdAt;  // Represented as a String for easy JSON formatting
}
```

---

# Services
## ItemService
```java
package com.example.service;

import com.example.dto.item.ItemDTO;
import com.example.model.Item;
import com.example.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Create or update an Item
    public ItemDTO saveItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setItemId(itemDTO.getItemId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(BigDecimal.valueOf(itemDTO.getPrice()));
        item.setStockQuantity(itemDTO.getStockQuantity() == null ? 0 : itemDTO.getStockQuantity());

        Item savedItem = itemRepository.save(item);

        return new ItemDTO(
                savedItem.getItemId(),
                savedItem.getName(),
                savedItem.getDescription(),
                savedItem.getPrice().doubleValue(),
                savedItem.getStockQuantity()
        );
    }

    // Retrieve all Items
    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll().stream()
                .map(item -> new ItemDTO(
                        item.getItemId(),
                        item.getName(),
                        item.getDescription(),
                        item.getPrice().doubleValue(),
                        item.getStockQuantity()
                ))
                .collect(Collectors.toList());
    }

    // Retrieve an Item by ID
    public Optional<ItemDTO> getItemById(Integer itemId) {
        return itemRepository.findById(itemId).map(item -> new ItemDTO(
                item.getItemId(),
                item.getName(),
                item.getDescription(),
                item.getPrice().doubleValue(),
                item.getStockQuantity()
        ));
    }

    // Delete an Item by ID
    public void deleteItem(Integer itemId) {
        itemRepository.deleteById(itemId);
    }
}
```
### Changes in `ItemService`

#### **Introduction of `ItemDTO`**

- Previously, the service worked directly with `Item` entities.
- Now, it uses `ItemDTO` to control data transfer and prevent direct exposure of the entity.

#### **Modifications in Methods**

- **`saveItem(ItemDTO itemDTO)`**
  - Converts `ItemDTO` to `Item` for persistence.
  - Converts `Item` back to `ItemDTO` before returning it.
  - Ensures only relevant data is sent and received.

- **`getAllItems()`**
  - Previously returned `List<Item>`.
  - Now maps `Item` entities to `ItemDTO`, ensuring a structured API response.

- **`getItemById(Integer itemId)`**
  - Previously returned `Optional<Item>`.
  - Now returns `Optional<ItemDTO>`, preventing unnecessary exposure of entity fields.

- **`deleteItem(Integer itemId)`**
  - Remains unchanged since deletion does not require DTO conversion.

---

## OrderService
```java
package com.example.service;

import com.example.dto.order.OrderDTO;
import com.example.dto.orderitem.OrderItemDTO;
import com.example.model.Order;
import com.example.model.User;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository; // Add UserRepository to fetch User by ID

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // Save an Order using OrderDTO
    public Optional<OrderDTO> saveOrder(OrderDTO orderDTO) {
        // Fetch the User entity based on the userId in the DTO
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create the Order entity and set properties
        Order order = new Order();
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);

        // Return saved order as OrderDTO
        return Optional.of(new OrderDTO(
                savedOrder.getOrderId(),
                savedOrder.getOrderDate(),
                savedOrder.getStatus().toString(), // Converting OrderStatus to String
                savedOrder.getUser().getUserId(),
                Optional.ofNullable(savedOrder.getOrderItems())  // Optional to avoid NPE
                        .orElse(Collections.emptyList())         // Default to an empty list if null
                        .stream()
                        .map(item -> new OrderItemDTO(item.getOrder().getOrderId(), item.getItem().getItemId(), item.getQuantity()))
                        .collect(Collectors.toList())
        ));
    }

    // Retrieve an Order by ID
    public Optional<OrderDTO> getOrderById(Integer orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            return Optional.empty();
        }

        OrderDTO orderDTO = new OrderDTO(
                order.get().getOrderId(),
                order.get().getOrderDate(),
                order.get().getStatus().toString(),
                order.get().getUser().getUserId(),
                order.get().getOrderItems().stream()
                        .map(item -> new OrderItemDTO(item.getOrder().getOrderId(), item.getItem().getItemId(), item.getQuantity()))
                        .collect(Collectors.toList())
        );

        return Optional.of(orderDTO);
    }

    // Retrieve Orders by User ID
    public List<OrderDTO> getOrdersByUserId(Integer userId) {
        List<Order> orders = orderRepository.findByUser_UserId(userId);
        return orders.stream()
                .map(order -> new OrderDTO(
                        order.getOrderId(),
                        order.getOrderDate(),
                        order.getStatus().toString(),
                        order.getUser().getUserId(),
                        order.getOrderItems().stream()
                                .map(item -> new OrderItemDTO(item.getOrder().getOrderId(), item.getItem().getItemId(), item.getQuantity()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    // Delete an Order by ID
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }
}
```
### Changes in `OrderService`

#### **Introduction of `OrderDTO`**
- `OrderService` now uses `OrderDTO` instead of working directly with `Order` entities.
- Provides a controlled way to expose order data while keeping internal structure hidden.

#### **Modifications in Methods**
- **`saveOrder(OrderDTO orderDTO)`**
  - Converts `OrderDTO` to `Order` for persistence.
  - Returns an `OrderDTO` after saving.
  - Ensures encapsulation of data.

- **`getAllOrders()`**
  - Maps `Order` entities to `OrderDTO`.
  - Provides a cleaner and structured API response.

- **`getOrderById(Integer orderId)`**
  - Now returns `Optional<OrderDTO>` instead of `Optional<Order>`.
  - Prevents exposure of entity structure.

- **`deleteOrder(Integer orderId)`**
  - Logic remains unchanged as DTOs are not needed for deletion.

---

## UserService
```java
package com.example.service;

import com.example.dto.user.UserDTO;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save or update a User
    public UserDTO saveUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        User savedUser = userRepository.save(user);

        return new UserDTO(
                savedUser.getUserId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt().toString()
        );
    }

    // Retrieve a User by ID
    public Optional<UserDTO> getUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return Optional.empty();
        }

        User u = user.get();
        UserDTO userDTO = new UserDTO(u.getUserId(), u.getUsername(), u.getEmail(), u.getCreatedAt().toString());
        return Optional.of(userDTO);
    }

    // Retrieve a User by Username
    public Optional<UserDTO> getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return Optional.empty();
        }

        User u = user.get();
        UserDTO userDTO = new UserDTO(u.getUserId(), u.getUsername(), u.getEmail(), u.getCreatedAt().toString());
        return Optional.of(userDTO);
    }

    // Retrieve a User by Email
    public Optional<UserDTO> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return Optional.empty();
        }

        User u = user.get();
        UserDTO userDTO = new UserDTO(u.getUserId(), u.getUsername(), u.getEmail(), u.getCreatedAt().toString());
        return Optional.of(userDTO);
    }

    // Delete a User by ID
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}
```

### Changes in `UserService`

#### **Introduction of `UserDTO`**
- `UserService` now manages users using `UserDTO`.
- Provides abstraction over `User` entity, ensuring data security.

#### **Modifications in Methods**
- **`saveUser(UserDTO userDTO)`**
  - Converts `UserDTO` to `User`.
  - Returns `UserDTO` instead of `User`.

- **`getAllUsers()`**
  - Previously returned `List<User>`.
  - Now returns `List<UserDTO>` for API consistency.

- **`getUserById(Integer userId)`**
  - Returns `Optional<UserDTO>` instead of `Optional<User>`.

- **`deleteUser(Integer userId)`**
  - Unchanged as deletion does not require DTOs.

---

## OrderItemService
```java
package com.example.service;

import com.example.dto.orderitem.OrderItemDTO;
import com.example.model.Item;
import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.model.key.OrderItemKey;
import com.example.repository.ItemRepository;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;  // Inject the OrderRepository
    private final ItemRepository itemRepository;    // Inject the ItemRepository

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    // Add an item to an order
    public Optional<OrderItemDTO> addItemToOrder(OrderItemDTO orderItemDTO) {
        // Create the composite key for OrderItem
        OrderItemKey orderItemKey = new OrderItemKey(orderItemDTO.getOrderId(), orderItemDTO.getItemId());

        Optional<OrderItem> existingOrderItem = orderItemRepository.findById(orderItemKey);

        // Fetch Order and Item entities
        Optional<Order> order = orderRepository.findById(orderItemDTO.getOrderId());
        Optional<Item> item = itemRepository.findById(orderItemDTO.getItemId());

        if (order.isEmpty() || item.isEmpty()) {
            return Optional.empty();  // Return empty if either order or item doesn't exist
        }

        if (existingOrderItem.isPresent()) {
            // If the item already exists, update the quantity
            OrderItem orderItem = existingOrderItem.get();
            orderItem.setQuantity(orderItem.getQuantity() + orderItemDTO.getQuantity());
            orderItemRepository.save(orderItem);
        } else {
            // Create a new order item
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setId(orderItemKey);
            newOrderItem.setQuantity(orderItemDTO.getQuantity());
            newOrderItem.setOrder(order.get());  // Set the Order entity
            newOrderItem.setItem(item.get());    // Set the Item entity

            orderItemRepository.save(newOrderItem);
        }

        // Return the DTO
        return Optional.of(orderItemDTO);
    }

    // Remove an item from an order
    public Optional<OrderItemDTO> removeItemFromOrder(OrderItemDTO orderItemDTO) {
        OrderItemKey orderItemKey = new OrderItemKey(orderItemDTO.getOrderId(), orderItemDTO.getItemId());

        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemKey);

        if (orderItemOptional.isPresent()) {
            orderItemRepository.deleteById(orderItemKey);
            return Optional.of(orderItemDTO); // Return the DTO to show item was removed
        }

        return Optional.empty(); // Return empty if the item wasn't found
    }
}
```
### Changes in `OrderItemService`

#### **Introduction of `OrderItemDTO`**
- `OrderItemService` now handles order items using `OrderItemDTO`.
- Provides a structured and secure way to interact with order items.

#### **Modifications in Methods**
- **`addItemToOrder(OrderItemDTO orderItemDTO)`**
  - Converts `OrderItemDTO` to `OrderItem` for persistence.
  - Returns an `OrderItemDTO` to prevent entity exposure.

- **`removeItemFromOrder(OrderItemDTO orderItemDTO)`**
  - Uses `OrderItemDTO` for structured request handling.

- **`getAllOrderItems()`**
  - Converts `OrderItem` entities to `OrderItemDTO`.

---

# Controllers
## ItemController
```java
package com.example.controller;

import com.example.dto.item.ItemDTO;
import com.example.rest.request.item.ItemRequest;
import com.example.rest.response.item.ItemResponse;
import com.example.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        ItemDTO itemDTO = new ItemDTO(
                null, // Item ID is not needed for creation
                itemRequest.getName(),
                itemRequest.getDescription(),
                itemRequest.getPrice(),
                0 // Default stock quantity
        );

        ItemDTO savedItemDTO = itemService.saveItem(itemDTO);

        // Creating the response
        ItemResponse response = new ItemResponse(
                savedItemDTO.getItemId(),
                savedItemDTO.getName(),
                savedItemDTO.getDescription(),
                savedItemDTO.getPrice(),
                savedItemDTO.getStockQuantity()
        );

        return ResponseEntity.ok(response);
    }

    // Retrieve an item by its ID
    @GetMapping("/{itemId}")  // Method-level mapping with path variable
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Integer itemId) {
        Optional<ItemDTO> itemDTO = itemService.getItemById(itemId);
        if (itemDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Creating the response
        ItemResponse response = new ItemResponse(
                itemDTO.get().getItemId(),
                itemDTO.get().getName(),
                itemDTO.get().getDescription(),
                itemDTO.get().getPrice(),
                itemDTO.get().getStockQuantity()
        );

        return ResponseEntity.ok(response);
    }

    // Modify an existing item (update name, description, and price)
    @PutMapping("/{itemId}")  // Method-level mapping with path variable
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Integer itemId, @RequestBody @Valid ItemRequest itemRequest) {
        Optional<ItemDTO> existingItemDTO = itemService.getItemById(itemId);
        if (existingItemDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ItemDTO itemDTO = existingItemDTO.get();
        if (itemRequest.getName() != null) itemDTO.setName(itemRequest.getName());
        if (itemRequest.getDescription() != null) itemDTO.setDescription(itemRequest.getDescription());
        if (itemRequest.getPrice() != null) itemDTO.setPrice(itemRequest.getPrice());

        ItemDTO updatedItemDTO = itemService.saveItem(itemDTO);

        // Creating the response
        ItemResponse response = new ItemResponse(
                updatedItemDTO.getItemId(),
                updatedItemDTO.getName(),
                updatedItemDTO.getDescription(),
                updatedItemDTO.getPrice(),
                updatedItemDTO.getStockQuantity()
        );

        return ResponseEntity.ok(response);
    }
}
```

**Changes:**
- Incoming requests (`ItemRequest`) are converted to `ItemDTO` before being passed to the service.
- Returned `ItemDTO` from the service is manually mapped to an `ItemResponse` for the client.
- Update endpoints, use DTOs to modify existing items, checking for the presence of data with `Optional`.

## OrderController
```java
package com.example.controller;

import com.example.dto.order.OrderDTO;
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
@RequestMapping("/shop/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // Create a new order (without items initially)
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        // Pass the DTO to the service
        OrderDTO orderDTO = new OrderDTO(
                null, // orderId is not set for new orders
                null, // orderDate is set in the service layer
                null, // status is set in the service layer
                orderRequest.getUserId(), // The userId from the request
                null // No items in the order initially
        );

        Optional<OrderDTO> savedOrder = orderService.saveOrder(orderDTO);

        // Check if the order was saved and return appropriate response
        if (savedOrder.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Manually map fields from OrderDTO to OrderResponse
        OrderDTO savedOrderDTO = savedOrder.get();
        OrderResponse response = new OrderResponse(
                savedOrderDTO.getOrderId(),
                savedOrderDTO.getOrderDate(),
                savedOrderDTO.getStatus(),
                savedOrderDTO.getUserId(),
                savedOrderDTO.getOrderItems()
        );

        return ResponseEntity.status(201).body(response);
    }

    // Retrieve an order by its ID with explicit parameter
    @GetMapping(params = "orderId")
    public ResponseEntity<OrderResponse> getOrderById(@RequestParam Integer orderId) {
        Optional<OrderDTO> orderDTO = orderService.getOrderById(orderId);

        // If the order doesn't exist, return a 404 response
        if (orderDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Manually map fields from OrderDTO to OrderResponse
        OrderDTO orderDTOResponse = orderDTO.get();
        OrderResponse response = new OrderResponse(
                orderDTOResponse.getOrderId(),
                orderDTOResponse.getOrderDate(),
                orderDTOResponse.getStatus(),
                orderDTOResponse.getUserId(),
                orderDTOResponse.getOrderItems() // Map order items if necessary
        );

        return ResponseEntity.ok(response);
    }

    // Retrieve orders by user ID
    @GetMapping(params = "userId")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@RequestParam Integer userId) {
        List<OrderDTO> ordersDTO = orderService.getOrdersByUserId(userId);

        if (ordersDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Manually map List<OrderDTO> to List<OrderResponse>
        List<OrderResponse> responses = ordersDTO.stream()
                .map(orderDTO -> new OrderResponse(
                        orderDTO.getOrderId(),
                        orderDTO.getOrderDate(),
                        orderDTO.getStatus(),
                        orderDTO.getUserId(),
                        orderDTO.getOrderItems()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // Delete an order by its ID
    @DeleteMapping
    public ResponseEntity<RemoveOrderResponse> deleteOrder(@RequestBody @Valid RemoveOrderRequest request) {
        Optional<OrderDTO> orderDTO = orderService.getOrderById(request.getOrderId());

        if (orderDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        orderService.deleteOrder(request.getOrderId());

        RemoveOrderResponse response = new RemoveOrderResponse(
                request.getOrderId(),
                "Order deleted successfully"
        );

        return ResponseEntity.ok(response);
    }
}
```

**Changes:**
- The creation endpoint converts an `OrderRequest` into an `OrderDTO` (with some fields set to null for later population in the service).
- The service returns an `Optional<OrderDTO>`, which is then mapped to an `OrderResponse`.
- Separate endpoints are used to retrieve orders by orderId and by userId, ensuring all responses are DTO-based.
- Deletion also uses the DTO to check existence before proceeding.

## UserController
```java
package com.example.controller;

import com.example.dto.user.UserDTO;
import com.example.rest.request.user.RemoveUserRequest;
import com.example.rest.request.user.UserRequest;
import com.example.rest.response.user.UserResponse;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        UserDTO userDTO = new UserDTO(null, userRequest.getUsername(), userRequest.getEmail(), null);
        UserDTO savedUser = userService.saveUser(userDTO);

        UserResponse response = new UserResponse(
                savedUser.getUserId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    // Modify an existing user (update username and/or email)
    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody @Valid UserRequest userRequest) {
        Optional<UserDTO> existingUser = userService.getUserByEmail(userRequest.getEmail());
        if (existingUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserDTO userDTO = existingUser.get();
        userDTO.setUsername(userRequest.getUsername());
        userDTO.setEmail(userRequest.getEmail());

        UserDTO updatedUser = userService.saveUser(userDTO);

        UserResponse response = new UserResponse(
                updatedUser.getUserId(),
                updatedUser.getUsername(),
                updatedUser.getEmail(),
                updatedUser.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    // Retrieve user by ID (query parameter)
    @GetMapping(params = "id")
    public ResponseEntity<UserResponse> getUserById(@RequestParam Integer id) {
        Optional<UserDTO> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserResponse response = new UserResponse(
                user.get().getUserId(),
                user.get().getUsername(),
                user.get().getEmail(),
                user.get().getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    // Retrieve user by email (query parameter)
    @GetMapping(params = "email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
        Optional<UserDTO> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserResponse response = new UserResponse(
                user.get().getUserId(),
                user.get().getUsername(),
                user.get().getEmail(),
                user.get().getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    // Retrieve user by username (query parameter)
    @GetMapping(params = "username")
    public ResponseEntity<UserResponse> getUserByUsername(@RequestParam String username) {
        Optional<UserDTO> user = userService.getUserByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserResponse response = new UserResponse(
                user.get().getUserId(),
                user.get().getUsername(),
                user.get().getEmail(),
                user.get().getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    // Remove a user (delete)
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody @Valid RemoveUserRequest request) {
        Optional<UserDTO> user = userService.getUserById(request.getUserId());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUser(request.getUserId());

        return ResponseEntity.ok("User deleted successfully");
    }
}
```
**Changes:**
- All user-related endpoints (create, update, retrieve by ID/email/username) now work with `UserDTO` instead of the direct entity.
- Incoming `UserRequest` is converted into `UserDTO` for processing.
- The service returns `Optional<UserDTO>` which is checked by the controller to map into a `UserResponse`.
- Delete operations also follow this pattern, ensuring the user exists before attempting deletion.

## OrderItemController
```java
package com.example.controller;

import com.example.dto.orderitem.OrderItemDTO;
import com.example.rest.request.orderitem.AddItemToOrderRequest;
import com.example.rest.request.orderitem.RemoveItemFromOrderRequest;
import com.example.rest.response.orderitem.AddItemToOrderResponse;
import com.example.rest.response.orderitem.RemoveItemFromOrderResponse;
import com.example.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shop/orderitems")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public ResponseEntity<AddItemToOrderResponse> addItemToOrder(@RequestBody AddItemToOrderRequest request) {
        // Convert the request into a DTO
        OrderItemDTO orderItemDTO = new OrderItemDTO(
                request.getOrderId(),
                request.getItemId(),
                request.getQuantity()
        );

        // Call the service layer
        Optional<OrderItemDTO> responseDTO = orderItemService.addItemToOrder(orderItemDTO);

        // Check if the order item was successfully added or not
        if (responseDTO.isEmpty()) {
            return ResponseEntity.badRequest().body(new AddItemToOrderResponse(
                    request.getOrderId(), request.getItemId(), request.getQuantity(), "Item not added to order."));
        }

        // Build a success response
        AddItemToOrderResponse response = new AddItemToOrderResponse(
                responseDTO.get().getOrderId(),
                responseDTO.get().getItemId(),
                responseDTO.get().getQuantity(),
                "Item successfully added to the order."
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<RemoveItemFromOrderResponse> removeItemFromOrder(@RequestBody RemoveItemFromOrderRequest request) {
        // Convert the request into a DTO
        OrderItemDTO orderItemDTO = new OrderItemDTO(
                request.getOrderId(),
                request.getItemId(),
                null  // Quantity is not needed for removal
        );

        // Call the service layer
        Optional<OrderItemDTO> responseDTO = orderItemService.removeItemFromOrder(orderItemDTO);

        // Check if the order item was successfully removed or not
        if (responseDTO.isEmpty()) {
            return ResponseEntity.badRequest().body(new RemoveItemFromOrderResponse(
                    request.getOrderId(), request.getItemId(), "Item not found in the order."));
        }

        // Build a success response
        RemoveItemFromOrderResponse response = new RemoveItemFromOrderResponse(
                responseDTO.get().getOrderId(),
                responseDTO.get().getItemId(),
                "Item successfully removed from the order."
        );

        return ResponseEntity.ok(response);
    }
}
```
**Changes:**
- Incoming requests (`AddItemToOrderRequest` and `RemoveItemFromOrderRequest`) are converted into `OrderItemDTO` before interacting with the service.
- The service methods return an `Optional<OrderItemDTO>`, which the controller uses to determine whether to send a success or error response.
- Manual mapping is performed to convert `OrderItemDTO` into the corresponding response objects (`AddItemToOrderResponse` and `RemoveItemFromOrderResponse`).
---

# Re-test
Run again the following:

```bash
curl -X GET "http://localhost:8080/shop/orders?userId=3"
```

to check that recursive nesting is now resolved.

