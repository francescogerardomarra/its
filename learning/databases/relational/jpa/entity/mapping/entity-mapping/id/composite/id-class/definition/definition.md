# Definition
**The `@IdClass` annotation has the following characteristics:**
- it requires defining a separate class
for the composite key and using `@Id` on the individual fields in the entity class.

**Here are the steps to use it:**
1. **Create a primary key class:**
   - this class contains the fields that form the
   composite key and implements `Serializable`.
2. **Annotate the entity class**:
   - use `@IdClass` to specify the primary key class;
   - annotate the composite key fields in the entity class with `@Id`.

**Here is an example:**
<div style="display: flex; justify-content: space-between; margin: 0 -5px;">
  <div style="width: 50%; margin: 0 5px;">

**Primary key class:**
```java
import java.io.Serializable;

public class OrderId implements Serializable {
    private Long orderId;
    private Long productId;

    // Constructors, Getters, Setters, hashCode, equals
    public OrderId() {}

    public OrderId(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    // Getters and Setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId1 = (OrderId) o;
        return Objects.equals(orderId, orderId1.orderId) &&
                Objects.equals(productId, orderId1.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
```
  </div>
  <div style="width: 50%; margin: 0 5px;">

**Entity class:**
```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(OrderId.class)
public class Order {
    @Id
    private Long orderId;

    @Id
    private Long productId;

    // Other fields, Getters, Setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    // Other Getters and Setters
}
```
  </div>
</div>